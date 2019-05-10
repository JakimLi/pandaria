package com.github.jakimli.pandaria.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.github.jakimli.pandaria.utils.JsonUtil.toJsonString;
import static java.util.Optional.empty;

@Component
@Scope("cucumber-glue")
public class GraphqlContext {
    private String query;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<String> variables = empty();

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<String> operationName = empty();

    public void query(String query) {
        this.query = query;
    }

    public void variables(String variables) {
        this.variables = Optional.of(variables);
    }

    public void reset() {
        this.query = null;
        this.variables = empty();
        operationName = empty();
    }

    public String request() {
        try {

            Builder<String, String> builder = ImmutableMap.<String, String>builder()
                    .put("query", this.query);


            variables.ifPresent(variables -> builder.put("variables", variables));
            operationName.ifPresent(operationName -> builder.put("operationName", operationName));

            return toJsonString(builder.build());
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception.getMessage(), exception);
        }
    }

    public void operationName(String name) {
        this.operationName = Optional.of(name);
    }
}
