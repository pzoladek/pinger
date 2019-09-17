package com.pzold.pinger.service;

import com.pzold.pinger.dto.Metric;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MetricsService {

    private final Map<Metric, Integer> metrics;
    private final MailService mailService;

    public MetricsService(final Map<Metric, Integer> metrics,
                          final MailService mailService) {
        this.metrics = metrics;
        this.mailService = mailService;
    }

    public void increment(final Metric metric) {
        metrics.put(metric, metrics.getOrDefault(metric, 0) + 1);
    }

    @Scheduled(fixedRateString = "${mail.scheduling.rate}")
    public void sendMetricsMail() {
        final var metricsMessage = metrics.entrySet().stream()
                .map(this::prepareMetricsMessage)
                .collect(Collectors.joining("\n"));

        mailService.sendAdminMail("Metrics report for last 2 minutes", metricsMessage);
        metrics.clear();
    }

    private String prepareMetricsMessage(final Map.Entry<Metric, Integer> entry) {
        return entry.getKey().getMessage() + ": " + entry.getValue();
    }

}
