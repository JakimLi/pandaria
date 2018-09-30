package com.github.jakimli.panda.steps;

import com.github.jakimli.panda.domain.verification.JsonVerificationContext;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class JsonVerificationSteps {
    @Autowired
    JsonVerificationContext verifier;

    @When("^verify: '([^\"]*)'='([^\"]*)'$")
    public void verify(String path, final String expected) throws Throwable {
        verifier.verify(path, actual -> assertThat(actual, is(expected)));
    }

    @When("^verify: '([^\"]*)'=(\\d+)$")
    public void verify(String path, final int expected) throws Throwable {
        verifier.verify(path, actual -> assertThat(actual, is(expected)));
    }
}
