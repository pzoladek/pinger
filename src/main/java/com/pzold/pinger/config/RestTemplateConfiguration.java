package com.pzold.pinger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        final var restTemplate = new RestTemplate();
        var interceptors = restTemplate.getInterceptors();
        interceptors.add(new RestTemplateInterceptor());
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    private class RestTemplateInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            final var startTime = System.currentTimeMillis();
            final var response = execution.execute(request, body);
            final var endTime = System.currentTimeMillis();
            response.getHeaders().add("REQUEST_TIME", Long.toString(endTime - startTime));
            return response;
        }
    }
}
