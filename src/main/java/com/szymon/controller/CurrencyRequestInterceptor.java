package com.szymon.controller;

import com.szymon.cache.LogCacheManager;
import com.szymon.entity.Log;
import com.szymon.service.LogService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class CurrencyRequestInterceptor implements HandlerInterceptor {

    private final LogCacheManager logCacheManager;

    private final LogService logService;

    public CurrencyRequestInterceptor(LogCacheManager logCacheManager, LogService logService) {
        this.logCacheManager = logCacheManager;
        this.logService = logService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logCacheManager.add(new Log("Request URI: " + request.getServletPath() + " HTTP method: " + request.getMethod()));

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        logCacheManager.add(new Log("Response status: " + response.getStatus()));

        logService.saveAllLogs(logCacheManager.getLogs());
    }

}
