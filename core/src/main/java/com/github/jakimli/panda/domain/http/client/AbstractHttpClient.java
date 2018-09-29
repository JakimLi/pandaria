package com.github.jakimli.panda.domain.http.client;

import com.github.jakimli.panda.domain.http.HttpContext;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

class AbstractHttpClient {

    Client client;

    AbstractHttpClient() {
        client = ClientBuilder.newBuilder().build();
    }

    void updateHttpContext(Response response, HttpContext context) {
        context.responseBody(response.readEntity(String.class));
        context.status(response.getStatus());
    }
}
