package com.github.jakimli.pandaria.steps;

import com.github.jakimli.pandaria.domain.FeatureConfiguration;
import com.github.jakimli.pandaria.domain.VerificationContext;
import com.github.jakimli.pandaria.domain.variable.Variables;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import javax.script.ScriptException;
import java.io.IOException;
import java.util.UUID;

import static com.github.jakimli.pandaria.utils.FileUtil.read;
import static com.github.jakimli.pandaria.utils.ScriptUtil.eval;

public class VariableDefinitionSteps {

    @Autowired
    Variables variables;

    @Autowired
    VerificationContext toBeVerified;

    @Autowired
    private FeatureConfiguration configuration;

    @Given("^var: ([^\"' ]*)='([^\"]*)'$")
    public void defineLiteralStringVariable(String key, String value) {
        variables.assign(key, value);
    }

    @Given("^var: ([^\"' ]*)=\"([^\"]*)\"$")
    public void defineStringVariable(String key, String value) {
        variables.assign(key, variables.interpret(value));
    }

    @Given("^var: ([^\"' ]*)=(\\d+)$")
    public void defineIntegerVariable(String key, int value) {
        variables.assign(key, value);
    }

    @Given("^var: ([^\"' ]*)=(\\d+\\.\\d+)$")
    public void defineDoubleVariable(String key, String value) {
        variables.assign(key, Double.parseDouble(value));
    }

    @Given("^var: ([^\"' ]*)<-'([^\"]*)'$")
    public void defineVariableExtractByJsonPath(String key, String path) throws IOException {
        variables.assign(key, toBeVerified.json(path));
    }

    @Given("^var: ([^\"' ]*)<-response body$")
    public void defineVariableExtractByResponseBody(String key) {
        variables.assign(key, toBeVerified.plainText());
    }

    @Given("^var: ([^\"' ]*)=code:$")
    public void defineVariableFromCodeBlock(String key, String code) throws ScriptException {
        variables.assign(key, eval(variables.interpret(code)));
    }

    @Given("^var: ([^\"' ]*)=code: ([^\"]*)$")
    public void defineVariableFromCodeInLine(String key, String code) throws ScriptException {
        variables.assign(key, eval(variables.interpret(code)));
    }

    @Given("^var: ([^\"' ]*)=code file: ([^\"]*)$")
    public void defineVariableFromFile(String key, String file) throws ScriptException, IOException {
        variables.assign(key, eval(variables.interpret(read(configuration.classpathFile(file)))));
    }


    @Given("^var: ([^\"' ]*)=random uuid")
    public void defineRandomUUIDVariable(String name) {
        variables.assign(name, UUID.randomUUID());
    }

    @Given("^var: ([^\"' ]*)=faker: ([^\"]*)$")
    public void faker(String name, String expression) {
        variables.assign(name, configuration.faker().expression(expression));
    }
}
