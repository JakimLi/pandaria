package com.github.jakimli.pandaria.domain.http.client;

import com.github.jakimli.pandaria.domain.http.client.HttpMethod.Method;

import javax.ws.rs.client.SyncInvoker;

import static com.github.jakimli.pandaria.domain.http.client.HttpClient.in;

class HttpMethods {

    static Method get() {
        return context -> in(context).request(SyncInvoker::get);
    }

    static Method post() {
        return context -> in(context).request(target -> target.post(context.requestBody()));
    }

    static Method put() {
        return context -> in(context).request(target -> target.put(context.requestBody()));
    }

    static Method delete() {
        return context -> in(context).request(SyncInvoker::delete);
    }

    static Method patch() {
        return context -> in(context).request(target -> target
                .build("PATCH", context.requestBody())
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
