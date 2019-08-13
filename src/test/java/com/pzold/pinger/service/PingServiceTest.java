package com.pzold.pinger.service;

import com.pzold.pinger.config.SchedulerConfiguration;
import org.awaitility.Duration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@Import(SchedulerConfiguration.class)
@TestPropertySource(properties = {
        "scheduling.rate=100"
})
public class PingServiceTest {
    @SpyBean
    private PingService mockPingService;

    @MockBean
    private RestTemplate restTemplate;


    @Test
    public void scheduler_should_be_invoked_with_fixed_rate() {
        await().atMost(Duration.ONE_SECOND)
                .untilAsserted(() -> verify(mockPingService, atLeast(10)).ping());
    }
}