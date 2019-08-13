package com.pzold.pinger.controller;

import com.pzold.pinger.service.PingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PingController {

    private final PingService pingService;

    PingController(final PingService pingService) {
        this.pingService = pingService;
    }


    void ping() {
        pingService.ping();
    }
}
