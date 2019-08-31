package com.github.jakimli.pandaria.domain.variable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.github.jakimli.pandaria.utils.JsonContext.json;
import static com.google.common.collect.Maps.newHashMap;

@Component
@Scope("cucumber-glue")
@ConfigurationProperties
public class Variables {

    private static final String DOT = "\\.";

    Map<String, Object> variables = newHashMap();

    public void assign(String key, Object value) {
        this.variables.put(key, value);
    }

    //required for spring
    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    //expression: ${variable.nest.property}
    //expression: ${variable.nest[0].property}
    //expression: ${variable}
    public Object get(String expression) {
        String name = expression.split(DOT)[0];
        Object value = variables.get(name);

        if (value == null) {
            return null;
        }

        String path = expression.replaceFirst(name, "");
        return json(value).path("$" + path);
    }

    interface Expression {
        String evaluate(String raw);
    }
}
