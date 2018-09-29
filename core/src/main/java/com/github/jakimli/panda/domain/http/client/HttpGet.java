package com.github.jakimli.panda.domain.http.client;

import com.github.jakimli.panda.domain.http.HttpContext;

import javax.ws.rs.core.Response;

public class HttpGet extends AbstractHttpClient implements HttpClient {

    @Override
    public void send(HttpContext context) {
        Response response = client.target(context.uri()).request().get();
        updateHttpContext(response, context);
    }
}
