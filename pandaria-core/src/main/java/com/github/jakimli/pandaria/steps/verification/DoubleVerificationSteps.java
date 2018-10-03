package com.github.jakimli.pandaria.steps.verification;

import com.github.jakimli.pandaria.domain.Variables;
import com.github.jakimli.pandaria.domain.VerificationContext;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import static java.lang.Double.parseDouble;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

public class DoubleVerificationSteps {

    @Autowired
    Variables variables;

    @Autowired
    VerificationContext toBeVerified;

    @Then("^verify: '([^\"]*)'=double: (\\d+\\.\\d+)$")
    public void verifyEqualsDouble(String path, String expected) throws Throwable {
        assertThat(toBeVerified.json(path), is(parseDouble(expected)));
    }

    @Then("^verify: '([^\"]*)'!=double: (\\d+\\.\\d+)$")
    public void verifyNotEqualsDouble(String path, String expected) throws Throwable {
        assertThat(toBeVerified.json(path), not(parseDouble(expected)));
    }

    @Then("^verify: '([^\"]*)'>double: (\\d+\\.\\d+)$")
    public void verifyGreaterThanDouble(String path, String expected) throws Throwable {
        assertThat((Double) toBeVerified.json(path), greaterThan(parseDouble(expected)));
    }

    @Then("^verify: '([^\"]*)'>=double: (\\d+\\.\\d+)$")
    public void verifyGreaterThanOrEqualToDouble(String path, String expected) throws Throwable {
        assertThat((Double) toBeVerified.json(path), greaterThanOrEqualTo(parseDouble(expected)));
    }

    @Then("^verify: '([^\"]*)'<double: (\\d+\\.\\d+)$")
    public void verifyLessThanDouble(String path, String expected) throws Throwable {
        assertThat((Double) toBeVerified.json(path), lessThan(parseDouble(expected)));
    }

    @Then("^verify: '([^\"]*)'<=double: (\\d+\\.\\d+)$")
    public void verifyLessThanOrEqualToDouble(String path, String expected) throws Throwable {
        assertThat((Double) toBeVerified.json(path), lessThanOrEqualTo(parseDouble(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}=double: (\\d+\\.\\d+)$")
    public void verifyVariableEqualsDouble(String varName, String expected) {
        assertThat(variables.get(varName), is(Double.parseDouble(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}!=double: (\\d+\\.\\d+)$")
    public void verifyVariableNotEqualsDouble(String varName, String expected) {
        assertThat(variables.get(varName), not(Double.parseDouble(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}>double: (\\d+\\.\\d+)$")
    public void verifyVariableGreaterThanDouble(String varName, String expected) {
        assertThat((double) variables.get(varName), greaterThan(Double.parseDouble(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}>=double: (\\d+\\.\\d+)$")
    public void verifyVariableGreaterThanOrEqualToDouble(String varName, String expected) {
        assertThat((double) variables.get(varName), greaterThanOrEqualTo(Double.parseDouble(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}<double: (\\d+\\.\\d+)$")
    public void verifyVariableLessThanDouble(String varName, String expected) {
        assertThat((double) variables.get(varName), lessThan(Double.parseDouble(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}<=double: (\\d+\\.\\d+)$")
    public void verifyVariableLessThanOrEqualToDouble(String varName, String expected) {
        assertThat((double) variables.get(varName), lessThanOrEqualTo(Double.parseDouble(expected)));
    }
}
