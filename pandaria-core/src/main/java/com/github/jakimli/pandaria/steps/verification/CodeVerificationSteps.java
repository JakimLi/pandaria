package com.github.jakimli.pandaria.steps.verification;

import com.github.jakimli.pandaria.domain.FeatureConfiguration;
import com.github.jakimli.pandaria.domain.VerificationContext;
import com.github.jakimli.pandaria.domain.expression.Expressions;
import com.github.jakimli.pandaria.domain.variable.Variables;
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
    Expressions expressions;

    @Autowired
    private FeatureConfiguration configuration;

    @Then("^verify: '([^\"]*)'=code: (.*)$")
    public void verifyEqualsCodeInLine(String path, String code) throws IOException, ScriptException {
        assertThat(actual.json(path), is(eval(expressions.evaluate(code))));
    }

    @Then("^verify: '([^\"]*)'!=code: (.*)$")
    public void verifyNotEqualsCodeInLine(String path, String code) throws IOException, ScriptException {
        assertThat(actual.json(path), not(eval(expressions.evaluate(code))));
    }

    @Then("^verify: '([^\"]*)'=code:$")
    public void verifyEqualsCodeBlock(String path, String code) throws IOException, ScriptException {
        assertThat(actual.json(path), is(eval(expressions.evaluate(code))));
    }

    @Then("^verify: '([^\"]*)'!=code:$")
    public void verifyNotEqualsCodeBlock(String path, String code) throws IOException, ScriptException {
        assertThat(actual.json(path), not(eval(expressions.evaluate(code))));
    }

    @Then("^verify: '([^\"]*)'=code file: ([^\"]*)$")
    public void verifyEqualsCodeInFile(String path, String file) throws IOException, ScriptException {
        assertThat(actual.json(path), is(eval(expressions.evaluate(read(configuration.classpathFile(file))))));
    }

    @Then("^verify: '([^\"]*)'!=code file: ([^\"]*)$")
    public void verifyNotEqualsInFile(String path, String file) throws IOException, ScriptException {
        assertThat(actual.json(path), not(eval(expressions.evaluate(read(configuration.classpathFile(file))))));
    }

    @Then("^verify: \\$\\{([^\"]*)}=code: (.*)$")
    public void verifyVariableEqualsCodeInLine(String expression, String code) throws ScriptException {
        assertThat(variables.get(expression), is(eval(expressions.evaluate(code))));
    }

    @Then("^verify: \\$\\{([^\"]*)}!=code: (.*)$")
    public void verifyVariableNotEqualsCodeInLine(String expression, String code) throws ScriptException {
        assertThat(variables.get(expression), not(eval(expressions.evaluate(code))));
    }

    @Then("^verify: \\$\\{([^\"]*)}=code:$")
    public void verifyVariableEqualsCodeBlock(String expression, String code) throws ScriptException {
        assertThat(variables.get(expression), is(eval(expressions.evaluate(code))));
    }

    @Then("^verify: \\$\\{([^\"]*)}!=code:$")
    public void verifyVariableNotEqualsCodeBlock(String expression, String code) throws ScriptException {
        assertThat(variables.get(expression), not(eval(expressions.evaluate(code))));
    }

    @Then("^verify: \\$\\{([^\"]*)}=code file: ([^\"]*)$")
    public void verifyVariableEqualsCodeInFile(String expression, String file) throws IOException, ScriptException {
        assertThat(variables.get(expression), is(eval(expressions.evaluate(read(configuration.classpathFile(file))))));
    }

    @Then("^verify: \\$\\{([^\"]*)}!=code file: ([^\"]*)$")
    public void verifyVariableNotEqualsInFile(String expression, String file) throws IOException, ScriptException {
        assertThat(variables.get(expression), not(eval(expressions.evaluate(read(configuration.classpathFile(file))))));
    }

    @Then("^verify code: (.*)")
    public void verifyCodeInLineTrue(String code) throws ScriptException {
        assertThat(eval(expressions.evaluate(code)), is(true));
    }

    @Then("^verify code:")
    public void verifyCodeBlockTrue(String code) throws ScriptException {
        assertThat(eval(expressions.evaluate(code)), is(true));
    }

    @Then("^verify code file: ([^\"]*)")
    public void verifyCodeInFileTrue(String file) throws ScriptException, IOException {
        assertThat(eval(expressions.evaluate(read(configuration.classpathFile(file)))), is(true));
    }
}
