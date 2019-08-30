package com.pzold.pinger.controller;

import com.pzold.pinger.dto.LogMessage;
import com.pzold.pinger.service.LogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {

    private final LogService logService;

    LogController(final LogService logService) {
        this.logService = logService;
    }

    @GetMapping
    LogMessage getHomeEndpoint() {
        return new LogMessage("Hello pinger", LocalDateTime.now(), System.currentTimeMillis());
    }

    @GetMapping("/recent")
    List<LogMessage> getRecentLogs() {
        return logService.getRecentPingLogs();
    }

    @GetMapping("/all")
    List<LogMessage> getAllLogs() {
        return logService.getAllPingLogs();
    }
}
