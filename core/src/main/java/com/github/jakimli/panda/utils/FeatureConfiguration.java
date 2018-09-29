package com.github.jakimli.panda.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class FeatureConfiguration {
    private String dir;

    public void dir(String dir) {
        this.dir = dir;
    }

    public String classpathFile(String file) {
        if (file.startsWith("classpath:")) {
            return file.replace("classpath:", "");
        }
        return dir + "/" + file;
    }
}
