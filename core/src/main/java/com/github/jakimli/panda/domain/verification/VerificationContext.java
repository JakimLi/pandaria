package com.github.jakimli.panda.domain.verification;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class VerificationContext {

    private Object toBeVerified;

    public void toBeVerified(String toBeVerified) {
        this.toBeVerified = toBeVerified;
    }

    public Object toBeVerified() {
        return toBeVerified;
    }
}
