package com.github.jakimli.pandaria.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonUtil {

    public static Map<String, Object> jsonToMap(String content) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(content, new TypeReference<Map<String, Object>>() {
        });
    }

    public static List<Object> jsonToList(String content) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(content, new TypeReference<List<Object>>() {
        });
    }

    public static Object jsonToObject(String content) throws IOException {
        return content.trim().startsWith("[") ? jsonToList(content) : jsonToMap(content);
    }

    public static String toJsonString(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    public static Object toJSONAware(Object content) throws JsonProcessingException {
        if (content instanceof List) {
            return new JSONArray(toJsonString(content));
        } else if (content instanceof Map) {
            return new JSONObject(toJsonString(content));
        } else {
            return content;
        }
    }

    public static int size(Object json) {
        if (json instanceof String) {
            return ((String) json).length();
        } else if (json instanceof List) {
            return ((List) json).size();
        } else if (json instanceof Map) {
            return ((Map) json).size();
        } else if (json instanceof JSONArray) {
            return ((JSONArray) json).length();
        } else if (json instanceof JSONObject) {
            return ((JSONObject) json).length();
        }
        throw new RuntimeException("unable to get size");
    }
}
