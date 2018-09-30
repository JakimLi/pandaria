package com.github.jakimli.panda.steps.verification;

import com.github.jakimli.panda.domain.Variables;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class VariableVerificationSteps {
    @Autowired
    Variables variables;

    @Then("^verify: \\$\\{([^\"]*)}='([^\"]*)'$")
    public void verifyVariableEqualsLiteral(String varName, String expected) {
        assertThat(variables.get(varName), is(expected));
    }

    @Then("^verify: \\$\\{([^\"]*)} contains: '([^\"]*)'$")
    public void verifyVariablContainsLiteral(String varName, String expected) {
        assertThat(String.valueOf(variables.get(varName)), containsString(expected));
    }

    @Then("^verify: \\$\\{([^\"]*)} contains: \"([^\"]*)\"$")
    public void verifyVariablContainsString(String varName, String expected) {
        assertThat(String.valueOf(variables.get(varName)), containsString(variables.interpret(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}=\"([^\"]*)\"$")
    public void verifyVariableEqualsString(String varName, String expected) {
        assertThat(variables.get(varName), is(variables.interpret(expected)));
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

    @Then("^verify: \\$\\{([^\"]*)}=(\\d+\\.\\d+)$")
    public void verifyVariableEqualsDouble(String varName, String expected) {
        assertThat(variables.get(varName), is(Double.parseDouble(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}!=(\\d+\\.\\d+)$")
    public void verifyVariableNotEqualsDouble(String varName, String expected) {
        assertThat(variables.get(varName), not(Double.parseDouble(expected)));
    }
}
