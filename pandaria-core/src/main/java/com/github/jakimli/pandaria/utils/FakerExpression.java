package com.github.jakimli.pandaria.utils;

import com.github.javafaker.Faker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FakerExpression {
    private static final String EXPRESSION = "(#\\{[a-z0-9A-Z_.]+\\s?(?:'([^']+)')?(?:,'([^']+)')*})";

    public static String eval(String content) {
        Matcher matcher = Pattern.compile(EXPRESSION).matcher(content);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, new Faker().expression(matcher.group(1)));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }
}
