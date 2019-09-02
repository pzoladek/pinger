package com.pzold.pinger.service;

import com.pzold.pinger.config.RestTemplateConfiguration;
import com.pzold.pinger.dto.Subscriber;
import com.pzold.pinger.model.LogMessage;
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

    @Scheduled(fixedRateString = "${ping.scheduling.rate}")
    public void ping() {
        subscriberService.getAllSubscribers().forEach(this::sendHttpGet);
    }

    private void sendHttpGet(Subscriber subscriber) {
        try {
            final var response = restTemplate.exchange(subscriber.getUrl(), HttpMethod.GET, null, Object.class);
            logRepository.save(
                    new LogMessage("Pinged " + subscriber.getName() + " with code " + response.getStatusCode().toString(),
                            LocalDateTime.now(),
                            retrieveRequestTime(response.getHeaders()))
            );
        } catch (Exception e) {
            e.printStackTrace();
            logRepository.save(
                    new LogMessage("Couldn't ping " + subscriber.getName(),
                            LocalDateTime.now(),
                            -1L)
            );
        }
    }

    private Long retrieveRequestTime(HttpHeaders headers) {
        return headers.get(RestTemplateConfiguration.REQUEST_TIME_HEADER).stream()
                .findAny()
                .map(Long::valueOf)
                .orElseThrow(() -> new IllegalStateException("Couldn't retrieve request time header"));
    }

}
