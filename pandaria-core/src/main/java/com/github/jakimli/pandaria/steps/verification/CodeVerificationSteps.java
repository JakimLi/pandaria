package com.github.jakimli.pandaria.steps.verification;

import com.github.jakimli.pandaria.domain.FeatureConfiguration;
import com.github.jakimli.pandaria.domain.variable.Variables;
import com.github.jakimli.pandaria.domain.VerificationContext;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import javax.script.ScriptException;
import java.io.IOException;

import static com.github.jakimli.pandaria.utils.FileUtil.read;
import static com.github.jakimli.pandaria.utils.ScriptUtil.eval;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CodeVerificationSteps {

    @Autowired
    VerificationContext actual;

    @Autowired
    Variables variables;

    @Autowired
    private FeatureConfiguration configuration;

    @Then("^verify: '([^\"]*)'=code: (.*)$")
    public void verifyEqualsCodeInLine(String path, String code) throws IOException, ScriptException {
        assertThat(actual.json(path), is(eval(variables.interpret(code))));
    }

    @Then("^verify: '([^\"]*)'!=code: (.*)$")
    public void verifyNotEqualsCodeInLine(String path, String code) throws IOException, ScriptException {
        assertThat(actual.json(path), not(eval(variables.interpret(code))));
    }

    @Then("^verify: '([^\"]*)'=code:$")
    public void verifyEqualsCodeBlock(String path, String code) throws IOException, ScriptException {
        assertThat(actual.json(path), is(eval(variables.interpret(code))));
    }

    @Then("^verify: '([^\"]*)'!=code:$")
    public void verifyNotEqualsCodeBlock(String path, String code) throws IOException, ScriptException {
        assertThat(actual.json(path), not(eval(variables.interpret(code))));
    }

    @Then("^verify: '([^\"]*)'=code file: ([^\"]*)$")
    public void verifyEqualsCodeInFile(String path, String file) throws IOException, ScriptException {
        assertThat(actual.json(path), is(eval(variables.interpret(read(configuration.classpathFile(file))))));
    }

    @Then("^verify: '([^\"]*)'!=code file: ([^\"]*)$")
    public void verifyNotEqualsInFile(String path, String file) throws IOException, ScriptException {
        assertThat(actual.json(path), not(eval(variables.interpret(read(configuration.classpathFile(file))))));
    }

    @Then("^verify: \\$\\{([^\"]*)}=code: (.*)$")
    public void verifyVariableEqualsCodeInLine(String path, String code) throws IOException, ScriptException {
        assertThat(variables.get(path), is(eval(variables.interpret(code))));
    }

    @Then("^verify: \\$\\{([^\"]*)}!=code: (.*)$")
    public void verifyVariableNotEqualsCodeInLine(String path, String code) throws IOException, ScriptException {
        assertThat(variables.get(path), not(eval(variables.interpret(code))));
    }

    @Then("^verify: \\$\\{([^\"]*)}=code:$")
    public void verifyVariableEqualsCodeBlock(String path, String code) throws IOException, ScriptException {
        assertThat(variables.get(path), is(eval(variables.interpret(code))));
    }

    @Then("^verify: \\$\\{([^\"]*)}!=code:$")
    public void verifyVariableNotEqualsCodeBlock(String path, String code) throws IOException, ScriptException {
        assertThat(variables.get(path), not(eval(variables.interpret(code))));
    }

    @Then("^verify: \\$\\{([^\"]*)}=code file: ([^\"]*)$")
    public void verifyVariableEqualsCodeInFile(String path, String file) throws IOException, ScriptException {
        assertThat(variables.get(path), is(eval(variables.interpret(read(configuration.classpathFile(file))))));
    }

    @Then("^verify: \\$\\{([^\"]*)}!=code file: ([^\"]*)$")
    public void verifyVariableNotEqualsInFile(String path, String file) throws IOException, ScriptException {
        assertThat(variables.get(path), not(eval(variables.interpret(read(configuration.classpathFile(file))))));
    }

    @Then("^verify code: (.*)")
    public void verifyCodeInLineTrue(String code) throws ScriptException {
        assertThat(eval(variables.interpret(code)), is(true));
    }

    @Then("^verify code:")
    public void verifyCodeBlockTrue(String code) throws ScriptException {
        assertThat(eval(variables.interpret(code)), is(true));
    }

    @Then("^verify code file: ([^\"]*)")
    public void verifyCodeInFileTrue(String file) throws ScriptException, IOException {
        assertThat(eval(variables.interpret(read(configuration.classpathFile(file)))), is(true));
    }
}
