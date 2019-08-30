package com.pzold.pinger.service;

import com.pzold.pinger.dto.LogMessage;
import com.pzold.pinger.repository.LogRepository;
import org.springframework.stereotype.Service;

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

    public List<LogMessage> getAllPingLogs() {
        return logRepository.findAll()
                .stream()
                .map(LogMessage::of)
                .collect(Collectors.toList());
    }
}
