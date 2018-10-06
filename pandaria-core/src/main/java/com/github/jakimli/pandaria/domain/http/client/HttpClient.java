package com.github.jakimli.pandaria.domain.http.client;

import com.github.jakimli.pandaria.domain.http.HttpContext;
import org.glassfish.jersey.logging.LoggingFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;

import static com.github.jakimli.pandaria.domain.http.client.EmptySSLContext.emptyContext;
import static java.util.logging.Logger.getLogger;
import static org.glassfish.jersey.SslConfigurator.getDefaultContext;
import static org.glassfish.jersey.client.HttpUrlConnectorProvider.SET_METHOD_WORKAROUND;
import static org.glassfish.jersey.logging.LoggingFeature.Verbosity.PAYLOAD_ANY;

class HttpClient {

    private Client client;
    private HttpContext context;

    static HttpClient in(HttpContext context) {
        return new HttpClient(context);
    }

    private HttpClient(HttpContext context) {
        ClientBuilder builder = ClientBuilder.newBuilder()
                .property(SET_METHOD_WORKAROUND, true)
                .register(logAny())
                .sslContext(context.isHttpSslVerify() ? getDefaultContext() : emptyContext());

        if (!context.isHttpSslVerify()) {
            builder.hostnameVerifier((s1, s2) -> true);
        }

        this.client = builder.build();
        this.context = context;
    }

    private Object logAny() {
        return new LoggingFeature(getLogger("HTTP"), Level.INFO, PAYLOAD_ANY, null);
    }

    void request(Function<Builder, Response> method) {
        Builder target = client.target(context.uri()).request();
        addHeaders(target, context.requestHeaders());
        Response response = method.apply(target);
        updateHttpContext(response);
        response.close();
    }

    private void addHeaders(Builder target, MultivaluedMap<String, Object> headers) {
        headers.keySet().forEach(key -> {
            List<Object> values = headers.get(key);
            values.forEach(value -> target.header(key, value));
        });
    }

    private void updateHttpContext(Response response) {
        this.context.status(response.getStatus());
        this.context.responseHeaders(response.getStringHeaders());
        this.context.responseBody(response.readEntity(String.class));
    }
}
