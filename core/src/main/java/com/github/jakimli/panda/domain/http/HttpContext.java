package com.github.jakimli.panda.domain.http;

import com.github.jakimli.panda.domain.http.client.HttpMethod;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.net.URI;

@Component
@Scope("cucumber-glue")
public class HttpContext {
    private URI uri;
    private HttpMethod method;
    private String requestBody;
    private MultivaluedMap<String, Object> requestHeaders = new MultivaluedHashMap<>();

    private String responseBody;
    private int responseStatus;


    public void uri(URI uri) {
        this.uri = uri;
    }

    public void method(HttpMethod method) {
        this.method = method;
    }

    public void send() {
        method.send(this);
    }

    public URI uri() {
        return this.uri;
    }

    public void responseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public int status() {
        return responseStatus;
    }

    public void status(int status) {
        this.responseStatus = status;
    }

    public String responseBody() {
        return responseBody;
    }

    public void requestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String requestBody() {
        return requestBody;
    }

    public void reset() {
        this.uri = null;
        this.method = null;
        this.requestBody = null;
        this.requestHeaders.clear();

        this.responseBody = null;
        this.responseStatus = 0;
    }

    public void requestHeader(String key, String value) {
        requestHeaders.add(key, value);
    }

    public MultivaluedMap<String, Object> requestHeaders() {
        return requestHeaders;
    }
}
