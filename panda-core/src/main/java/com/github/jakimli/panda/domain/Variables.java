package com.github.jakimli.panda.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

@Component
@Scope("cucumber-glue")
public class Variables {

    private Map<String, Object> variables = newHashMap();

    public void assign(String key, Object value) {
        this.variables.put(key, value);
    }

    public Object get(String name) {
        return variables.get(name);
    }
}
