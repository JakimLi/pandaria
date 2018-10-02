package com.github.jakimli.pandaria.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.github.jakimli.pandaria.utils.JsonUtil.jsonToObject;
import static com.github.jakimli.pandaria.utils.StringUtil.assertNotEmpty;

@Component
@Scope("cucumber-glue")
public class VerificationContext {

    private Object toBeVerified;

    public void toBeVerified(Object toBeVerified) {
        this.toBeVerified = toBeVerified;
    }

    public Object toJsonObject() throws IOException {
        if (!(toBeVerified instanceof String)) {
            return toBeVerified;
        }

        String verifying = (String) toBeVerified;
        assertNotEmpty(verifying);
        return jsonToObject(verifying);
    }
}
