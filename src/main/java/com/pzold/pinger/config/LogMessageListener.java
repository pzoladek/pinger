package com.pzold.pinger.config;

import com.pzold.pinger.model.LogMessage;
import com.pzold.pinger.repository.LogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;

@Component
public class LogMessageListener {

    private final LogRepository logRepository;
    private final Integer maxLogsNumber;

    public LogMessageListener(@Lazy final LogRepository logRepository,
                              @Value("${ping.max-logs}") final Integer maxLogsNumber) {
        this.logRepository = logRepository;
        this.maxLogsNumber = maxLogsNumber;
    }

//    @PostPersist
//    public void postPersist(LogMessage logMessage) {
//        System.out.println("post persisteed");
//        if (logRepository.findAll().size() >= maxLogsNumber) {
//           // logRepository.deleteLatest();
//            //logRepository.findTop15ByOrderByIdDesc();
//        }
//    }
}
