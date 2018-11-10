package com.github.jakimli.pandaria.domain.variable;

import com.github.jakimli.pandaria.domain.FeatureConfiguration;
import com.github.jakimli.pandaria.domain.VerificationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static org.apache.commons.text.StringSubstitutor.replace;

@Component
@Scope("cucumber-glue")
@ConfigurationProperties
public class Variables {

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

    public Object get(String name) {
        return variables.get(name);
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
