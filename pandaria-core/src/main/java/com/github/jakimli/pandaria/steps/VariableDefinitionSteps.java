package com.github.jakimli.pandaria.steps;

import com.github.jakimli.pandaria.domain.Variables;
import com.github.jakimli.pandaria.domain.VerificationContext;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import javax.script.ScriptException;
import java.io.IOException;
import java.util.UUID;

import static com.github.jakimli.pandaria.utils.ScriptUtil.eval;

public class VariableDefinitionSteps {

    @Autowired
    Variables variables;

    @Autowired
    VerificationContext toBeVerified;

    @Given("^var: '([^\"]*)'='([^\"]*)'$")
    public void defineLiteralStringVariable(String key, String value) {
        variables.assign(key, value);
    }

    @Given("^var: '([^\"]*)'=\"([^\"]*)\"$")
    public void defineStringVariable(String key, String value) {
        variables.assign(key, variables.interpret(value));
    }

    @Given("^var: '([^\"]*)'=(\\d+)$")
    public void defineIntegerVariable(String key, int value) {
        variables.assign(key, value);
    }

    @Given("^var: '([^\"]*)'=(\\d+\\.\\d+)$")
    public void defineDoubleVariable(String key, String value) {
        variables.assign(key, Double.parseDouble(value));
    }

    @Given("^var: '([^\"]*)'<-'([^\"]*)'$")
    public void defineVariableExtractByJsonPath(String key, String path) throws IOException {
        variables.assign(key, toBeVerified.json(path));
    }

    @Given("^var: '([^\"]*)'=code:$")
    public void defineVariableFromCodeBlock(String key, String code) throws ScriptException {
        variables.assign(key, eval(variables.interpret(code)));
    }

    @Given("^var: '([^\"]*)'=code: (.*)$")
    public void defineVariableFromCodeInLine(String key, String code) throws ScriptException {
        variables.assign(key, eval(variables.interpret(code)));
    }

    @Given("^var: '([^\"]*)'=random uuid")
    public void defineRandomUUIDVariable(String name) {
        variables.assign(name, UUID.randomUUID());
    }
}
