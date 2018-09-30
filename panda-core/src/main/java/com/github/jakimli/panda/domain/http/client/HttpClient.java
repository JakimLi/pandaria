package com.github.jakimli.panda.domain.http.client;

import com.github.jakimli.panda.domain.http.HttpContext;

public interface HttpClient {
    void send(HttpContext context);
}
