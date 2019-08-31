package com.github.jakimli.pandaria.steps;

import com.github.jakimli.pandaria.domain.FeatureConfiguration;
import com.github.jakimli.pandaria.domain.GraphqlContext;
import com.github.jakimli.pandaria.domain.VerificationContext;
import com.github.jakimli.pandaria.domain.http.HttpContext;
import com.github.jakimli.pandaria.domain.expression.Expressions;
import com.github.jakimli.pandaria.domain.wait.Wait;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URI;

import static com.github.jakimli.pandaria.domain.http.client.HttpMethod.POST;
import static com.github.jakimli.pandaria.utils.FileUtil.read;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class GraphqlSteps {

    @Autowired
    GraphqlContext graphql;

    @Autowired
    Expressions expressions;

    @Autowired
    HttpContext http;

    @Autowired
    FeatureConfiguration configuration;

    @Autowired
    VerificationContext verifier;

    @Autowired
    Wait wait;

    @Given("^graphql:$")
    public void graphql(String body) {
        graphql.reset();
        graphql.query(expressions.evaluate(body));
    }

    @Given("^graphql: ([^\"]*)$")
    public void graphqlFromFile(String file) throws IOException {
        graphql.reset();
        String fileName = configuration.classpathFile(file);
        graphql.query(expressions.evaluate(read(fileName)));
    }

    @Given("^variables:$")
    public void graphqlVariables(String variables) {
        String graphqlVariables = this.expressions.evaluate(variables);

        assertNotNull(graphqlVariables);
        assertFalse(graphqlVariables.isEmpty());
        graphql.variables(graphqlVariables);
    }

    @Given("^variables: ([^\"]*)$")
    public void graphqlVariablesFromFile(String file) throws IOException {
        String fileName = configuration.classpathFile(file);
        String graphqlVariables = expressions.evaluate(read(fileName));

        assertNotNull(graphqlVariables);
        assertFalse(graphqlVariables.isEmpty());
        graphql.variables(graphqlVariables);
    }

    @Given("^operation: ([^\"]*)$")
    public void operationName(String operation) {
        String operationName = expressions.evaluate(operation);

        assertNotNull(operationName);
        assertFalse(operationName.isEmpty());
        graphql.operationName(operationName);
    }

    @When("send")
    public void sendGraphql() {
        http.reset();
        http.uri(URI.create(expressions.evaluate(configuration.baseUri())));
        http.requestBody(graphql.request());
        http.method(POST);
        http.send();
        verifier.toBeVerified(http.responseBody());
        wait.waitable(http);
    }
}
