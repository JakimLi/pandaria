package com.github.jakimli.panda.domain.http.client;

import com.github.jakimli.panda.domain.http.HttpContext;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.function.Function;

class HttpClient {

    private Client client;
    private HttpContext context;

    HttpClient() {
        client = ClientBuilder.newBuilder().build();
    }

    HttpClient context(HttpContext context) {
        this.context = context;
        return this;
    }

    void request(Function<Builder, Response> method) {
        Builder target = client.target(context.uri()).request();
        addHeaders(target, context.requestHeaders());
        Response response = method.apply(target);
        updateHttpContext(response);
    }

    private void addHeaders(Builder target, MultivaluedMap<String, Object> headers) {
        headers.keySet().forEach(key -> {
            List<Object> values = headers.get(key);
            values.forEach(value -> target.header(key, value));
        });
    }

    private void updateHttpContext(Response response) {
        this.context.responseBody(response.readEntity(String.class));
        this.context.status(response.getStatus());
    }
}
