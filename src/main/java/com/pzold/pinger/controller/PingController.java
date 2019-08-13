package com.pzold.pinger.controller;

import com.pzold.pinger.dto.LogMessage;
import com.pzold.pinger.service.PingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/")
public class PingController {

    private final PingService pingService;

    PingController(final PingService pingService) {
        this.pingService = pingService;
    }

    @GetMapping
    LogMessage getHomeEndpoint() {
        return new LogMessage("Hello pinger", LocalDateTime.now(), System.currentTimeMillis());
    }
}
