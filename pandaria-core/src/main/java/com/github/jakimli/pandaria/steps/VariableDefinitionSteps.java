package com.github.jakimli.pandaria.steps;

import com.github.jakimli.pandaria.domain.Variables;
import com.github.jakimli.pandaria.domain.VerificationContext;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static com.github.jakimli.pandaria.utils.JsonContext.json;

public class VariableDefinitionSteps {

    @Autowired
    Variables variables;

    @Autowired
    VerificationContext toBeVerified;

    @Given("^var: '([^\"]*)'='([^\"]*)'$")
    public void defineLiteralStringVariable(String key, String value) {
        variables.assign(key, value);
    }

    @Given("^var: '([^\"]*)'=\"([^\"]*)\"$")
    public void defineStringVariable(String key, String value) {
        variables.assign(key, variables.interpret(value));
    }

    @Given("^var: '([^\"]*)'=(\\d+)$")
    public void defineIntegerVariable(String key, int value) {
        variables.assign(key, value);
    }

    @Given("^var: '([^\"]*)'=(\\d+\\.\\d+)$")
    public void defineDoubleVariable(String key, String value) {
        variables.assign(key, Double.parseDouble(value));
    }

    @Given("^var: '([^\"]*)'<-'([^\"]*)'$")
    public void defineVariableExtractByJsonPath(String key, String path) throws IOException {
        variables.assign(key, json(toBeVerified.toJsonObject()).path(path));
    }
}
