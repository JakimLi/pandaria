package com.github.jakimli.pandaria.steps.verification;

import com.github.jakimli.pandaria.domain.VerificationContext;
import com.github.jakimli.pandaria.domain.variable.Variables;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class NullableVerificationSteps {

    @Autowired
    VerificationContext actual;

    @Autowired
    Variables variables;

    @Given("^verify: '([^\"]*)' is null")
    public void verifyIsNull(String path) throws IOException {
        assertThat(actual.json(path), nullValue());
    }

    @Given("^verify: '([^\"]*)' is not null")
    public void verifyIsNotNull(String path) throws IOException {
        assertThat(actual.json(path), not(nullValue()));
    }

    @Given("^verify: \\$\\{([^\"]*)} is null")
    public void verifyVariableIsNull(String expression) {
        assertThat(variables.get(expression), nullValue());
    }

    @Given("^verify: \\$\\{([^\"]*)} is not null")
    public void verifyVariableIsNotNull(String expression) {
        assertThat(variables.get(expression), not(nullValue()));
    }
}
