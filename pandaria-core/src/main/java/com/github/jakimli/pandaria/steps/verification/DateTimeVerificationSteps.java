package com.github.jakimli.pandaria.steps.verification;

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
    VerificationContext toBeVerified;

    @Then("^verify: '([^\"]*)'=datetime: '([^\"]*)' pattern: '([^\"]*)'$")
    public void verifyDateWithPattern(String path, String date, String pattern) throws IOException {
        assertThat(new DateTime(toBeVerified.json(path)), is(forPattern(pattern).parseDateTime(date)));
    }
}
