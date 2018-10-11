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

    public static List<Map<String, Object>> jsonToList(String content) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(content, new TypeReference<List<Map<String, Object>>>() {
        });
    }

    public static Object jsonToObject(String content) throws IOException {
        return content.trim().startsWith("[") ? jsonToList(content) : jsonToMap(content);
    }

    public static String toJsonString(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    public static Object toJSONObjectOrArray(String content) {
        return content.trim().startsWith("[") ? new JSONArray(content) : new JSONObject(content);
    }
}
