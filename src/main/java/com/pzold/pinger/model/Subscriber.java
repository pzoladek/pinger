package com.pzold.pinger.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "SUBSCRIBERS", schema = "logs")
@Data
@NoArgsConstructor
public class Subscriber {

    public Subscriber(String name, String url, LocalDateTime subscriptionDate) {
        this.name = name;
        this.url = url;
        this.subscriptionDate = subscriptionDate;
    }

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ID")
//    private Integer id;

    @Id
    @Column(name = "URL")
    private String url;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SUBSCRIPTION_DATE")
    private LocalDateTime subscriptionDate;

}
