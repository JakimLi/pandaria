package com.github.jakimli.pandaria.steps.verification;

import com.github.jakimli.pandaria.domain.variable.Variables;
import com.github.jakimli.pandaria.domain.VerificationContext;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class StringVerificationSteps {

    @Autowired
    VerificationContext toBeVerified;

    @Autowired
    Variables variables;

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
        assertThat(toBeVerified.json(path), is(variables.interpret(expected)));
    }

    @Then("^verify: '([^\"]*)'!=\"([^\"]*)\"$")
    public void verifyNotEqualsString(String path, String expected) throws Throwable {
        assertThat(toBeVerified.json(path), not(variables.interpret(expected)));
    }

    @Then("^verify: '([^\"]*)' contains: '([^\"]*)'$")
    public void verifyContainsLiteral(String path, String contained) throws Throwable {
        assertThat(String.valueOf(toBeVerified.json(path)), containsString(contained));
    }

    @Then("^verify: '([^\"]*)' contains: \"([^\"]*)\"$")
    public void verifyContainsString(String path, String contained) throws Throwable {
        assertThat(String.valueOf(toBeVerified.json(path)), containsString(variables.interpret(contained)));
    }

    @Then("^verify: '([^\"]*)' starts with: '([^\"]*)'$")
    public void verifyStartsWithLiteral(String path, String prefix) throws Throwable {
        assertThat(String.valueOf(toBeVerified.json(path)), startsWith(prefix));
    }

    @Then("^verify: '([^\"]*)' starts with: \"([^\"]*)\"$")
    public void verifyStartsWithString(String path, String prefix) throws Throwable {
        assertThat(String.valueOf(toBeVerified.json(path)), startsWith(variables.interpret(prefix)));
    }

    @Then("^verify: '([^\"]*)' ends with: '([^\"]*)'$")
    public void verifyEndsWithLiteral(String path, String prefix) throws Throwable {
        assertThat(String.valueOf(toBeVerified.json(path)), endsWith(prefix));
    }

    @Then("^verify: '([^\"]*)' ends with: \"([^\"]*)\"$")
    public void verifyEndsWithString(String path, String prefix) throws Throwable {
        assertThat(String.valueOf(toBeVerified.json(path)), endsWith(variables.interpret(prefix)));
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
    public void verifyVariableEqualsLiteral(String varName, String expected) {
        assertThat(variables.get(varName), is(expected));
    }

    @Then("^verify: \\$\\{([^\"]*)}=\"([^\"]*)\"$")
    public void verifyVariableEqualsString(String varName, String expected) {
        assertThat(variables.get(varName), is(variables.interpret(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)} contains: '([^\"]*)'$")
    public void verifyVariableContainsLiteral(String varName, String expected) {
        assertThat(String.valueOf(variables.get(varName)), containsString(expected));
    }

    @Then("^verify: \\$\\{([^\"]*)} contains: \"([^\"]*)\"$")
    public void verifyVariableContainsString(String varName, String expected) {
        assertThat(String.valueOf(variables.get(varName)), containsString(variables.interpret(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)} starts with: '([^\"]*)'$")
    public void verifyVariableStartsWithLiteral(String varName, String prefix) {
        assertThat(String.valueOf(variables.get(varName)), startsWith(prefix));
    }

    @Then("^verify: \\$\\{([^\"]*)} starts with: \"([^\"]*)\"$")
    public void verifyVariableStartsWithString(String varName, String prefix) {
        assertThat(String.valueOf(variables.get(varName)), startsWith(variables.interpret(prefix)));
    }

    @Then("^verify: \\$\\{([^\"]*)} ends with: '([^\"]*)'$")
    public void verifyVariableEndsWithLiteral(String varName, String prefix) {
        assertThat(String.valueOf(variables.get(varName)), endsWith(prefix));
    }

    @Then("^verify: \\$\\{([^\"]*)} ends with: \"([^\"]*)\"$")
    public void verifyVariableEndsWithString(String varName, String prefix) {
        assertThat(String.valueOf(variables.get(varName)), endsWith(variables.interpret(prefix)));
    }

    @Then("^verify: \\$\\{([^\"]*)} matches: '([^\"]*)'$")
    public void verifyVariableMatchesRegex(String varName, String regex) {
        assertTrue(String.valueOf(variables.get(varName)).matches(regex));
    }

    @Then("^verify: \\$\\{([^\"]*)} length: (\\d+)$")
    public void verifyVariableStringLength(String varName, int length) {
        assertThat(String.valueOf(variables.get(varName)).length(), is(length));
    }

    @Then("^verify: \\$\\{([^\"]*)}!='([^\"]*)'$")
    public void verifyVariableNotEqualsLiteral(String varName, String expected) {
        assertThat(variables.get(varName), not(expected));
    }

    @Then("^verify: \\$\\{([^\"]*)}!=\"([^\"]*)\"$")
    public void verifyVariableNotEqualsString(String varName, String expected) {
        assertThat(variables.get(varName), not(variables.interpret(expected)));
    }
}
