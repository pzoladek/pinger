package com.pzold.pinger.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "LOGS", schema = "logs")
@Data
@Builder
public class LogMessage implements Serializable {

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
