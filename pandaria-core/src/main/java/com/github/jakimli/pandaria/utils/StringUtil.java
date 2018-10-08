package com.github.jakimli.pandaria.utils;

import java.util.List;

import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class StringUtil {
    public static void assertNotEmpty(String toBeVerified) {
        assertNotNull(toBeVerified);
        assertTrue(!toBeVerified.isEmpty());
    }

    public static String joinByComma(List<Object> objects) {
        return objects.stream().map(Object::toString).collect(joining(","));
    }
}
