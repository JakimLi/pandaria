package com.github.jakimli.panda.domain.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.github.jakimli.panda.utils.JsonContext.json;
import static com.github.jakimli.panda.utils.JsonUtil.jsonToObject;

@Component
@Scope("cucumber-glue")
public class JsonVerificationContext {

    @Autowired
    VerificationContext context;

    public void verify(String path, Verification verification) throws Throwable {
        Object actual = json(toJson(context.toBeVerified())).path(path);
        verification.verify(actual);
    }

    private Object toJson(Object content) throws IOException {
        return content instanceof String ? jsonToObject((String) content) : content;
    }
}
