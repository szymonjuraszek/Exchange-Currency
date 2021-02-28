package com.szymon.controller;

import com.szymon.cache.LogCacheManager;
import com.szymon.entity.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private final LogCacheManager logCacheManager;

    public RestTemplateInterceptor(LogCacheManager logCacheManager) {
        this.logCacheManager = logCacheManager;
    }

    @Override
    public ClientHttpResponse intercept(
            HttpRequest req, byte[] reqBody, ClientHttpRequestExecution ex) throws IOException {

        logCacheManager.add(new Log("Request URI: " + req.getURI().toString() + " , Http method: " + req.getMethod().toString()));

        ClientHttpResponse response = ex.execute(req, reqBody);

        logCacheManager.add(new Log("Response Status: " + response.getStatusCode().toString()));

        return response;
    }
}
