package com.github.jakimli.pandaria.domain.http.client;

import com.github.jakimli.pandaria.domain.http.client.HttpMethod.Method;

import javax.ws.rs.client.SyncInvoker;

import static com.github.jakimli.pandaria.domain.http.client.HttpClient.in;
import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

class HttpMethods {

    static Method get() {
        return context -> in(context).request(SyncInvoker::get);
    }

    static Method post() {
        return context -> in(context).request(target -> target
                .post(entity(context.requestBody(), APPLICATION_JSON_TYPE)));
    }

    static Method put() {
        return context -> in(context).request(target -> target
                .put(entity(context.requestBody(), APPLICATION_JSON_TYPE)));
    }

    static Method delete() {
        return context -> in(context).request(SyncInvoker::delete);
    }

    static Method patch() {
        return context -> in(context).request(target -> target
                .build("PATCH", entity(context.requestBody(), APPLICATION_JSON_TYPE))
                .invoke());
    }

    static Method head() {
        return context -> in(context).request(SyncInvoker::head);
    }

    static Method options() {
        return context -> in(context).request(SyncInvoker::options);
    }

    static Method trace() {
        return context -> in(context).request(SyncInvoker::trace);
    }
}
