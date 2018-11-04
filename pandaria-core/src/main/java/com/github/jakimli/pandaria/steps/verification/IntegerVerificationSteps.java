package com.github.jakimli.pandaria.steps.verification;

import com.github.jakimli.pandaria.domain.variable.Variables;
import com.github.jakimli.pandaria.domain.VerificationContext;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class IntegerVerificationSteps {
    @Autowired
    Variables variables;

    @Autowired
    VerificationContext actual;

    @Then("^verify: '([^\"]*)'=(\\d+)$")
    public void verifyEqualsInteger(String path, final int expected) throws Throwable {
        assertThat(actual.json(path), is(expected));
    }

    @Then("^verify: '([^\"]*)'!=(\\d+)$")
    public void verifyNotEqualsInteger(String path, final int expected) throws Throwable {
        assertThat(actual.json(path), not(expected));
    }

    @Then("^verify: '([^\"]*)'>(\\d+)$")
    public void verifyGreaterThanInteger(String path, final int expected) throws Throwable {
        assertThat((int) actual.json(path), greaterThan(expected));
    }

    @Then("^verify: '([^\"]*)'>=(\\d+)$")
    public void verifyGreaterThanOrEqualToInteger(String path, final int expected) throws Throwable {
        assertThat((int) actual.json(path), greaterThanOrEqualTo(expected));
    }

    @Then("^verify: '([^\"]*)'<(\\d+)$")
    public void verifyLessThanInteger(String path, final int expected) throws Throwable {
        assertThat((int) actual.json(path), lessThan(expected));
    }

    @Then("^verify: '([^\"]*)'<=(\\d+)$")
    public void verifyLessThanOrEqualToInteger(String path, final int expected) throws Throwable {
        assertThat((int) actual.json(path), lessThanOrEqualTo(expected));
    }

    @Then("^verify: \\$\\{([^\"]*)}=(\\d+)$")
    public void verifyVariableEqualsInteger(String varName, Integer expected) {
        assertThat(variables.get(varName), is(expected));
    }

    @Then("^verify: \\$\\{([^\"]*)}!=(\\d+)$")
    public void verifyVariableNotEqualsInteger(String varName, Integer expected) {
        assertThat(variables.get(varName), not(expected));
    }

    @Then("^verify: \\$\\{([^\"]*)}>(\\d+)$")
    public void verifyVariableGreaterThanInteger(String varName, Integer expected) {
        assertThat((int) variables.get(varName), greaterThan(expected));
    }

    @Then("^verify: \\$\\{([^\"]*)}>=(\\d+)$")
    public void verifyVariableGreaterThanOrEqualToInteger(String varName, Integer expected) {
        assertThat((int) variables.get(varName), greaterThanOrEqualTo(expected));
    }

    @Then("^verify: \\$\\{([^\"]*)}<(\\d+)$")
    public void verifyVariableLessThanInteger(String varName, Integer expected) {
        assertThat((int) variables.get(varName), lessThan(expected));
    }

    @Then("^verify: \\$\\{([^\"]*)}<=(\\d+)$")
    public void verifyVariableLessThanOrEqualToInteger(String varName, Integer expected) {
        assertThat((int) variables.get(varName), lessThanOrEqualTo(expected));
    }
}
