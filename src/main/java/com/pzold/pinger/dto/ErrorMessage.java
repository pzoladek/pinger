package com.pzold.pinger.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorMessage {

    private final String message;
    private final LocalDateTime timestamp;
}
