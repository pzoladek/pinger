package com.pzold.pinger.service;

import com.pzold.pinger.repository.LogRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class PingService {

    private final RestTemplate restTemplate;
    private final LogRepository logRepository;
    private final SubscriberService subscriberService;

    public PingService(final RestTemplate restTemplate,
                       final LogRepository logRepository,
                       final SubscriberService subscriberService) {
        this.restTemplate = restTemplate;
        this.logRepository = logRepository;
        this.subscriberService = subscriberService;
    }

    @Scheduled(fixedRateString = "${scheduling.rate}")
    public void ping() {
        final var subscribers = subscriberService.getAllSubscribers();
        for (var subscriber : subscribers) {
            final var response = restTemplate.exchange(subscriber.getUrl(), HttpMethod.GET, null, Object.class);

            logRepository.save(
                    new com.pzold.pinger.model.LogMessage("Pinged " + subscriber.getName() + " with code " + response.getStatusCode().toString(),
                    LocalDateTime.now(),
                    retrieveRequestTime(response.getHeaders()))
            );
        }
    }

    private Long retrieveRequestTime(HttpHeaders headers) {
        return headers.get("REQUEST_TIME").stream()
                .findAny()
                .map(Long::valueOf)
                .orElseThrow(() -> new IllegalStateException("Couldn't retrieve request time header"));
    }
}
