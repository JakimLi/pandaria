package com.github.jakimli.pandaria.domain.http;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.NewCookie;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope("cucumber-glue")
public class ScenarioContext {
    private Map<String, NewCookie> cookies = new HashMap<>();
    private Map<String, String> requestHeaders = new HashMap<>();

    public Map<String, NewCookie> getCookies() {
        return cookies;
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }
}
