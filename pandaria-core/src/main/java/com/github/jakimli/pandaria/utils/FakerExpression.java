package com.github.jakimli.pandaria.utils;

import com.github.javafaker.Faker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FakerExpression {
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile("(#?#\\{[a-z0-9A-Z_.]+\\s?(?:'([^']+)')?(?:,'([^']+)')*})");

    public static String evaluate(Faker faker, String expression) {
        Matcher matcher = EXPRESSION_PATTERN.matcher(expression);

        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String group = matcher.group(1);
            if (group.startsWith("##")) {
                matcher.appendReplacement(buffer, group.substring(1));
                continue;
            }
            matcher.appendReplacement(buffer, faker.expression(group));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }
}
