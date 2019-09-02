package com.pzold.pinger.service;

import com.pzold.pinger.dto.Subscriber;
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
        return Subscriber.of(subscriberRepository.save(modelOf(subscriber)));
    }

    public List<Subscriber> getAllSubscribers() {
        return subscriberRepository.findAll()
                .stream()
                .map(Subscriber::of)
                .collect(Collectors.toList());
    }

    public void unsubscribe(final Subscriber subscriber) {
        final var subscriptionUrl = subscriber.getUrl();
        if (subscriberRepository.existsById(subscriptionUrl)) {
            validateSubscriberName(subscriber.getName(), subscriberRepository.findById(subscriptionUrl).get().getName());
            subscriberRepository.deleteById(subscriptionUrl);
        }
    }

    private com.pzold.pinger.model.Subscriber modelOf(Subscriber subscriber) {
        return new com.pzold.pinger.model.Subscriber(subscriber.getName(), subscriber.getUrl(), LocalDateTime.now());
    }

    private void validateSubscriberName(final String dtoName, final String modelName) {
        if (!dtoName.equalsIgnoreCase(modelName)) {
            throw new IllegalArgumentException("Subscriber's name does not match subscribed website's name.");
        }
    }

}
