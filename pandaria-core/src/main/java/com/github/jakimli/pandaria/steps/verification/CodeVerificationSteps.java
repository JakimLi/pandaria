package com.github.jakimli.pandaria.steps.verification;

import com.github.jakimli.pandaria.domain.Variables;
import com.github.jakimli.pandaria.domain.VerificationContext;
import com.github.jakimli.pandaria.utils.ScriptUtil;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import javax.script.ScriptException;
import java.io.IOException;

import static com.github.jakimli.pandaria.utils.ScriptUtil.eval;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CodeVerificationSteps {

    @Autowired
    VerificationContext actual;

    @Autowired
    Variables variables;

    @Then("^verify: '([^\"]*)'=code: (.*)$")
    public void verifyEqualsCodeInLine(String path, String code) throws IOException, ScriptException {
        assertThat(actual.json(path), is(eval(variables.interpret(code))));
    }

    @Then("^verify: '([^\"]*)'!=code: (.*)$")
    public void verifyNotEqualsCodeInLine(String path, String code) throws IOException, ScriptException {
        assertThat(actual.json(path), not(eval(variables.interpret(code))));
    }
}
