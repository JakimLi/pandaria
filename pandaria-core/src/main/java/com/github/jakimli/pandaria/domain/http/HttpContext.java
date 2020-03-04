package com.github.jakimli.pandaria.domain.http;

import com.github.jakimli.pandaria.domain.http.client.HttpMethod;
import com.github.jakimli.pandaria.domain.wait.Waitable;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.jakimli.pandaria.utils.FileUtil.file;
import static com.github.jakimli.pandaria.utils.StringUtil.joinByComma;
import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.*;
import static javax.ws.rs.core.UriBuilder.fromUri;

@Component
@Scope("cucumber-glue")
public class HttpContext implements Waitable<String> {
    private static final String CONTENT_TYPE = "content-type";

    private URI uri;
    private HttpMethod method;
    private String requestBody;
    private MultiPart multiPart = new FormDataMultiPart();

    private Map<String, Cookie> cookies = new HashMap<>();
    private MultivaluedMap<String, Object> requestHeaders = new MultivaluedHashMap<>();

    private String responseBody;
    private int responseStatus;
    private MultivaluedMap<String, String> responseHeaders = new MultivaluedHashMap<>();

    private boolean isForm;

    @Value("${http.ssl.verify:false}")
    private boolean httpSslVerify;

    public void uri(URI uri) {
        this.uri = uri;
        this.isForm = false;
    }

    public void form(URI uri) {
        this.uri = uri;
        this.isForm = true;
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
        return hasAttachment() || this.isForm ? entity(multiPart, multiPart.getMediaType()) : entity(requestBody, contentType());
    }

    private MediaType contentType() {
        return requestHeaders.entrySet().stream()
                .filter(entry -> CONTENT_TYPE.equalsIgnoreCase(entry.getKey()))
                .findFirst()
                .map(entry -> valueOf(joinByComma(entry.getValue())))
                .orElse(APPLICATION_JSON_TYPE);
    }

    public void reset() {
        this.uri = null;
        this.method = null;
        this.requestBody = null;
        this.requestHeaders.clear();
        this.multiPart.cleanup();
        this.cookies.clear();
        this.responseBody = null;
        this.responseStatus = 0;
        this.responseHeaders.clear();
        this.isForm = false;
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
        this.cookies.put(key, new NewCookie(key, value));
    }

    public Map<String, Cookie> cookies() {
        return this.cookies;
    }

    public void attachment(String attachment) {
        File file = file(attachment);
        addAttachment(file.getName(), file);
    }

    public void attachment(String alias, String attachment) {
        addAttachment(alias, file(attachment));
    }

    private void addAttachment(String name, File file) {
        this.multiPart.bodyPart(new FileDataBodyPart(name, file, APPLICATION_OCTET_STREAM_TYPE));
    }

    private boolean hasAttachment() {
        return !multiPart.getBodyParts().isEmpty();
    }

    public void addGlobalRequestHeaders(Map<String, String> headers) {
        headers.entrySet().stream()
                .filter(entry -> !requestHeaders.containsKey(entry.getKey()))
                .forEach(entry -> requestHeader(entry.getKey(), entry.getValue()));
    }

    public void addCookies(Map<String, NewCookie> cookies) {
        this.cookies.putAll(cookies);
    }

    public Object getCookieValue(String cookieName) {
        if (!cookies.containsKey(cookieName)) {
            throw new RuntimeException(String.format("Can't find cookie by name: %s", cookieName));
        }

        return cookies.get(cookieName).getValue();
    }

    public void field(String key, String value) {
        ((FormDataMultiPart) this.multiPart).field(key, value, APPLICATION_JSON_TYPE);
    }
}
