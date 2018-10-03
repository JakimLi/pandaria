package com.github.jakimli.pandaria.steps.verification;

import com.github.jakimli.pandaria.domain.VerificationContext;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import static java.lang.Long.parseLong;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

public class LongVerificationSteps {

    @Autowired
    VerificationContext toBeVerified;

    @Then("^verify: '([^\"]*)'=long: ([^\"]*)$")
    public void verifyEqualsLong(String path, String expected) throws Throwable {
        assertThat(toBeVerified.json(path), is(parseLong(expected)));
    }

    @Then("^verify: '([^\"]*)'!=long: ([^\"]*)$")
    public void verifyNotEqualsLong(String path, String expected) throws Throwable {
        assertThat(toBeVerified.json(path), not(parseLong(expected)));
    }

    @Then("^verify: '([^\"]*)'>long: ([^\"]*)$")
    public void verifyGreaterThanLong(String path, String expected) throws Throwable {
        assertThat((Long) toBeVerified.json(path), greaterThan(parseLong(expected)));
    }

    @Then("^verify: '([^\"]*)'>=long: ([^\"]*)$")
    public void verifyGreaterThanOrEqualToLong(String path, String expected) throws Throwable {
        assertThat((Long) toBeVerified.json(path), greaterThanOrEqualTo(parseLong(expected)));
    }

    @Then("^verify: '([^\"]*)'<long: ([^\"]*)$")
    public void verifyLessThanLong(String path, String expected) throws Throwable {
        assertThat((Long) toBeVerified.json(path), lessThan(parseLong(expected)));
    }

    @Then("^verify: '([^\"]*)'<=long: ([^\"]*)$")
    public void verifyLessThanOrEqualToLong(String path, String expected) throws Throwable {
        assertThat((Long) toBeVerified.json(path), lessThanOrEqualTo(parseLong(expected)));
    }
}
