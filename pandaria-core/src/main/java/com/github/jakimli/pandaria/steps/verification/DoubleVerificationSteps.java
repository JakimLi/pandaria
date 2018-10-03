package com.github.jakimli.pandaria.steps.verification;

import com.github.jakimli.pandaria.domain.VerificationContext;
import com.github.jakimli.pandaria.utils.JsonContext;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

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
    VerificationContext toBeVerified;

    private Object json(String path) throws IOException {
        return JsonContext.json(toBeVerified.toJsonObject()).path(path);
    }

    @Then("^verify: '([^\"]*)'=double: (\\d+\\.\\d+)$")
    public void verifyEqualsDouble(String path, String expected) throws Throwable {
        assertThat(json(path), is(parseDouble(expected)));
    }

    @Then("^verify: '([^\"]*)'!=double: (\\d+\\.\\d+)$")
    public void verifyNotEqualsDouble(String path, String expected) throws Throwable {
        assertThat(json(path), not(parseDouble(expected)));
    }

    @Then("^verify: '([^\"]*)'>double: (\\d+\\.\\d+)$")
    public void verifyGreaterThanDouble(String path, String expected) throws Throwable {
        assertThat((Double) json(path), greaterThan(parseDouble(expected)));
    }

    @Then("^verify: '([^\"]*)'>=double: (\\d+\\.\\d+)$")
    public void verifyGreaterThanOrEqualToDouble(String path, String expected) throws Throwable {
        assertThat((Double) json(path), greaterThanOrEqualTo(parseDouble(expected)));
    }

    @Then("^verify: '([^\"]*)'<double: (\\d+\\.\\d+)$")
    public void verifyLessThanDouble(String path, String expected) throws Throwable {
        assertThat((Double) json(path), lessThan(parseDouble(expected)));
    }

    @Then("^verify: '([^\"]*)'<=double: (\\d+\\.\\d+)$")
    public void verifyLessThanOrEqualToDouble(String path, String expected) throws Throwable {
        assertThat((Double) json(path), lessThanOrEqualTo(parseDouble(expected)));
    }
}
