package com.github.jakimli.pandaria.steps.verification;

import com.github.jakimli.pandaria.domain.variable.Variables;
import com.github.jakimli.pandaria.domain.VerificationContext;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import static java.lang.Float.parseFloat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

public class FloatVerificationSteps {

    @Autowired
    private VerificationContext actual;

    @Autowired
    private Variables variables;

    @Then("^verify: '([^\"]*)'=float: (\\d+\\.\\d+)$")
    public void verifyEqualsFloat(String path, String expected) throws Throwable {
        assertThat(actual.json(path), is(parseFloat(expected)));
    }

    @Then("^verify: '([^\"]*)'!=float: (\\d+\\.\\d+)$")
    public void verifyNotEqualsFloat(String path, String expected) throws Throwable {
        assertThat(actual.json(path), not(parseFloat(expected)));
    }

    @Then("^verify: '([^\"]*)'>float: (\\d+\\.\\d+)$")
    public void verifyGreaterThanFloat(String path, String expected) throws Throwable {
        assertThat((Float) actual.json(path), greaterThan(parseFloat(expected)));
    }

    @Then("^verify: '([^\"]*)'>=float: (\\d+\\.\\d+)$")
    public void verifyGreaterThanOrEqualToFloat(String path, String expected) throws Throwable {
        assertThat((Float) actual.json(path), greaterThanOrEqualTo(parseFloat(expected)));
    }

    @Then("^verify: '([^\"]*)'<float: (\\d+\\.\\d+)$")
    public void verifyLessThanFloat(String path, String expected) throws Throwable {
        assertThat((Float) actual.json(path), lessThan(parseFloat(expected)));
    }

    @Then("^verify: '([^\"]*)'<=float: (\\d+\\.\\d+)$")
    public void verifyLessThanOrEqualToFloat(String path, String expected) throws Throwable {
        assertThat((Float) actual.json(path), lessThanOrEqualTo(parseFloat(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}=float: (\\d+\\.\\d+)$")
    public void verifyVariableEqualsFloat(String name, String expected) {
        assertThat(variables.get(name), is(parseFloat(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}!=float: (\\d+\\.\\d+)$")
    public void verifyVariableNotEqualsFloat(String name, String expected) {
        assertThat(variables.get(name), not(parseFloat(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}>float: (\\d+\\.\\d+)$")
    public void verifyVariableGreaterThanFloat(String name, String expected) {
        assertThat((Float) variables.get(name), greaterThan(parseFloat(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}>=float: (\\d+\\.\\d+)$")
    public void verifyVariableGreaterThanOrEqualToFloat(String name, String expected) {
        assertThat((Float) variables.get(name), greaterThanOrEqualTo(parseFloat(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}<float: (\\d+\\.\\d+)$")
    public void verifyVariableLessThanFloat(String name, String expected) {
        assertThat((Float) variables.get(name), lessThan(parseFloat(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}<=float: (\\d+\\.\\d+)$")
    public void verifyVariableLessThanOrEqualToFloat(String name, String expected) {
        assertThat((Float) variables.get(name), lessThanOrEqualTo(parseFloat(expected)));
    }
}
