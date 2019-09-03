package com.pzold.pinger.config;

import com.pzold.pinger.model.LogMessage;
import com.pzold.pinger.repository.LogRepository;
import com.pzold.pinger.service.LogService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;

@Component
public class LogMessageListener {

    private final LogService logService;
    private final Integer maxLogsNumber;

    public LogMessageListener(@Lazy final LogService logService,
                              @Value("${ping.max-logs}") final Integer maxLogsNumber) {
        this.logService = logService;
        this.maxLogsNumber = maxLogsNumber;
    }

    @PostPersist
    public void postPersist(LogMessage logMessage) {
        System.out.println("post persisteed");
//        if (logService.getAllPingLogs().size() >= maxLogsNumber) {
//            logService.deleteRecent();
//        }
//        System.out.println(logService.getAllPingLogs().size());
    }
}
