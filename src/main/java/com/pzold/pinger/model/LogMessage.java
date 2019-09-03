package com.pzold.pinger.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "LOGS", schema = "logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogMessage implements Serializable {

    public LogMessage(String message, LocalDateTime timestamp, Long requestTimeMillis) {
        this.message = message;
        this.timestamp = timestamp;
        this.requestTimeMillis = requestTimeMillis;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "LOCAL_TIME")
    private LocalDateTime timestamp;

    @Column(name = "REQUEST_TIME")
    private Long requestTimeMillis;
}
