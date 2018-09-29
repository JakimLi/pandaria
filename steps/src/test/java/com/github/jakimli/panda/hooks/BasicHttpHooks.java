package com.github.jakimli.panda.hooks;

import com.github.jakimli.panda.MockServer;
import cucumber.api.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.dreamhead.moco.Moco.by;
import static com.github.dreamhead.moco.Moco.json;
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

        server.start();
    }
}
