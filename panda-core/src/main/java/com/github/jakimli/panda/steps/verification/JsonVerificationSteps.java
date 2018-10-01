package com.github.jakimli.panda.steps.verification;

import com.github.jakimli.panda.domain.Variables;
import com.github.jakimli.panda.domain.verification.VerificationContext;
import com.github.jakimli.panda.utils.JsonContext;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static java.lang.Double.parseDouble;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

public class JsonVerificationSteps {

    @Autowired
    VerificationContext toBeVerified;

    @Autowired
    Variables variables;

    private Object json(String path) throws IOException {
        return JsonContext.json(toBeVerified.toJsonObject()).path(path);
    }

    @Then("^verify: '([^\"]*)'='([^\"]*)'$")
    public void verifyEqualsLiteral(String path, final String expected) throws Throwable {
        assertThat(json(path), is(expected));
    }

    @Then("^verify: '([^\"]*)'!='([^\"]*)'$")
    public void verifyNotEqualsLiteral(String path, final String expected) throws Throwable {
        assertThat(json(path), not(expected));
    }

    @Then("^verify: '([^\"]*)'=\"([^\"]*)\"$")
    public void verifyEqualsString(String path, String expected) throws Throwable {
        assertThat(json(path), is(variables.interpret(expected)));
    }

    @Then("^verify: '([^\"]*)'!=\"([^\"]*)\"$")
    public void verifyNotEqualsString(String path, String expected) throws Throwable {
        assertThat(json(path), not(variables.interpret(expected)));
    }

    @Then("^verify: '([^\"]*)' contains: '([^\"]*)'$")
    public void verifyContainsLiteral(String path, String contained) throws Throwable {
        assertThat(String.valueOf(json(path)), containsString(contained));
    }

    @Then("^verify: '([^\"]*)' contains: \"([^\"]*)\"$")
    public void verifyContainsString(String path, String contained) throws Throwable {
        assertThat(String.valueOf(json(path)), containsString(variables.interpret(contained)));
    }

    @Then("^verify: '([^\"]*)'=(\\d+)$")
    public void verifyEqualsInteger(String path, final int expected) throws Throwable {
        assertThat(json(path), is(expected));
    }

    @Then("^verify: '([^\"]*)'!=(\\d+)$")
    public void verifyNotEqualsInteger(String path, final int expected) throws Throwable {
        assertThat(json(path), not(expected));
    }

    @Then("^verify: '([^\"]*)'>(\\d+)$")
    public void verifyGreaterThanInteger(String path, final int expected) throws Throwable {
        assertThat((int) json(path), greaterThan(expected));
    }

    @Then("^verify: '([^\"]*)'<(\\d+)$")
    public void verifyLessThanInteger(String path, final int expected) throws Throwable {
        assertThat((int) json(path), lessThan(expected));
    }

    @Then("^verify: '([^\"]*)'=(\\d+\\.\\d+)$")
    public void verifyEqualsDouble(String path, final String expected) throws Throwable {
        assertThat(json(path), is(parseDouble(expected)));
    }

    @Then("^verify: '([^\"]*)'!=(\\d+\\.\\d+)$")
    public void verifyNotEqualsDouble(String path, final String expected) throws Throwable {
        assertThat(json(path), not(parseDouble(expected)));
    }

    @Then("^verify: '([^\"]*)'>(\\d+\\.\\d+)$")
    public void verifyGreaterThanDouble(String path, final String expected) throws Throwable {
        assertThat((double) json(path), greaterThan(parseDouble(expected)));
    }

    @Then("^verify: '([^\"]*)'<(\\d+\\.\\d+)$")
    public void verifyLessThanDouble(String path, final String expected) throws Throwable {
        assertThat((double) json(path), lessThan(parseDouble(expected)));
    }
}
