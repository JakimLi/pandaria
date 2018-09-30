package com.github.jakimli.panda.hooks;

import com.github.jakimli.panda.MockServer;
import cucumber.api.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.dreamhead.moco.Moco.and;
import static com.github.dreamhead.moco.Moco.by;
import static com.github.dreamhead.moco.Moco.eq;
import static com.github.dreamhead.moco.Moco.header;
import static com.github.dreamhead.moco.Moco.json;
import static com.github.dreamhead.moco.Moco.jsonPath;
import static com.github.dreamhead.moco.Moco.method;
import static com.github.dreamhead.moco.Moco.status;
import static com.github.dreamhead.moco.Moco.text;
import static com.github.dreamhead.moco.Moco.uri;
import static com.google.common.collect.ImmutableMap.of;

public class BasicHttpHooks {

    @Autowired
    MockServer server;

    @Before("@variables")
    public void mockForVariableTests() {
        server.server()
                .request(by(uri("/not_important")))
                .response(json(of(
                        "name", "panda",
                        "age", 18,
                        "iq", 80.0
                )));

        server.server()
                .post(and(
                        by(uri("/users")),
                        eq(jsonPath("$.name"), "someone")))
                .response(status(201));

        server.start();
    }

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
                        eq(jsonPath("$.username"), "jakim")
                ))
                .response(json(of(
                        "id", "auto-generated",
                        "username", "jakim",
                        "age", 18
                )));

        server.server()
                .get(and(
                        by(uri("/custom_header")),
                        eq(header("Accept"), "text.plain")
                ))
                .response(text("success"));

        server.server()
                .put(and(
                        by(uri("/users/me")),
                        eq(jsonPath("$.username"), "lj")
                ))
                .response(json(of("username", "lj")));

        server.server()
                .delete(by(uri("/users/20")))
                .response(status(200));

        server.server()
                .request(and(
                        by(uri("/users/20")),
                        by(method("PATCH"))
                ))
                .response(json(of("username", "lj")));

        server.server()
                .request(and(
                        by(uri("/users")),
                        by(method("HEAD"))
                ))
                .response(
                        header("test", "first,second,third"),
                        header("Date", "Thur, 2018 12"));

        server.server()
                .request(and(
                        by(uri("/users")),
                        by(method("OPTIONS"))
                ))
                .response(
                        header("Allow", "OPTIONS, GET, HEAD"));

        server.server()
                .request(and(
                        by(uri("/users")),
                        by(method("TRACE"))
                )).response(header("Content-Type", "message/http"));

        server.start();
    }
}
