package com.github.jakimli.pandaria.domain.wait;

import com.github.jakimli.pandaria.domain.VerificationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@Component
@Scope("cucumber-glue")
public class Wait {
    @Autowired
    private VerificationContext verifier;

    private int millis;
    private int maxRetry;
    private boolean on;

    private List<Waitable> waitables = newArrayList();

    public void configure(int millis, int maxRetry) {
        if (maxRetry < 0) {
            fail("Invalid retry times: " + maxRetry);
        }
        this.on = true;
        this.millis = millis;
        this.maxRetry = maxRetry;
    }

    public void waitable(Waitable waitable) {
        this.waitables.add(waitable);
    }

    boolean on() {
        return on;
    }

    void retry() {
        assertNotNull(waitables);
        this.waitables.forEach(Waitable::retry);
        last().ifPresent(waitable -> verifier.toBeVerified(waitable.result()));
    }

    private Optional<Waitable> last() {
        return Optional.ofNullable(this.waitables.get(this.waitables.size() - 1));
    }

    int maxRetry() {
        return this.maxRetry;
    }

    void sleep() throws InterruptedException {
        Thread.sleep(millis);
    }

    void off() {
        this.on = false;
        this.waitables = newArrayList();
    }

    int millis() {
        return this.millis;
    }
}
