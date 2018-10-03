package com.github.extension;

import com.github.jakimli.pandaria.domain.VerificationContext;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class NotNullSteps {

    @Autowired
    VerificationContext actual;

    @Then("^verify: '([^\"]*)' not null$")
    public void verifyNotNull(String path) throws IOException {
        assertNotNull(actual.json(path));
    }
}
