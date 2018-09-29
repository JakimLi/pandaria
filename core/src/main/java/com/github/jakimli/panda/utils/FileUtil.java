package com.github.jakimli.panda.utils;

import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.io.Resources.getResource;

public class FileUtil {

    public static String read(String file) throws IOException {
        URL resource = getResource(file);
        return Resources.toString(resource, UTF_8);
    }
}
