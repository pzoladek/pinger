package com.pzold.pinger.controller;

import com.pzold.pinger.dto.ErrorMessage;
import com.pzold.pinger.dto.Subscriber;
import com.pzold.pinger.exception.SubscriberAlreadyExistsException;
import com.pzold.pinger.exception.SubscriberNotFoundException;
import com.pzold.pinger.service.SubscriberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/subscribers")
public class SubscriberController {

    private final SubscriberService subscriberService;

    public SubscriberController(final SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Subscriber subscribe(@RequestBody Subscriber subscriber) {
        return subscriberService.subscribe(subscriber);
    }

    @GetMapping
    List<Subscriber> getAllSubscribers() {
        return subscriberService.getAllSubscribers();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    void unsubscribe(@RequestBody Subscriber subscriber) {
        subscriberService.unsubscribe(subscriber);
    }

    @ExceptionHandler(SubscriberAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleSubscriberAlreadyExistsException(SubscriberAlreadyExistsException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SubscriberNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleSubscriberNotFoundException(SubscriberNotFoundException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), LocalDateTime.now()), HttpStatus.NOT_FOUND);
    }
}