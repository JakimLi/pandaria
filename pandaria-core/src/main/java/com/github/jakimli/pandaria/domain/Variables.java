package com.github.jakimli.pandaria.domain;

import com.github.javafaker.Faker;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.Maps.newHashMap;
import static org.apache.commons.text.StringSubstitutor.replace;

@Component
@Scope("cucumber-glue")
@ConfigurationProperties()
public class Variables {

    private static final String EXPRESSION = "(#\\{[a-z0-9A-Z_.]+\\s?(?:'([^']+)')?(?:,'([^']+)')*})";

    private Map<String, Object> variables = newHashMap();

    public void assign(String key, Object value) {
        this.variables.put(key, value);
    }

    //required for spring
    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public Object get(String name) {
        return variables.get(name);
    }

    public String interpret(String value) {
        return evalFakerExpression(replace(value, variables));
    }

    private String evalFakerExpression(String content) {
        Matcher matcher = Pattern.compile(EXPRESSION).matcher(content);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, new Faker().expression(matcher.group(1)));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }
}
