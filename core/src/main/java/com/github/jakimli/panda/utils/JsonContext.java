package com.github.jakimli.panda.utils;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

public class JsonContext {

    private DocumentContext context;

    private JsonContext(DocumentContext context) {
        this.context = context;
    }

    public static JsonContext json(Object content) {
        return new JsonContext(JsonPath.parse(content));
    }

    public Object path(String path) {
        try {
            return context.read(path);
        } catch (PathNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
