package com.github.jakimli.panda.domain.verification;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.github.jakimli.panda.utils.JsonUtil.jsonToObject;
import static com.github.jakimli.panda.utils.StringUtil.assertNotEmpty;

@Component
@Scope("cucumber-glue")
public class VerificationContext {

    private Object toBeVerified;

    public void toBeVerified(String toBeVerified) {
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
