package com.pzold.pinger.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LogMessage {
    private final String message;
    private final LocalDateTime timestamp;

    @JsonCreator
    public LogMessage(@JsonProperty("message") final String message,
                      @JsonProperty("timestamp") final LocalDateTime timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
