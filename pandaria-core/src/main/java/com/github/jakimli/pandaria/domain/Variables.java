package com.github.jakimli.pandaria.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.github.jakimli.pandaria.utils.FakerExpression.eval;
import static com.google.common.collect.Maps.newHashMap;
import static org.apache.commons.text.StringSubstitutor.replace;

@Component
@Scope("cucumber-glue")
@ConfigurationProperties()
public class Variables {

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
        return eval(replace(value, variables));
    }
}
