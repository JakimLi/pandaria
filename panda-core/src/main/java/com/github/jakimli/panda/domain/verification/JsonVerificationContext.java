package com.github.jakimli.panda.domain.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.github.jakimli.panda.utils.JsonContext.json;

@Component
@Scope("cucumber-glue")
public class JsonVerificationContext {

    @Autowired
    private VerificationContext toBeVerified;

    public void verify(String path, Verification verification) throws Throwable {
        verification.verify(json(toBeVerified.toJsonObject()).path(path));
    }
}
