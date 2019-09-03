package com.pzold.pinger.service;

import com.pzold.pinger.dto.LogMessage;
import com.pzold.pinger.repository.LogRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogService {

    private final LogRepository logRepository;

    public LogService(final LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public List<LogMessage> getRecentPingLogs() {
        return logRepository.findTop15ByOrderByIdDesc()
                .stream()
                .map(LogMessage::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<LogMessage> getAllPingLogs() {
        return logRepository.findAll(new Sort(Sort.Direction.DESC, "timestamp"))
                .stream()
                .map(LogMessage::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public LogMessage save(final LogMessage logMessage) {
        return LogMessage.of(logRepository.save(modelOf(logMessage)));
    }

    @Transactional
    public void deleteRecent() {
        logRepository.deleteLatest();
    }

    private com.pzold.pinger.model.LogMessage modelOf(final LogMessage logMessage) {
        return new com.pzold.pinger.model.LogMessage(logMessage.getMessage(), logMessage.getTimestamp(), logMessage.getRequestTimeMillis());
    }
}
