package com.github.jakimli.pandaria.steps.verification;

import com.github.jakimli.pandaria.domain.VerificationContext;
import com.github.jakimli.pandaria.utils.JsonContext;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

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
    VerificationContext toBeVerified;

    private Object json(String path) throws IOException {
        return JsonContext.json(toBeVerified.toJsonObject()).path(path);
    }

    @Then("^verify: '([^\"]*)'=float: (\\d+\\.\\d+)$")
    public void verifyEqualsFloat(String path, String expected) throws Throwable {
        assertThat(json(path), is(parseFloat(expected)));
    }

    @Then("^verify: '([^\"]*)'!=float: (\\d+\\.\\d+)$")
    public void verifyNotEqualsFloat(String path, String expected) throws Throwable {
        assertThat(json(path), not(parseFloat(expected)));
    }

    @Then("^verify: '([^\"]*)'>float: (\\d+\\.\\d+)$")
    public void verifyGreaterThanFloat(String path, String expected) throws Throwable {
        assertThat((Float) json(path), greaterThan(parseFloat(expected)));
    }

    @Then("^verify: '([^\"]*)'>=float: (\\d+\\.\\d+)$")
    public void verifyGreaterThanOrEqualToFloat(String path, String expected) throws Throwable {
        assertThat((Float) json(path), greaterThanOrEqualTo(parseFloat(expected)));
    }

    @Then("^verify: '([^\"]*)'<float: (\\d+\\.\\d+)$")
    public void verifyLessThanFloat(String path, String expected) throws Throwable {
        assertThat((Float) json(path), lessThan(parseFloat(expected)));
    }

    @Then("^verify: '([^\"]*)'<=float: (\\d+\\.\\d+)$")
    public void verifyLessThanOrEqualToFloat(String path, String expected) throws Throwable {
        assertThat((Float) json(path), lessThanOrEqualTo(parseFloat(expected)));
    }
}
