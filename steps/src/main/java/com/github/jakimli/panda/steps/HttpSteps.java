package com.github.jakimli.panda.steps;

import com.github.jakimli.panda.domain.http.HttpContext;
import com.github.jakimli.panda.domain.http.client.HttpMethod;
import com.github.jakimli.panda.domain.verification.JsonVerificationContext;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URI;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HttpSteps {

    @Autowired
    HttpContext context;

    @Autowired
    JsonVerificationContext verifier;

    @Given("^uri: ([^\"]*)$")
    public void uri(String url) {
        context.uri(URI.create(url));
    }

    @When("^send: ([^\"]*)$")
    public void send(String method) throws IOException {
        context.method(HttpMethod.valueOf(method));
        context.send();
        verifier.toBeVerified(context.responseBody());
    }

    @Then("^status: (\\d+)")
    public void verifyStatus(int status) {
        assertThat(context.status(), is(status));
    }
}
