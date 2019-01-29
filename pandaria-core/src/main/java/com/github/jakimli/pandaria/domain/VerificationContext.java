package com.github.jakimli.pandaria.domain;

import com.github.jakimli.pandaria.utils.JsonContext;
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

    public Object json(String path) throws IOException {
        return JsonContext.json(toJsonObject()).path(path);
    }

    public String plainText() {
        return toBeVerified.toString();
    }

    private Object toJsonObject() throws IOException {
        if (!(toBeVerified instanceof String)) {
            return toBeVerified;
        }

        String verifying = (String) toBeVerified;
        assertNotEmpty(verifying);
        return jsonToObject(verifying);
    }
}
