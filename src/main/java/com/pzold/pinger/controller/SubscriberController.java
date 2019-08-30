package com.pzold.pinger.controller;

import com.pzold.pinger.dto.Subscriber;
import com.pzold.pinger.service.SubscriberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscribers")
public class SubscriberController {

    private final SubscriberService subscriberService;

    public SubscriberController(final SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @PostMapping
    Subscriber subscribe(@RequestBody Subscriber subscriber) {
        System.out.println(subscriber.toString() + " controller");
        return subscriberService.subscribe(subscriber);
    }

    @GetMapping
    List<Subscriber> getAllSubscribers() {
        return subscriberService.getAllSubscribers();
    }
}