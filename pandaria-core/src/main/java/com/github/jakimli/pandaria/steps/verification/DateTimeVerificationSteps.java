package com.github.jakimli.pandaria.steps.verification;

import com.github.jakimli.pandaria.domain.Variables;
import com.github.jakimli.pandaria.domain.VerificationContext;
import cucumber.api.java.en.Then;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.joda.time.format.DateTimeFormat.forPattern;
import static org.junit.Assert.assertThat;

public class DateTimeVerificationSteps {

    @Autowired
    VerificationContext actual;

    @Autowired
    Variables variables;

    @Then("^verify: '([^\"]*)'=datetime: '([^\"]*)' pattern: '([^\"]*)'$")
    public void verifyDateWithPattern(String path, String date, String pattern) throws IOException {
        assertThat(new DateTime(actual.json(path)), is(forPattern(pattern).parseDateTime(date)));
    }

    @Then("^verify: \\$\\{([^\"]*)}=datetime: '([^\"]*)' pattern: '([^\"]*)'$")
    public void verifyVariableDateWithPattern(String name, String date, String pattern) throws IOException {
        assertThat(new DateTime(variables.get(name)), is(forPattern(pattern).parseDateTime(date)));
    }
}
