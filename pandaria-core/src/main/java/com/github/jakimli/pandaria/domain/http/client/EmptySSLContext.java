package com.github.jakimli.pandaria.domain.http.client;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import static javax.net.ssl.SSLContext.getInstance;

class EmptySSLContext {

    static SSLContext emptyContext() {
        try {
            SSLContext context = getInstance("TLS");

            context.init(null, new TrustManager[]{new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }
            }}, new SecureRandom());

            return context;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
            //ignore
        }
        return null;
    }
}
