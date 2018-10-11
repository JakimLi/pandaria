package com.github.jakimli.pandaria.domain.http;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Scope("cucumber-glue")
@ConfigurationProperties(prefix = "http")
public class GlobalHeaders {

    private Map<String, String> headers;

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }
}
