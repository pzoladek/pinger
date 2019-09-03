package com.pzold.pinger.service;

import com.pzold.pinger.dto.LogMessage;
import com.pzold.pinger.repository.LogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogService {

    private final LogRepository logRepository;
    private final Integer maxLogsNumber;
    private final Integer recordsToDeleteNumber;

    public LogService(final LogRepository logRepository,
                      final @Value("${logs.max-number}") Integer maxLogsNumber,
                      final @Value("${logs.to-delete-number}") Integer recordsToDeleteNumber) {
        this.logRepository = logRepository;
        this.maxLogsNumber = maxLogsNumber;
        this.recordsToDeleteNumber = recordsToDeleteNumber;
    }

    @Transactional
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
    public void limitNumberOfLogs() {
        if (logRepository.count() >= maxLogsNumber) {
            deleteOldest(recordsToDeleteNumber);
        }
    }

    private void deleteOldest(final int numberOfRecords) {
        logRepository.findAll()
                .stream()
                .limit(numberOfRecords)
                .forEach(lm -> logRepository.deleteById(lm.getId()));
    }

    private com.pzold.pinger.model.LogMessage modelOf(final LogMessage logMessage) {
        return new com.pzold.pinger.model.LogMessage(logMessage.getMessage(), logMessage.getTimestamp(), logMessage.getRequestTimeMillis());
    }
}
