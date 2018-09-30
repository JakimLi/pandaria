package com.github.jakimli.panda.domain.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.github.jakimli.panda.utils.JsonContext.json;

@Component
@Scope("cucumber-glue")
public class JsonVerificationContext {

    @Autowired
    VerificationContext context;

    public void verify(String path, Verification verification) throws Throwable {
        Object actual = json(context.toBeVerified()).path(path);
        verification.verify(actual);
    }
}
