package com.github.jakimli.pandaria.domain.http;

import com.github.jakimli.pandaria.domain.http.client.HttpMethod;
import com.github.jakimli.pandaria.domain.wait.Waitable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.UriBuilder.fromUri;

@Component
@Scope("cucumber-glue")
public class HttpContext implements Waitable<String> {
    private URI uri;
    private HttpMethod method;
    private String requestBody;
    private MultivaluedMap<String, Object> requestHeaders = new MultivaluedHashMap<>();

    private String responseBody;
    private int responseStatus;
    private MultivaluedMap<String, String> responseHeaders = new MultivaluedHashMap<>();


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
        this.responseHeaders.clear();
    }

    public void requestHeader(String key, String value) {
        requestHeaders.add(key, value);
    }

    public MultivaluedMap<String, Object> requestHeaders() {
        return requestHeaders;
    }

    public void responseHeaders(MultivaluedMap<String, String> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public List<String> responseHeader(String key) {
        return this.responseHeaders.get(key);
    }

    public void queryParameter(String name, String value) {
        this.uri = fromUri(this.uri)
                .queryParam(name, value)
                .build();
    }

    @Override
    public void retry() {
        send();
    }

    @Override
    public String result() {
        return this.responseBody;
    }
}
