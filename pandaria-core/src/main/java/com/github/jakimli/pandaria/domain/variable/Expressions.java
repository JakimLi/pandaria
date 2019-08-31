package com.github.jakimli.pandaria.domain.variable;

import com.github.jakimli.pandaria.domain.variable.Variables.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Scope("cucumber-glue")
public class Expressions {

    @Autowired
    List<Expression> expressions;

    public String evaluate(String value) {
        Optional<Expression> expression = expressions.stream()
                .reduce((expression1, expression2) -> raw -> expression2.evaluate(expression1.evaluate(raw)));
        return expression.orElse(raw -> raw).evaluate(value);
    }
}
