package com.github.jakimli.pandaria.steps.verification;

import com.github.jakimli.pandaria.domain.VerificationContext;
import com.github.jakimli.pandaria.domain.variable.Variables;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class DecimalVerificationSteps {

    @Autowired
    VerificationContext actual;

    @Autowired
    Variables variables;

    @Then("^verify: '([^\"]*)'=decimal: ([^\"]*)$")
    public void verifyEqualsDecimal(String path, String expected) throws Throwable {
        assertEquals(0, ((BigDecimal) actual.json(path)).compareTo(new BigDecimal(expected)));
    }

    @Then("^verify: '([^\"]*)'!=decimal: ([^\"]*)$")
    public void verifyNotEqualsDecimal(String path, String expected) throws Throwable {
        assertNotEquals(0, ((BigDecimal) actual.json(path)).compareTo(new BigDecimal(expected)));
    }

    @Then("^verify: '([^\"]*)'>decimal: ([^\"]*)$")
    public void verifyGreaterThanDecimal(String path, String expected) throws Throwable {
        assertEquals(1, ((BigDecimal) actual.json(path)).compareTo(new BigDecimal(expected)));
    }

    @Then("^verify: '([^\"]*)'>=decimal: ([^\"]*)$")
    public void verifyGreaterThanOrEqualToDecimal(String path, String expected) throws Throwable {
        assertTrue(((BigDecimal) this.actual.json(path)).compareTo(new BigDecimal(expected)) >= 0);
    }

    @Then("^verify: '([^\"]*)'<decimal: ([^\"]*)$")
    public void verifyLessThanDecimal(String path, String expected) throws Throwable {
        assertEquals(-1, ((BigDecimal) actual.json(path)).compareTo(new BigDecimal(expected)));
    }

    @Then("^verify: '([^\"]*)'<=decimal: ([^\"]*)$")
    public void verifyLessThanOrEqualToDecimal(String path, String expected) throws Throwable {
        assertTrue(((BigDecimal) this.actual.json(path)).compareTo(new BigDecimal(expected)) <= 0);
    }
    
    @Then("^verify: \\$\\{([^\"]*)}=decimal: ([^\"]*)$")
    public void verifyVariableEqualsDecimal(String expression, String expected) {
        assertEquals(0, ((BigDecimal) variables.get(expression)).compareTo(new BigDecimal(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}!=decimal: ([^\"]*)$")
    public void verifyVariableNotEqualsDecimal(String expression, String expected) {
        assertNotEquals(0, ((BigDecimal) variables.get(expression)).compareTo(new BigDecimal(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}>decimal: ([^\"]*)$")
    public void verifyVariableGreaterThanDecimal(String expression, String expected) {
        assertEquals(1, ((BigDecimal) variables.get(expression)).compareTo(new BigDecimal(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}>=decimal: ([^\"]*)$")
    public void verifyVariableGreaterThanOrEqualToDecimal(String expression, String expected) {
        assertTrue(((BigDecimal) this.variables.get(expression)).compareTo(new BigDecimal(expected)) >= 0);
    }

    @Then("^verify: \\$\\{([^\"]*)}<decimal: ([^\"]*)$")
    public void verifyVariableLessThanDecimal(String expression, String expected) {
        assertEquals(-1, ((BigDecimal) variables.get(expression)).compareTo(new BigDecimal(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}<=decimal: ([^\"]*)$")
    public void verifyVariableLessThanOrEqualToDecimal(String expression, String expected) {
        assertTrue(((BigDecimal) this.variables.get(expression)).compareTo(new BigDecimal(expected)) <= 0);
    }
}
