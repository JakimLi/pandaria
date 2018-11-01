package com.github.jakimli.pandaria.utils;

import com.github.javafaker.Faker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FakerExpression {
    private static final String EXPRESSION = "(#\\{[a-z0-9A-Z_.]+\\s?(?:'([^']+)')?(?:,'([^']+)')*})";

    public static String eval(Faker faker, String content) {
        Matcher matcher = Pattern.compile(EXPRESSION).matcher(content);
        return replace(faker, matcher);
    }

    private static String replace(Faker faker, Matcher matcher) {
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, faker.expression(matcher.group(1)));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }
}
