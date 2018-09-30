package com.github.jakimli.panda.steps;

import com.github.jakimli.panda.domain.Variables;
import com.github.jakimli.panda.domain.http.HttpContext;
import com.github.jakimli.panda.domain.http.client.HttpMethod;
import com.github.jakimli.panda.domain.verification.VerificationContext;
import com.github.jakimli.panda.domain.FeatureConfiguration;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import static com.github.jakimli.panda.utils.FileUtil.read;
import static java.lang.String.join;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class HttpSteps {

    @Autowired
    HttpContext context;

    @Autowired
    VerificationContext verifier;

    @Autowired
    FeatureConfiguration configuration;

    @Autowired
    Variables variables;

    @Given("^uri: ([^\"]*)$")
    public void uri(String url) {
        context.reset();
        context.uri(URI.create(variables.interpret(configuration.uri(url))));
    }

    @Given("^header: '([^\"]*)'='([^\"]*)'$")
    public void header(String key, String value) {
        context.requestHeader(key, value);
    }

    @Given("^request body:$")
    public void requestBody(String body) {
        context.requestBody(body);
    }

    @Given("^request body: ([^\"]*)$")
    public void requestBodyFromFile(String file) throws IOException {
        String fileName = configuration.classpathFile(file);
        context.requestBody(variables.interpret(read(fileName)));
    }

    @When("^send: ([^\"]*)$")
    public void send(String method) {
        context.method(HttpMethod.valueOf(method));
        context.send();
        verifier.toBeVerified(context.responseBody());
    }

    @Then("^status: (\\d+)")
    public void verifyStatus(int status) {
        assertThat(context.status(), is(status));
    }

    @Then("^response body:$")
    public void verifyResponseBody(String expected) {
        assertThat(context.responseBody(), is(variables.interpret(expected)));
    }

    @Then("^response body: ([^\"]*)$")
    public void verifyResponseBodyAgainstFile(String file) throws IOException {
        String content = variables.interpret(read(configuration.classpathFile(file)));
        assertThat(context.responseBody(), is(content));
    }

    @Then("^response header: '([^\"]*)'='([^\"]*)'$")
    public void verifyResponseHeader(String key, String expected) {
        List<String> values = context.responseHeader(key);
        assertNotNull(values);
        assertThat(join(",", values), is(expected));
    }
}
