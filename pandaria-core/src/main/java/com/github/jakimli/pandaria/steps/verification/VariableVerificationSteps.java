package com.github.jakimli.pandaria.steps.verification;

import com.github.jakimli.pandaria.domain.Variables;
import com.github.jakimli.pandaria.domain.VerificationContext;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class VariableVerificationSteps {

    @Autowired
    VerificationContext actual;

    @Autowired
    Variables variables;

    @Then("^verify: '([^\"]*)'=\\$\\{([^\"]*)}$")
    public void verifyEqualsVariable(String path, String variable) throws IOException {
        assertThat(actual.json(path), is(variables.get(variable)));
    }

    @Then("^verify: '([^\"]*)'!=\\$\\{([^\"]*)}$")
    public void verifyNotEqualsVariable(String path, String variable) throws IOException {
        assertThat(actual.json(path), not(variables.get(variable)));
    }
}
