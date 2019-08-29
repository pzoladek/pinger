package com.pzold.pinger.config;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RequestTimeMapConfiguration {

    @Bean
    public Map<RequestType, Long> requestTimes() {
        return new HashMap<>();
    }

    @Data(staticConstructor = "of")
    public static class RequestType {
        private final HttpMethod httpMethod;
        private final String path;
    }
}
