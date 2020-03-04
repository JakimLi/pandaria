package com.github.jakimli.pandaria.steps.verification;

import com.github.jakimli.pandaria.domain.VerificationContext;
import com.github.jakimli.pandaria.domain.expression.Expressions;
import com.github.jakimli.pandaria.domain.variable.Variables;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class StringVerificationSteps {

    @Autowired
    VerificationContext toBeVerified;

    @Autowired
    Variables variables;
    
    @Autowired
    Expressions expressions;

    @Then("^verify: '([^\"]*)'='([^\"]*)'$")
    public void verifyEqualsLiteral(String path, final String expected) throws Throwable {
        assertThat(toBeVerified.json(path), is(expected));
    }

    @Then("^verify: '([^\"]*)'!='([^\"]*)'$")
    public void verifyNotEqualsLiteral(String path, final String expected) throws Throwable {
        assertThat(toBeVerified.json(path), not(expected));
    }

    @Then("^verify: '([^\"]*)'=\"([^\"]*)\"$")
    public void verifyEqualsString(String path, String expected) throws Throwable {
        assertThat(toBeVerified.json(path), is(expressions.evaluate(expected)));
    }

    @Then("^verify: '([^\"]*)'!=\"([^\"]*)\"$")
    public void verifyNotEqualsString(String path, String expected) throws Throwable {
        assertThat(toBeVerified.json(path), not(expressions.evaluate(expected)));
    }

    @Then("^verify: '([^\"]*)' contains: '([^\"]*)'$")
    public void verifyContainsLiteral(String path, String contained) throws Throwable {
        assertThat(String.valueOf(toBeVerified.json(path)), containsString(contained));
    }

    @Then("^verify: '([^\"]*)' contains: \"([^\"]*)\"$")
    public void verifyContainsString(String path, String contained) throws Throwable {
        assertThat(String.valueOf(toBeVerified.json(path)), containsString(expressions.evaluate(contained)));
    }

    @Then("^verify: '([^\"]*)' starts with: '([^\"]*)'$")
    public void verifyStartsWithLiteral(String path, String prefix) throws Throwable {
        assertThat(String.valueOf(toBeVerified.json(path)), startsWith(prefix));
    }

    @Then("^verify: '([^\"]*)' starts with: \"([^\"]*)\"$")
    public void verifyStartsWithString(String path, String prefix) throws Throwable {
        assertThat(String.valueOf(toBeVerified.json(path)), startsWith(expressions.evaluate(prefix)));
    }

    @Then("^verify: '([^\"]*)' ends with: '([^\"]*)'$")
    public void verifyEndsWithLiteral(String path, String prefix) throws Throwable {
        assertThat(String.valueOf(toBeVerified.json(path)), endsWith(prefix));
    }

    @Then("^verify: '([^\"]*)' ends with: \"([^\"]*)\"$")
    public void verifyEndsWithString(String path, String prefix) throws Throwable {
        assertThat(String.valueOf(toBeVerified.json(path)), endsWith(expressions.evaluate(prefix)));
    }

    @Then("^verify: '([^\"]*)' length: (\\d+)$")
    public void verifyStringLength(String path, int length) throws Throwable {
        assertThat(String.valueOf(toBeVerified.json(path)).length(), is(length));
    }

    @Then("^verify: '([^\"]*)' matches: '([^\"]*)'$")
    public void verifyMatchesRegex(String path, String regex) throws Throwable {
        assertTrue(String.valueOf(toBeVerified.json(path)).matches(regex));
    }

    @Then("^verify: \\$\\{([^\"]*)}='([^\"]*)'$")
    public void verifyVariableEqualsLiteral(String expression, String expected) {
        assertThat(variables.get(expression), is(expected));
    }

    @Then("^verify: \\$\\{([^\"]*)}=\"([^\"]*)\"$")
    public void verifyVariableEqualsString(String expression, String expected) {
        assertThat(variables.get(expression), is(expressions.evaluate(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)} contains: '([^\"]*)'$")
    public void verifyVariableContainsLiteral(String expression, String expected) {
        assertThat(String.valueOf(variables.get(expression)), containsString(expected));
    }

    @Then("^verify: \\$\\{([^\"]*)} contains: \"([^\"]*)\"$")
    public void verifyVariableContainsString(String expression, String expected) {
        assertThat(String.valueOf(variables.get(expression)), containsString(expressions.evaluate(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)} starts with: '([^\"]*)'$")
    public void verifyVariableStartsWithLiteral(String expression, String prefix) {
        assertThat(String.valueOf(variables.get(expression)), startsWith(prefix));
    }

    @Then("^verify: \\$\\{([^\"]*)} starts with: \"([^\"]*)\"$")
    public void verifyVariableStartsWithString(String expression, String prefix) {
        assertThat(String.valueOf(variables.get(expression)), startsWith(expressions.evaluate(prefix)));
    }

    @Then("^verify: \\$\\{([^\"]*)} ends with: '([^\"]*)'$")
    public void verifyVariableEndsWithLiteral(String expression, String prefix) {
        assertThat(String.valueOf(variables.get(expression)), endsWith(prefix));
    }

    @Then("^verify: \\$\\{([^\"]*)} ends with: \"([^\"]*)\"$")
    public void verifyVariableEndsWithString(String expression, String prefix) {
        assertThat(String.valueOf(variables.get(expression)), endsWith(expressions.evaluate(prefix)));
    }

    @Then("^verify: \\$\\{([^\"]*)} matches: '([^\"]*)'$")
    public void verifyVariableMatchesRegex(String expression, String regex) {
        assertTrue(String.valueOf(variables.get(expression)).matches(regex));
    }

    @Then("^verify: \\$\\{([^\"]*)} length: (\\d+)$")
    public void verifyVariableStringLength(String expression, int length) {
        assertThat(String.valueOf(variables.get(expression)).length(), is(length));
    }

    @Then("^verify: \\$\\{([^\"]*)}!='([^\"]*)'$")
    public void verifyVariableNotEqualsLiteral(String expression, String expected) {
        assertThat(variables.get(expression), not(expected));
    }

    @Then("^verify: \\$\\{([^\"]*)}!=\"([^\"]*)\"$")
    public void verifyVariableNotEqualsString(String expression, String expected) {
        assertThat(variables.get(expression), not(expressions.evaluate(expected)));
    }
}
