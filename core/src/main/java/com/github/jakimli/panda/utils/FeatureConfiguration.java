package com.github.jakimli.panda.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class FeatureConfiguration {
    private String dir;
    private String baseUri;

    public void dir(String dir) {
        this.dir = dir;
    }

    public void baseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public String classpathFile(String file) {
        if (file.startsWith("classpath:")) {
            return file.replace("classpath:", "");
        }
        return dir + "/" + file;
    }

    public String uri(String uri) {
        if (uri.startsWith("http")) {
            return uri;
        }
        return this.baseUri + prefixSlash(uri);
    }

    private String prefixSlash(String uri) {
        if (uri.startsWith("/")) {
            return uri;
        }
        return "/" + uri;
    }
}
