package com.szymon.cache;

import com.szymon.entity.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogCacheManager {

    private final UUID uuid;

    private final List<Log> logs = new ArrayList<>();

    public LogCacheManager() {
        uuid = UUID.randomUUID();
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void add(Log log) {
        log.setUuid(uuid);
        logs.add(log);
    }

}
