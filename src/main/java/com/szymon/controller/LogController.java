package com.szymon.controller;

import com.szymon.entity.Log;
import com.szymon.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LogController {

    private final Logger logger = LoggerFactory.getLogger(LogController.class);

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/logs")
    public List<Log> getAllLogs() {
        logger.info("Get all logs");

       return logService.getAllLogs();
    }
}
