package com.pzold.pinger.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "LOG_MESSAGE")
@Data
@Builder
public class LogMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "TIMESTAMP")
    private LocalDateTime timestamp;

    @Column(name = "REQUEST_TIME")
    private Long requestTimeMillis;
}
