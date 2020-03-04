package com.github.jakimli.pandaria.steps.verification;

import com.github.jakimli.pandaria.domain.VerificationContext;
import com.github.jakimli.pandaria.domain.variable.Variables;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import static java.lang.Long.parseLong;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class LongVerificationSteps {

    @Autowired
    VerificationContext actual;
    
    @Autowired
    Variables variables;

    @Then("^verify: '([^\"]*)'=long: ([^\"]*)$")
    public void verifyEqualsLong(String path, String expected) throws Throwable {
        assertThat(actual.json(path), is(parseLong(expected)));
    }

    @Then("^verify: '([^\"]*)'!=long: ([^\"]*)$")
    public void verifyNotEqualsLong(String path, String expected) throws Throwable {
        assertThat(actual.json(path), not(parseLong(expected)));
    }

    @Then("^verify: '([^\"]*)'>long: ([^\"]*)$")
    public void verifyGreaterThanLong(String path, String expected) throws Throwable {
        assertThat((Long) actual.json(path), greaterThan(parseLong(expected)));
    }

    @Then("^verify: '([^\"]*)'>=long: ([^\"]*)$")
    public void verifyGreaterThanOrEqualToLong(String path, String expected) throws Throwable {
        assertThat((Long) actual.json(path), greaterThanOrEqualTo(parseLong(expected)));
    }

    @Then("^verify: '([^\"]*)'<long: ([^\"]*)$")
    public void verifyLessThanLong(String path, String expected) throws Throwable {
        assertThat((Long) actual.json(path), lessThan(parseLong(expected)));
    }

    @Then("^verify: '([^\"]*)'<=long: ([^\"]*)$")
    public void verifyLessThanOrEqualToLong(String name, String expected) throws Throwable {
        assertThat((Long) actual.json(name), lessThanOrEqualTo(parseLong(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}=long: ([^\"]*)$")
    public void verifyVariableEqualsLong(String expression, String expected) {
        assertThat(variables.get(expression), is(parseLong(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}!=long: ([^\"]*)$")
    public void verifyVariableNotEqualsLong(String expression, String expected) {
        assertThat(variables.get(expression), not(parseLong(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}>long: ([^\"]*)$")
    public void verifyVariableGreaterThanLong(String expression, String expected) {
        assertThat((Long) variables.get(expression), greaterThan(parseLong(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}>=long: ([^\"]*)$")
    public void verifyVariableGreaterThanOrEqualToLong(String expression, String expected) {
        assertThat((Long) variables.get(expression), greaterThanOrEqualTo(parseLong(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}<long: ([^\"]*)$")
    public void verifyVariableLessThanLong(String expression, String expected) {
        assertThat((Long) variables.get(expression), lessThan(parseLong(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}<=long: ([^\"]*)$")
    public void verifyVariableLessThanOrEqualToLong(String expression, String expected) {
        assertThat((Long) variables.get(expression), lessThanOrEqualTo(parseLong(expected)));
    }
}
