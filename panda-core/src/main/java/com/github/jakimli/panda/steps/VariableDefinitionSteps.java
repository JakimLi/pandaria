package com.github.jakimli.panda.steps;

import com.github.jakimli.panda.domain.Variables;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

public class VariableDefinitionSteps {

    @Autowired
    Variables variables;

    @Given("^var '([^\"]*)'='([^\"]*)'$")
    public void defineLiteralStringVariable(String key, String value) {
        variables.assign(key, value);
    }

    @Given("^var '([^\"]*)'=\"([^\"]*)\"$")
    public void defineStringVariable(String key, String value) {
        variables.assign(key, variables.interpret(value));
    }

    @Given("^var '([^\"]*)'=(\\d+)$")
    public void defineIntegerVariable(String key, int value) {
        variables.assign(key, value);
    }
}
