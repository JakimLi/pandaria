package com.github.jakimli.pandaria.steps.verification;

import com.github.jakimli.pandaria.domain.FeatureConfiguration;
import com.github.jakimli.pandaria.domain.VerificationContext;
import com.github.jakimli.pandaria.domain.variable.Variables;
import cucumber.api.java.en.Given;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static com.github.jakimli.pandaria.utils.FileUtil.read;
import static com.github.jakimli.pandaria.utils.JsonUtil.toJSONAware;

public class JsonSchemaVerificationSteps {

    @Autowired
    VerificationContext actual;

    @Autowired
    FeatureConfiguration configuration;

    @Autowired
    Variables variables;

    @Given("^verify: '([^\"]*)' conform to:")
    public void verifyJsonConformSchema(String path, String schemaJson) throws IOException {
        validateJsonSchema(actual.json(path), schemaJson);
    }

    @Given("^verify: '([^\"]*)' conform to: ([^\"]*)")
    public void verifyJsonConformSchemaFile(String path, String file) throws IOException {
        validateJsonSchema(actual.json(path), read(configuration.classpathFile(file)));
    }

    @Given("^verify: \\$\\{([^\"]*)} conform to:")
    public void verifyJsonInVariableConformSchema(String expression, String schemaJson) throws IOException {
        validateJsonSchema(variables.get(expression), schemaJson);
    }

    @Given("^verify: \\$\\{([^\"]*)} conform to: ([^\"]*)")
    public void verifyJsonInVariableConformSchemaFile(String expression, String file) throws IOException {
        validateJsonSchema(variables.get(expression), read(configuration.classpathFile(file)));
    }

    private void validateJsonSchema(Object actual, String schemaJson) throws IOException {
        SchemaLoader loader = SchemaLoader.builder().schemaJson(new JSONObject(schemaJson)).build();
        Schema schema = loader.load().build();
        schema.validate(toJSONAware(actual));
    }
}
