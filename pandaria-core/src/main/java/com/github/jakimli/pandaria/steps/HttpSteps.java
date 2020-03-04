package com.github.jakimli.pandaria.steps;

import com.github.jakimli.pandaria.domain.FeatureConfiguration;
import com.github.jakimli.pandaria.domain.VerificationContext;
import com.github.jakimli.pandaria.domain.expression.Expressions;
import com.github.jakimli.pandaria.domain.http.HttpContext;
import com.github.jakimli.pandaria.domain.http.HttpGlobalHeaders;
import com.github.jakimli.pandaria.domain.http.ScenarioContext;
import com.github.jakimli.pandaria.domain.http.client.HttpMethod;
import com.github.jakimli.pandaria.domain.variable.Variables;
import com.github.jakimli.pandaria.domain.wait.Wait;
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
    private ScenarioContext scenarioContext;

    @Autowired
    VerificationContext verifier;

    @Autowired
    FeatureConfiguration configuration;

    @Autowired
    Variables variables;

    @Autowired
    Expressions expressions;

    @Autowired
    Wait wait;

    @Autowired
    HttpGlobalHeaders headers;

    @Given("^uri: ([^\"]*)$")
    public void uri(String url) {
        context.reset();
        context.uri(URI.create(expressions.evaluate(configuration.uri(url))));
    }

    @Given("^form: ([^\"]*)$")
    public void form(String url) {
        context.reset();
        context.form(URI.create(expressions.evaluate(configuration.uri(url))));
    }

    @Given("^header: '([^\"]*)'='([^\"]*)'$")
    public void headerFromLiteral(String key, String value) {
        context.requestHeader(key, value);
    }

    @Given("^header: '([^\"]*)'=\"([^\"]*)\"$")
    public void headerFromString(String key, String value) {
        context.requestHeader(key, expressions.evaluate(value));
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
        context.queryParameter(name, expressions.evaluate(value));
    }

    @Given("^request body:$")
    public void requestBody(String body) {
        context.requestBody(expressions.evaluate(body));
    }

    @Given("^request body: ([^\"]*)$")
    public void requestBodyFromFile(String file) throws IOException {
        String fileName = configuration.classpathFile(file);
        context.requestBody(expressions.evaluate(read(fileName)));
    }

    @Given("^field: ([^\"]*) value: ([^\"]*)$")
    public void fieldValueFromFile(String key, String valueFile) throws IOException {
        String fileName = configuration.classpathFile(valueFile);
        context.field(key, expressions.evaluate(read(fileName)));
    }

    @Given("^field: ([^\"]*) value:$")
    public void field(String key, String value) {
        context.field(key, expressions.evaluate(value));
    }

    @Given("^field: ([^\"]*) attachment: ([^\"]*)$")
    public void attachmentField(String key, String file) {
        context.attachment(key, configuration.classpathFile(file));
    }

    @Given("^cookie: '([^\"]*)'='([^\"]*)'$")
    public void cookieFromLiteral(String key, String value) {
        context.cookie(key, value);
    }

    @Given("^cookie: '([^\"]*)'=\"([^\"]*)\"$")
    public void cookieFromString(String key, String value) {
        context.cookie(key, expressions.evaluate(value));
    }

    @When("^send: ([^\"]*)$")
    public void send(String method) {
        context.addCookies(scenarioContext.getCookies());
        context.addGlobalRequestHeaders(scenarioContext.getRequestHeaders());
        context.addGlobalRequestHeaders(headers.getHeaders());
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
        assertThat(context.responseBody(), is(expressions.evaluate(expected)));
    }

    @Then("^response body: ([^\"]*)$")
    public void verifyResponseBodyAgainstFile(String file) throws IOException {
        String content = expressions.evaluate(read(configuration.classpathFile(file)));
        assertThat(context.responseBody(), is(content));
    }

    @Then("^response header: '([^\"]*)'='([^\"]*)'$")
    public void verifyResponseHeader(String key, String expected) {
        List<String> values = context.responseHeader(key);
        assertNotNull(values);
        assertThat(join(",", values), is(expected));
    }

    @Given("var: ([^\"' ]*)<-cookie:'([^\"]*)'$")
    public void defineVariableByCookieName(String key, String cookieName) {
        variables.assign(key, context.getCookieValue(cookieName));
    }
}
