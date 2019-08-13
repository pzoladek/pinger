package com.pzold.pinger.service;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PingService {

    private final RestTemplate restTemplate;
    private final String pingedApiUrl;
    private final RateLimiter rateLimiter;

    public PingService(final RestTemplate restTemplate,
                       @Value("${api.url}") final String pingedApiUrl,
                       @Value("${requests.per-second}") final int requestsPerSecond) {
        this.restTemplate = restTemplate;
        this.pingedApiUrl = pingedApiUrl;
        this.rateLimiter = RateLimiter.create(requestsPerSecond);
    }

    @Scheduled(fixedRateString = "${scheduling.rate}")
    public void ping() {
        rateLimiter.acquire();
        restTemplate.exchange(pingedApiUrl, HttpMethod.POST, null, Object.class);
    }
}
