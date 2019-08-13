package com.pzold.pinger.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LogMessage {
    @JsonProperty("message")
    private final String message;
    @JsonProperty("timestamp")
    private final LocalDateTime timestamp;
    @JsonProperty("requestTime")
    private final Long requestTimeMillis;

    @JsonCreator
    public LogMessage(@JsonProperty("message") final String message,
                      @JsonProperty("timestamp") final LocalDateTime timestamp,
                      @JsonProperty("requestTime") final Long requestTimeMillis) {
        this.message = message;
        this.timestamp = timestamp;
        this.requestTimeMillis = requestTimeMillis;
    }
}
