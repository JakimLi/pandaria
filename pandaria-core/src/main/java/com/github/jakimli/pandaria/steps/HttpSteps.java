package com.github.jakimli.pandaria.steps;

import com.github.jakimli.pandaria.domain.Variables;
import com.github.jakimli.pandaria.domain.http.GlobalHeaders;
import com.github.jakimli.pandaria.domain.wait.Wait;
import com.github.jakimli.pandaria.domain.http.HttpContext;
import com.github.jakimli.pandaria.domain.http.client.HttpMethod;
import com.github.jakimli.pandaria.domain.VerificationContext;
import com.github.jakimli.pandaria.domain.FeatureConfiguration;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import static com.github.jakimli.pandaria.utils.FileUtil.read;
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

    @Autowired
    Wait wait;

    @Autowired
    GlobalHeaders headers;

    @Given("^uri: ([^\"]*)$")
    public void uri(String url) {
        context.reset();
        context.uri(URI.create(variables.interpret(configuration.uri(url))));
    }

    @Given("^header: '([^\"]*)'='([^\"]*)'$")
    public void header(String key, String value) {
        context.requestHeader(key, value);
    }

    @Given("^attachment: ([^\"]*)")
    public void attachment(String file) {
        context.attachment(configuration.classpathFile(file));
    }

    @Given("^query parameter: '([^\"]*)'='([^\"]*)'$")
    public void queryParameterFromLiteral(String name, String value) {
        context.queryParameter(name, value);
    }

    @Given("^query parameter: '([^\"]*)'=\"([^\"]*)\"$")
    public void queryParameterFromString(String name, String value) {
        context.queryParameter(name, variables.interpret(value));
    }

    @Given("^request body:$")
    public void requestBody(String body) {
        context.requestBody(variables.interpret(body));
    }

    @Given("^request body: ([^\"]*)$")
    public void requestBodyFromFile(String file) throws IOException {
        String fileName = configuration.classpathFile(file);
        context.requestBody(variables.interpret(read(fileName)));
    }

    @Given("^cookie: '([^\"]*)'='([^\"]*)'$")
    public void cookieFromLiteral(String key, String value) {
        context.cookie(key, value);
    }

    @Given("^cookie: '([^\"]*)'=\"([^\"]*)\"$")
    public void cookieFromString(String key, String value) {
        context.cookie(key, variables.interpret(value));
    }

    @When("^send: ([^\"]*)$")
    public void send(String method) {
        context.requestHeader(headers.getHeaders());
        context.method(HttpMethod.valueOf(method.toUpperCase()));
        context.send();
        verifier.toBeVerified(context.responseBody());
        wait.waitable(context);
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
