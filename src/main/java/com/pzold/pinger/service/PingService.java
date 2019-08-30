package com.pzold.pinger.service;

import com.google.common.util.concurrent.RateLimiter;
import com.pzold.pinger.dto.LogMessage;
import com.pzold.pinger.repository.LogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PingService {

    private final RestTemplate restTemplate;
    private final String pingedApiUrl;
    private final RateLimiter rateLimiter;
    private final LogRepository logRepository;

    public PingService(final RestTemplate restTemplate,
                       @Value("${api.url}") final String pingedApiUrl,
                       @Value("${requests.per-second}") final int requestsPerSecond,
                       final LogRepository logRepository) {
        this.restTemplate = restTemplate;
        this.pingedApiUrl = pingedApiUrl;
        this.rateLimiter = RateLimiter.create(requestsPerSecond);
        this.logRepository = logRepository;
    }

    @Scheduled(fixedRateString = "${scheduling.rate}")
    public void ping() {
        rateLimiter.acquire();
        final var response = restTemplate.exchange(pingedApiUrl, HttpMethod.GET, null, Object.class);

        logRepository.save(new com.pzold.pinger.model.LogMessage(
                "Pinged " + pingedApiUrl + " with code " + response.getStatusCode().toString(),
                LocalDateTime.now(),
                retrieveRequestTime(response.getHeaders()))
        );
    }

    public List<LogMessage> getRecentPingLogs() {
        return logRepository.findTop15ByOrderByIdDesc()
                .stream()
                .map(LogMessage::of)
                .collect(Collectors.toList());
    }

    public List<LogMessage> getAllPingLogs() {
        return logRepository.findAll()
                .stream()
                .map(LogMessage::of)
                .collect(Collectors.toList());
    }

    private Long retrieveRequestTime(HttpHeaders headers) {
        return headers.get("REQUEST_TIME").stream()
                .findAny()
                .map(Long::valueOf)
                .orElseThrow(() -> new IllegalStateException("Couldn't retrieve request time header"));
    }
}
