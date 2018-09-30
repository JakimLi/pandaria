package com.github.jakimli.panda.domain.http.client;

import com.github.jakimli.panda.domain.http.HttpContext;
import com.github.jakimli.panda.domain.http.client.HttpMethod.Method;

import javax.ws.rs.client.SyncInvoker;

public class HttpDelete extends HttpClient implements Method {
    @Override
    public void send(HttpContext context) {
        request(context, SyncInvoker::delete);
    }
}
