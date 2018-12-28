package com.github.jakimli.pandaria.steps.verification;

import com.github.jakimli.pandaria.domain.FeatureConfiguration;
import com.github.jakimli.pandaria.domain.VerificationContext;
import com.github.jakimli.pandaria.domain.variable.Variables;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static com.github.jakimli.pandaria.utils.FileUtil.read;
import static com.github.jakimli.pandaria.utils.JsonUtil.size;
import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_EXTRA_ARRAY_ITEMS;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_EXTRA_FIELDS;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class JsonVerificationSteps {

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

    @Then("^verify: '([^\"]*)' contains json:$")
    public void containsJson(String path, String json) throws IOException {
        assertThat(actual.json(path), jsonEquals(variables.interpret(json))
                .when(IGNORING_ARRAY_ORDER, IGNORING_EXTRA_ARRAY_ITEMS, IGNORING_EXTRA_FIELDS));
    }

    @Then("^verify: '([^\"]*)' contains json: ([^\"]*)$")
    public void containsJsonFromFile(String path, String fileName) throws IOException {
        assertThat(actual.json(path), jsonEquals(fileContent(fileName))
                .when(IGNORING_ARRAY_ORDER, IGNORING_EXTRA_ARRAY_ITEMS, IGNORING_EXTRA_FIELDS));
    }

    @Then("^verify: '([^\"]*)' has size: (\\d+)$")
    public void hasSize(String path, Integer size) throws IOException {
        assertThat(size(actual.json(path)), is(size));
    }

    @Then("^verify: \\$\\{([^\"]*)} same json:$")
    public void variableSameJson(String name, String json) {
        assertThat(variables.get(name), jsonEquals(variables.interpret(json)).when(IGNORING_ARRAY_ORDER));
    }

    @Then("^verify: \\$\\{([^\"]*)} same json: ([^\"]*)$")
    public void variableSameJsonFromFile(String name, String fileName) throws IOException {
        assertThat(variables.get(name), jsonEquals(fileContent(fileName)).when(IGNORING_ARRAY_ORDER));
    }

    @Then("^verify: \\$\\{([^\"]*)} contains json:$")
    public void variableContainsJson(String name, String json) {
        assertThat(variables.get(name), jsonEquals(variables.interpret(json))
                .when(IGNORING_ARRAY_ORDER, IGNORING_EXTRA_ARRAY_ITEMS, IGNORING_EXTRA_FIELDS));
    }

    @Then("^verify: \\$\\{([^\"]*)} contains json: ([^\"]*)$")
    public void variableContainsJsonFromFile(String name, String fileName) throws IOException {
        assertThat(variables.get(name), jsonEquals(fileContent(fileName))
                .when(IGNORING_ARRAY_ORDER, IGNORING_EXTRA_ARRAY_ITEMS, IGNORING_EXTRA_FIELDS));
    }

    @Then("^verify: \\$\\{([^\"]*)} has size: (\\d+)$")
    public void variableHasSize(String name, Integer size) {
        assertThat(size(variables.get(name)), is(size));
    }

    private String fileContent(String fileName) throws IOException {
        return variables.interpret(read(configuration.classpathFile(fileName)));
    }
}
