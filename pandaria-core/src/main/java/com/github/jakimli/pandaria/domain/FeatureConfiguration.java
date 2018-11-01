package com.github.jakimli.pandaria.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Scope("cucumber-glue")
public class FeatureConfiguration {
    private String dir;
    private String baseUri;

    @Value("${faker.locale:en}")
    private Locale fakerLocale;

    public void dir(String dir) {
        this.dir = dir;
    }

    public void baseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public void fakerLocale(Locale fakerLocale) {
        this.fakerLocale = fakerLocale;
    }

    public Locale fakerLocale() {
        return fakerLocale;
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
