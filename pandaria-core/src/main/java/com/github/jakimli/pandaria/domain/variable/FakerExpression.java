package com.github.jakimli.pandaria.domain.variable;

import com.github.jakimli.pandaria.domain.variable.Variables.Expression;
import com.github.javafaker.Faker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FakerExpression implements Expression {

    private static final Pattern EXPRESSION_PATTERN = Pattern.compile("(#?#\\{[a-z0-9A-Z_.]+\\s?(?:'([^']+)')?(?:,'([^']+)')*})");

    private Faker faker;

    FakerExpression(Faker faker) {
        this.faker = faker;
    }

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
            matcher.appendReplacement(buffer, this.faker.expression(group));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }
}
