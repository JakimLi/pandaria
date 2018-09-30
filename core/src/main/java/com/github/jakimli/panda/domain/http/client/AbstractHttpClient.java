package com.github.jakimli.panda.domain.http.client;

import com.github.jakimli.panda.domain.http.HttpContext;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.function.Function;

class AbstractHttpClient {

    Client client;

    AbstractHttpClient() {
        client = ClientBuilder.newBuilder().build();
    }

    void request(HttpContext context, Function<Builder, Response> method) {
        Builder target = client.target(context.uri()).request();
        addHeaders(context, target);
        Response response = method.apply(target);
        updateHttpContext(response, context);
    }

    private void addHeaders(HttpContext context, Builder target) {
        MultivaluedMap<String, Object> headers = context.requestHeaders();
        headers.keySet().forEach(key -> {
            List<Object> values = headers.get(key);
            values.forEach(value -> target.header(key, value));
        });
    }

    private void updateHttpContext(Response response, HttpContext context) {
        context.responseBody(response.readEntity(String.class));
        context.status(response.getStatus());
    }
}
