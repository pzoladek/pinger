package com.pzold.pinger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

@Configuration
public class RestTemplateConfiguration {

    public static final String REQUEST_TIME_HEADER = "REQUEST_TIME";

    @Bean
    public RestTemplate restTemplate() {
        final var restTemplate = new RestTemplate();

        final var interceptors = restTemplate.getInterceptors();
        interceptors.add(new RestTemplateInterceptor());
        restTemplate.setInterceptors(interceptors); // measure request time

        final var converter = new StringHttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL)); // accept all responses
        restTemplate.getMessageConverters().add(converter);
        return restTemplate;
    }

    private class RestTemplateInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            final var startTime = System.currentTimeMillis();
            final var response = execution.execute(request, body);
            final var endTime = System.currentTimeMillis();
            response.getHeaders().add(REQUEST_TIME_HEADER, Long.toString(endTime - startTime));
            return response;
        }
    }
}
