package com.github.jakimli.pandaria.hooks;

import com.github.jakimli.pandaria.domain.http.ScenarioContext;
import cucumber.api.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.NewCookie;

public class ScenarioContextHooks {
    @Autowired
    private ScenarioContext scenarioContext;

    @Before("@DefaultCookieAndHeader")
    public void doSomething() {
        scenarioContext.getCookies().put("sessionid", new NewCookie("sessionid", "888888"));
        scenarioContext.getRequestHeaders().put("HeaderName", "a header");
    }
}
