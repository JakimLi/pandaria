package com.github.jakimli.pandaria.steps.verification;

import com.github.jakimli.pandaria.domain.FeatureConfiguration;
import com.github.jakimli.pandaria.domain.Variables;
import com.github.jakimli.pandaria.domain.VerificationContext;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static com.github.jakimli.pandaria.utils.FileUtil.read;
import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;
import static org.junit.Assert.assertThat;


public class JsonVerification {

    @Autowired
    VerificationContext actual;

    @Autowired
    Variables variables;

    @Autowired
    FeatureConfiguration configuration;

    @Then("^verify: '([^\"]*)' same json:$")
    public void sameJson(String path, String json) throws IOException {
        assertThat(actual.json(path), jsonEquals(variables.interpret(json)).when(IGNORING_ARRAY_ORDER));
    }

    @Then("^verify: '([^\"]*)' same json: ([^\"]*)$")
    public void sameJsonFromFile(String path, String fileName) throws IOException {
        assertThat(actual.json(path), jsonEquals(fileContent(fileName)).when(IGNORING_ARRAY_ORDER));
    }

    private String fileContent(String fileName) throws IOException {
        return variables.interpret(read(configuration.classpathFile(fileName)));
    }
}
