package com.github.jakimli.panda.steps;

import com.github.jakimli.panda.domain.Variables;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

public class VariableSteps {

    @Autowired
    Variables variables;

    @Given("^var '([^\"]*)'='([^\"]*)'$")
    public void defineLiteralStringVariable(String key, String value) {
        variables.assign(key, value);
    }
}
