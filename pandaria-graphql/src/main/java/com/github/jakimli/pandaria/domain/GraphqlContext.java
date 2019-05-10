package com.github.jakimli.pandaria.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.github.jakimli.pandaria.utils.JsonUtil.toJsonString;

@Component
@Scope("cucumber-glue")
public class GraphqlContext {
    private String query;
    private String variables;

    public void query(String query) {
        this.query = query;
    }

    public void variables(String variables) {
        this.variables = variables;
    }

    public void reset() {
        this.query = null;
        this.variables = null;
    }

    public String request() {
        try {
            return toJsonString(ImmutableMap.<String, String>builder()
                    .put("query", this.query)
                    .put("variables", this.variables)
                    .build());
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception.getMessage(), exception);
        }
    }
}
