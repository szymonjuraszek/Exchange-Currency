package com.szymon.service;

import com.szymon.entity.Log;
import com.szymon.repository.LogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void saveAllLogs(List<Log> logs) {
        logRepository.saveAll(logs);
    }
}
