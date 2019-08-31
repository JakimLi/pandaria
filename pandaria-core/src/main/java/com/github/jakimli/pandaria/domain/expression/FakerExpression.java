package com.github.jakimli.pandaria.domain.expression;

import com.github.jakimli.pandaria.domain.FeatureConfiguration;
import com.github.jakimli.pandaria.domain.expression.Expressions.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Scope("cucumber-glue")
class FakerExpression implements Expression {

    private static final Pattern EXPRESSION_PATTERN = Pattern.compile("(#?#\\{[a-z0-9A-Z_.]+\\s?(?:'([^']+)')?(?:,'([^']+)')*})");

    @Autowired
    FeatureConfiguration configuration;

    @Override
    public String evaluate(String raw) {
        Matcher matcher = EXPRESSION_PATTERN.matcher(raw);

        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String group = matcher.group(1);
            if (group.startsWith("##")) {
                matcher.appendReplacement(buffer, group.substring(1));
                continue;
            }
            matcher.appendReplacement(buffer, this.configuration.faker().expression(group));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }
}
