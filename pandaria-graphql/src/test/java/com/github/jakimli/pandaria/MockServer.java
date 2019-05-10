package com.github.jakimli.pandaria;

import com.github.dreamhead.moco.HttpServer;
import com.github.dreamhead.moco.Runner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.github.dreamhead.moco.Moco.httpServer;

@Component
@Scope("cucumber-glue")
public class MockServer {

    private final Runner runner;
    private HttpServer server;

    public MockServer() {
        this.server = httpServer(10081);
        runner = Runner.runner(server);
    }

    public void start() {
        runner.start();
    }

    public void stop() {
        runner.stop();
    }

    public HttpServer server() {
        return this.server;
    }
}
