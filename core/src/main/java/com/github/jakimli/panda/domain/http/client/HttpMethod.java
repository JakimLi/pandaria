package com.github.jakimli.panda.domain.http.client;

import com.github.jakimli.panda.domain.http.HttpContext;

import java.util.function.Supplier;

public enum HttpMethod {
    GET(HttpGet::new);

    private Supplier<HttpClient> client;

    HttpMethod(Supplier<HttpClient> client) {
        this.client = client;
    }

    public void send(HttpContext context) {
        client.get().send(context);
    }
}
