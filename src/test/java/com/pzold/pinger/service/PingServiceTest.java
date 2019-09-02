package com.pzold.pinger.service;

import com.pzold.pinger.config.RestTemplateConfiguration;
import com.pzold.pinger.config.SchedulerConfiguration;
import com.pzold.pinger.dto.Subscriber;
import com.pzold.pinger.model.LogMessage;
import com.pzold.pinger.repository.LogRepository;
import org.awaitility.Duration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@Import(SchedulerConfiguration.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class PingServiceTest {
    @SpyBean
    private PingService pingServiceSpy;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private LogRepository logRepository;

    @MockBean
    private SubscriberService subscriberService;

    @Before
    public void setUp() throws Exception {
        when(restTemplate.exchange("http://bad-url/", HttpMethod.GET, null, Object.class))
                .thenThrow(RestClientException.class);
        when(restTemplate.exchange("http://good-url/", HttpMethod.GET, null, Object.class))
                .thenReturn(ResponseEntity.ok().header(RestTemplateConfiguration.REQUEST_TIME_HEADER, "1234").build());
    }

    @Test
    public void scheduler_should_be_invoked_with_fixed_rate() {
        await().atMost(Duration.ONE_SECOND)
                .untilAsserted(() -> verify(pingServiceSpy, atLeast(10)).ping());
    }

    @Test
    public void should_catch_rest_exception() {
        // given
        when(subscriberService.getAllSubscribers())
                .thenReturn(List.of(new Subscriber("app-name1", "http://bad-url/", LocalDateTime.of(2019, 9, 2, 13, 0, 0))));

        // when
        pingServiceSpy.ping();

        // then
        verify(logRepository).save(new LogMessage("Couldn't ping app-nam1", any(), -1L));
    }

    @Test
    public void should_ping_successfully() {
        // given
        when(subscriberService.getAllSubscribers())
                .thenReturn(List.of(new Subscriber("app-name2", "http://good-url/", LocalDateTime.of(2019, 9, 2, 13, 0, 0))));

        // when
        pingServiceSpy.ping();

        // then
        verify(logRepository).save(new LogMessage("Pinged app-name2 with code 200 OK", any(), 1234L));
    }

}