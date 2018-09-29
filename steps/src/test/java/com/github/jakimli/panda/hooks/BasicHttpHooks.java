package com.github.jakimli.panda.hooks;

import com.github.jakimli.panda.MockServer;
import cucumber.api.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.dreamhead.moco.HttpMethod.POST;
import static com.github.dreamhead.moco.Moco.and;
import static com.github.dreamhead.moco.Moco.by;
import static com.github.dreamhead.moco.Moco.eq;
import static com.github.dreamhead.moco.Moco.json;
import static com.github.dreamhead.moco.Moco.jsonPath;
import static com.github.dreamhead.moco.Moco.method;
import static com.github.dreamhead.moco.Moco.status;
import static com.github.dreamhead.moco.Moco.uri;
import static com.google.common.collect.ImmutableMap.of;

public class BasicHttpHooks {

    @Autowired
    MockServer server;

    @Before("@http")
    public void mock() {
        server.server()
                .get(by(uri("/users/me")))
                .response(json(of("username", "jakim", "age", 18)));

        server.server()
                .post(by(uri("/empty_request")))
                .response(status(201))
                .response(json(of("name", "someone")));

        server.server()
                .post(and(
                        by(uri("/users")),
                        by(method(POST)),
                        eq(jsonPath("$.username"), "jakim")
                ))
                .response(json(of(
                        "id", "auto-generated",
                        "username", "jakim",
                        "age", 18
                )));

        server.start();
    }
}
