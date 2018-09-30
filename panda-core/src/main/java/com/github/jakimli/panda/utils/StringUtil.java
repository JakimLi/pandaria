package com.github.jakimli.panda.utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class StringUtil {
    public static void assertNotEmpty(String toBeVerified) {
        assertNotNull(toBeVerified);
        assertTrue(!toBeVerified.isEmpty());
    }
}
