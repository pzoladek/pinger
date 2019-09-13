package com.pzold.pinger.service;

import com.pzold.pinger.dto.Subscriber;
import com.pzold.pinger.exception.SubscriberAlreadyExistsException;
import com.pzold.pinger.exception.SubscriberNotFoundException;
import com.pzold.pinger.repository.SubscriberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;

    public SubscriberService(final SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    public Subscriber subscribe(final Subscriber subscriber) {
        subscriberRepository.findAll().stream()
                .filter(sub -> sub.getName().equals(subscriber.getName()) || sub.getUrl().equals(subscriber.getUrl()))
                .findAny()
                .ifPresent(s -> {
                    throw new SubscriberAlreadyExistsException("Subscriber's name or url already exists.");
                });

        return Subscriber.of(subscriberRepository.save(modelOf(subscriber)));
    }

    public List<Subscriber> getAllSubscribers() {
        return subscriberRepository.findAll()
                .stream()
                .map(Subscriber::of)
                .collect(Collectors.toList());
    }

    public void unsubscribe(final Subscriber subscriber) {
        subscriberRepository.findAll().stream()
                .filter(sub -> sub.getName().equals(subscriber.getName()) && sub.getUrl().equals(subscriber.getUrl()))
                .findAny()
                .orElseThrow(() -> {
                    throw new SubscriberNotFoundException("Subscriber does not exist.");
                });


        subscriberRepository.deleteById(subscriber.getName());
    }

    private com.pzold.pinger.model.Subscriber modelOf(Subscriber subscriber) {
        return new com.pzold.pinger.model.Subscriber(subscriber.getUrl(), subscriber.getName(), LocalDateTime.now());
    }
}
