package com.github.jakimli.pandaria.steps.verification;

import com.github.jakimli.pandaria.domain.Variables;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class VariableVerificationSteps {
    @Autowired
    Variables variables;

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

    @Then("^verify: \\$\\{([^\"]*)}=\\$\\{([^\"]*)}$")
    public void verifyVariableEqualsVariable(String varName, String anotherVar) {
        assertThat(variables.get(varName), is(variables.get(anotherVar)));
    }

    @Then("^verify: \\$\\{([^\"]*)}!=\\$\\{([^\"]*)}$")
    public void verifyVariableNotEqualsVariable(String varName, String anotherVar) {
        assertThat(variables.get(varName), not(variables.get(anotherVar)));
    }

    @Then("^verify: \\$\\{([^\"]*)}!='([^\"]*)'$")
    public void verifyVariableNotEqualsLiteral(String varName, String expected) {
        assertThat(variables.get(varName), not(expected));
    }

    @Then("^verify: \\$\\{([^\"]*)}!=\"([^\"]*)\"$")
    public void verifyVariableNotEqualsString(String varName, String expected) {
        assertThat(variables.get(varName), not(variables.interpret(expected)));
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
