package com.github.jakimli.pandaria.steps.verification;

import com.github.jakimli.pandaria.domain.VerificationContext;
import com.github.jakimli.pandaria.domain.expression.Expressions;
import com.github.jakimli.pandaria.domain.variable.Variables;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

public class DebugSteps {
    @Autowired
    VerificationContext actual;

    @Autowired
    Variables variables;

    @Autowired
    Expressions expressions;

    @Then("^print: \"([^\"]*)\"$")
    public void printExpression(String printing) {
        System.out.println(expressions.evaluate(printing));
    }

    @Then("^print: \'([^\"]*)\'$")
    public void printLiteral(String printing) {
        System.out.println(printing);
    }
}
