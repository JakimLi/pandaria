package com.github.jakimli.pandaria.domain.variable;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class VariableExpression implements Variables.Expression {

    @Autowired
    Variables variables;

    @Override
    public String evaluate(String raw) {
        return StringSubstitutor.replace(raw, variables.variables);
    }
}
