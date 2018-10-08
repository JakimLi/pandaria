package com.github.jakimli.pandaria.utils;

import com.google.common.io.Resources;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.io.Resources.getResource;
import static org.springframework.util.ResourceUtils.getFile;

public class FileUtil {

    public static String read(String file) throws IOException {
        URL resource = getResource(file);
        return Resources.toString(resource, UTF_8);
    }

    public static File file(String file) {
        try {
            return getFile(ResourceUtils.CLASSPATH_URL_PREFIX + file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(file + " not found");
        }
    }
}
