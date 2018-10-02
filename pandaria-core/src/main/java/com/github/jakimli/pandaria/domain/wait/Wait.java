package com.github.jakimli.pandaria.domain.wait;

import com.github.jakimli.pandaria.domain.verification.VerificationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.junit.Assert.fail;

@Component
@Scope("cucumber-glue")
public class Wait {
    @Autowired
    private VerificationContext verifier;

    private int millis;
    private int maxRetry;
    private boolean on;

    private Waitable waitable;

    public void configure(int millis, int maxRetry) {
        if (maxRetry < 1) {
            fail("Invalid retry times, must greater than 0");
        }
        this.on = true;
        this.millis = millis;
        this.maxRetry = maxRetry;
    }

    public void waitable(Waitable waitable) {
        this.waitable = waitable;
    }

    boolean on() {
        return on;
    }

    void retry() {
        this.waitable.retry();
        verifier.toBeVerified(this.waitable.result());
    }

    int maxRetry() {
        return this.maxRetry;
    }

    void sleep() throws InterruptedException {
        Thread.sleep(millis);
    }

    void off() {
        this.on = false;
    }
}
