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
    private SubscriberService subscriberService;

    @MockBean
    private LogService logService;

    @Before
    public void setUp() throws Exception {
        when(restTemplate.exchange(eq("http://bad-url/"), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenThrow(RestClientException.class);
        when(restTemplate.exchange(eq("http://good-url/"), eq(HttpMethod.GET), any(), eq(String.class)))
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
        verify(logService).save(argThat(lm -> lm.getMessage().equals("Couldn't ping app-name1") && lm.getRequestTimeMillis() == -1L));
    }

    @Test
    public void should_ping_successfully() {
        // given
        when(subscriberService.getAllSubscribers())
                .thenReturn(List.of(new Subscriber("app-name2", "http://good-url/", LocalDateTime.of(2019, 9, 2, 13, 0, 0))));

        // when
        pingServiceSpy.ping();

        // then
        verify(logService).save(argThat(lm ->
                lm.getMessage().equals("Pinged app-name2 with code 200 OK") && lm.getRequestTimeMillis() == 1234L));
    }

}