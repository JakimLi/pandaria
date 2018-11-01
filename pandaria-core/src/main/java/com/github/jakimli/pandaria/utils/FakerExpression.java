package com.github.jakimli.pandaria.utils;

import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FakerExpression {
    private static final String EXPRESSION = "(#\\{[a-z0-9A-Z_.]+\\s?(?:'([^']+)')?(?:,'([^']+)')*})";

    public static String eval(Locale locale, String content) {
        Matcher matcher = Pattern.compile(EXPRESSION).matcher(content);
        return replace(locale, matcher);
    }

    private static String replace(Locale locale, Matcher matcher) {
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, new Faker(locale).expression(matcher.group(1)));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }
}
