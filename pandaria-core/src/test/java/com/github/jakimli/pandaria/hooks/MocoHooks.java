package com.github.jakimli.pandaria.hooks;

import com.github.jakimli.pandaria.MockServer;
import cucumber.api.java.After;
import org.springframework.beans.factory.annotation.Autowired;

public class MocoHooks {

    @Autowired
    MockServer server;

    @After
    public void shutdown() {
        server.stop();
    }
}
