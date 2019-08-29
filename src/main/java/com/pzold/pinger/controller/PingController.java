package com.pzold.pinger.controller;

import com.pzold.pinger.config.RequestTimeMapConfiguration;
import com.pzold.pinger.dto.LogMessage;
import com.pzold.pinger.service.PingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/")
public class PingController {

    private final PingService pingService;
    private final Map<RequestTimeMapConfiguration.RequestType, Long> requestTimes;

    PingController(final PingService pingService, final Map<RequestTimeMapConfiguration.RequestType, Long> requestTimes) {
        this.pingService = pingService;
        this.requestTimes = requestTimes;
    }

    @GetMapping
    LogMessage getHomeEndpoint() {
        System.out.println(requestTimes + " .................................:::");
        return new LogMessage("Hello pinger", LocalDateTime.now(), System.currentTimeMillis());
    }

    @GetMapping("/logs")
    List<LogMessage> getRecentLogs(@RequestParam(value = "date", required = false) String dateLike) {
        if (Objects.nonNull(dateLike)) {
            pingService.getAllPingLogs();
        }
        return pingService.getRecentPingLogs();
    }

}
