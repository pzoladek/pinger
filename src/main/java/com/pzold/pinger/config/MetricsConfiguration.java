package com.pzold.pinger.config;

import com.pzold.pinger.dto.Metric;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MetricsConfiguration {

    @Bean
    public Map<Metric, Integer> metrics() {
        return new HashMap<>();
    }
}
