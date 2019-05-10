package com.github.jakimli.pandaria.hooks;

import com.github.jakimli.pandaria.MockServer;
import com.google.common.collect.ImmutableMap;
import cucumber.api.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

import static com.github.dreamhead.moco.Moco.and;
import static com.github.dreamhead.moco.Moco.by;
import static com.github.dreamhead.moco.Moco.exist;
import static com.github.dreamhead.moco.Moco.json;
import static com.github.dreamhead.moco.Moco.jsonPath;
import static com.github.dreamhead.moco.Moco.status;
import static com.github.dreamhead.moco.Moco.uri;
import static com.google.common.collect.ImmutableMap.of;

public class GraphqlHooks {

    @Autowired
    MockServer server;

    @Before("@graphql")
    public void outline() {
        server.server()
                .post(and(
                        by(uri("/graphql")),
                        exist(jsonPath("$.query")),
                        exist(jsonPath("$.variables"))
                ))
                .response(status(200))
                .response(json(of("data", of("book", book()))));
        server.start();
    }

    private ImmutableMap<String, Serializable> book() {
        return of("title", "CSS Designer Guide",
                "isbn", "ISBN01123",
                "author", of("name", "someone"));
    }
}
