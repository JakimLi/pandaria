package com.github.jakimli.pandaria.steps.verification;

import com.github.jakimli.pandaria.domain.Variables;
import com.github.jakimli.pandaria.domain.VerificationContext;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

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
    public void verifyVariableEqualsDecimal(String name, String expected) throws Throwable {
        assertEquals(0, ((BigDecimal) variables.get(name)).compareTo(new BigDecimal(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}!=decimal: ([^\"]*)$")
    public void verifyVariableNotEqualsDecimal(String name, String expected) throws Throwable {
        assertNotEquals(0, ((BigDecimal) variables.get(name)).compareTo(new BigDecimal(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}>decimal: ([^\"]*)$")
    public void verifyVariableGreaterThanDecimal(String name, String expected) throws Throwable {
        assertEquals(1, ((BigDecimal) variables.get(name)).compareTo(new BigDecimal(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}>=decimal: ([^\"]*)$")
    public void verifyVariableGreaterThanOrEqualToDecimal(String name, String expected) throws Throwable {
        assertTrue(((BigDecimal) this.variables.get(name)).compareTo(new BigDecimal(expected)) >= 0);
    }

    @Then("^verify: \\$\\{([^\"]*)}<decimal: ([^\"]*)$")
    public void verifyVariableLessThanDecimal(String name, String expected) throws Throwable {
        assertEquals(-1, ((BigDecimal) variables.get(name)).compareTo(new BigDecimal(expected)));
    }

    @Then("^verify: \\$\\{([^\"]*)}<=decimal: ([^\"]*)$")
    public void verifyVariableLessThanOrEqualToDecimal(String name, String expected) throws Throwable {
        assertTrue(((BigDecimal) this.variables.get(name)).compareTo(new BigDecimal(expected)) <= 0);
    }
}
