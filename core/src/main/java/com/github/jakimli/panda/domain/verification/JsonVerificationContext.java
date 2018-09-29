package com.github.jakimli.panda.domain.verification;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.github.jakimli.panda.utils.JsonContext.json;
import static com.github.jakimli.panda.utils.JsonUtil.jsonToObject;

@Component
@Scope("cucumber-glue")
public class JsonVerificationContext {

    private Object toBeVerified;

    public void verify(String path, Verification verification) throws Throwable {
        Object actual = json(this.toBeVerified).path(path);
        verification.verify(actual);
    }

    public void toBeVerified(String toBeVerified) throws IOException {
        this.toBeVerified = jsonToObject(toBeVerified);
    }
}
