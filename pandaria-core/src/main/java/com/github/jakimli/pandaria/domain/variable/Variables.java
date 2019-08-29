package com.github.jakimli.pandaria.domain.variable;

import com.github.jakimli.pandaria.domain.FeatureConfiguration;
import com.github.jakimli.pandaria.domain.VerificationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.github.jakimli.pandaria.utils.JsonContext.json;
import static com.google.common.collect.Maps.newHashMap;
import static org.apache.commons.text.StringSubstitutor.replace;

@Component
@Scope("cucumber-glue")
@ConfigurationProperties
public class Variables {

    private static final String DOT = "\\.";

    @Autowired
    FeatureConfiguration configuration;

    @Autowired
    VerificationContext verifying;

    private Map<String, Object> variables = newHashMap();

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

    public String interpret(String value) {
        String replaced = replace(value, variables);
        String content = new JsonPathExpression(verifying).evaluate(replaced);
        return new FakerExpression(configuration.faker()).evaluate(content);
    }

    interface Expression {
        String evaluate(String raw);
    }
}
