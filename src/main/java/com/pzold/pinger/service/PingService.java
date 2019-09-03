package com.pzold.pinger.service;

import com.pzold.pinger.config.RestTemplateConfiguration;
import com.pzold.pinger.dto.Subscriber;
import com.pzold.pinger.dto.LogMessage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PingService {

    private final RestTemplate restTemplate;
    private final LogService logService;
    private final SubscriberService subscriberService;

    public PingService(final RestTemplate restTemplate,
                       final LogService logService,
                       final SubscriberService subscriberService) {
        this.restTemplate = restTemplate;
        this.logService = logService;
        this.subscriberService = subscriberService;
    }

    @Scheduled(fixedRateString = "${ping.scheduling.rate}")
    public void ping() {
        subscriberService.getAllSubscribers().forEach(this::sendHttpGet);
    }

    private void sendHttpGet(Subscriber subscriber) {
        try {
            final var response = restTemplate.exchange(subscriber.getUrl(), HttpMethod.GET, prepareHttpEntity(), String.class); //TODO: consider http entity accept-header
            logService.save(
                    new LogMessage("Pinged " + subscriber.getName() + " with code " + response.getStatusCode().toString(),
                            LocalDateTime.now(),
                            retrieveRequestTime(response.getHeaders()))
            );
        } catch (Exception e) {
            e.printStackTrace();
            logService.save(
                    new LogMessage("Couldn't ping " + subscriber.getName(),
                            LocalDateTime.now(),
                            -1L)
            );
        }

        logService.limitNumberOfLogs();
    }

    private Long retrieveRequestTime(HttpHeaders headers) {
        return headers.get(RestTemplateConfiguration.REQUEST_TIME_HEADER).stream()
                .findAny()
                .map(Long::valueOf)
                .orElseThrow(() -> new IllegalStateException("Couldn't retrieve request time header"));
    }

    private HttpEntity prepareHttpEntity() {
        final var headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.ALL));
        return new HttpEntity(headers);
    }
}
