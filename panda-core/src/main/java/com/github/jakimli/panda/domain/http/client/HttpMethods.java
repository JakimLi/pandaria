package com.github.jakimli.panda.domain.http.client;

import com.github.jakimli.panda.domain.http.client.HttpMethod.Method;

import javax.ws.rs.client.SyncInvoker;

import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

class HttpMethods {

    private static HttpClient client = new HttpClient();

    static Method get() {
        return context -> client
                .context(context)
                .request(SyncInvoker::get);
    }

    static Method post() {
        return context -> client
                .context(context)
                .request(target -> target
                        .post(entity(context.requestBody(), APPLICATION_JSON_TYPE)));
    }

    static Method put() {
        return context -> client
                .context(context)
                .request(target -> target
                        .put(entity(context.requestBody(), APPLICATION_JSON_TYPE)));
    }

    static Method delete() {
        return context -> client
                .context(context)
                .request(SyncInvoker::delete);
    }

    static Method patch() {
        return context -> client
                .context(context)
                .request(target -> target
                        .build("PATCH", entity(context.requestBody(), APPLICATION_JSON_TYPE))
                        .invoke());
    }

    static Method head() {
        return context -> client
                .context(context)
                .request(SyncInvoker::head);
    }
}
