package com.github.jakimli.pandaria.steps;

import com.github.jakimli.pandaria.domain.wait.Wait;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import static java.lang.Thread.sleep;

public class WaitSteps {

    @Autowired
    Wait wait;

    @Given("^wait: (\\d+)ms$")
    public void waitMillis(int millis) throws InterruptedException {
        sleep(millis);
    }

    @Given("^wait: (\\d+)ms times: (\\d+)$")
    public void waitMillisUntil(int millis, int maxRetry) {
        wait.configure(millis, maxRetry);
    }

    @Given("^wait: (\\d+)s times: (\\d+)$")
    public void waitSecondsUntil(int seconds, int maxRetry) {
        wait.configure(1000 * seconds, maxRetry);
    }

    @Given("^wait: (\\d+)s$")
    public void waitSeconds(int seconds) throws InterruptedException {
        sleep(1000 * seconds);
    }
}
