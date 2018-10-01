package com.github.jakimli.pandaria.domain.http.client;

import com.github.jakimli.pandaria.domain.http.HttpContext;

import java.util.function.Supplier;

public enum HttpMethod {
    GET(HttpMethods::get),
    POST(HttpMethods::post),
    PUT(HttpMethods::put),
    DELETE(HttpMethods::delete),
    PATCH(HttpMethods::patch),
    HEAD(HttpMethods::head),
    OPTIONS(HttpMethods::options),
    TRACE(HttpMethods::trace);

    private Supplier<Method> method;

    HttpMethod(Supplier<Method> method) {
        this.method = method;
    }

    public void send(HttpContext context) {
        method.get().send(context);
    }

    public interface Method {
        void send(HttpContext context);
    }
}
