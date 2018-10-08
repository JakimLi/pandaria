package com.github.jakimli.pandaria.domain.http;

import com.github.jakimli.pandaria.domain.http.client.HttpMethod;
import com.github.jakimli.pandaria.domain.wait.Waitable;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import java.io.File;
import java.net.URI;
import java.util.List;

import static com.github.jakimli.pandaria.utils.FileUtil.file;
import static com.google.common.collect.Lists.newArrayList;
import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM_TYPE;
import static javax.ws.rs.core.UriBuilder.fromUri;

@Component
@Scope("cucumber-glue")
public class HttpContext implements Waitable<String> {
    private URI uri;
    private HttpMethod method;
    private String requestBody;
    private MultiPart attachments = new MultiPart();

    private List<Cookie> cookies = newArrayList();
    private MultivaluedMap<String, Object> requestHeaders = new MultivaluedHashMap<>();

    private String responseBody;
    private int responseStatus;
    private MultivaluedMap<String, String> responseHeaders = new MultivaluedHashMap<>();

    @Value("${http.ssl.verify:false}")
    private boolean httpSslVerify;

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

    public Entity<?> requestBody() {
        return hasAttachment() ? entity(attachments, attachments.getMediaType())
                : entity(requestBody, APPLICATION_JSON_TYPE);
    }

    public void reset() {
        this.uri = null;
        this.method = null;
        this.requestBody = null;
        this.requestHeaders.clear();
        this.cookies.clear();
        this.attachments.cleanup();

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

    public boolean isHttpSslVerify() {
        return httpSslVerify;
    }

    public void cookie(String key, String value) {
        this.cookies.add(new NewCookie(key, value));
    }

    public List<Cookie> cookies() {
        return this.cookies;
    }

    public void attachment(String attachment) {
        File file = file(attachment);
        this.attachments.bodyPart(new FileDataBodyPart(file.getName(), file, APPLICATION_OCTET_STREAM_TYPE));
    }

    private boolean hasAttachment() {
        return !attachments.getBodyParts().isEmpty();
    }
}
