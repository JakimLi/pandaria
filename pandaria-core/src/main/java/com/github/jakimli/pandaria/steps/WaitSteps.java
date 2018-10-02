package com.github.jakimli.pandaria.steps;

import cucumber.api.java.en.Given;

import static java.lang.Thread.sleep;

public class WaitSteps {

    @Given("^wait: (\\d+)ms$")
    public void waitMillis(int millis) throws InterruptedException {
        sleep(millis);
    }

    @Given("^wait: (\\d+)s$")
    public void waitSeconds(int seconds) throws InterruptedException {
        sleep(1000 * seconds);
    }
}
