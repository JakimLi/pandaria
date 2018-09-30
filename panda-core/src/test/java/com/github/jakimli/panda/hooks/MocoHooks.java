package com.github.jakimli.panda.hooks;

import com.github.jakimli.panda.MockServer;
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
