package com.pzold.pinger.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Subscriber {

    @JsonProperty("name")
    private final String name;

    @JsonProperty("url")
    private final String url;

    private final LocalDateTime subscriptionDate;

    @JsonCreator
    public Subscriber(@JsonProperty("name") final String name,
                      @JsonProperty("url") final String url,
                      @JsonProperty("subscriptionDate") final LocalDateTime subscriptionDate) {
        this.name = name;
        this.url = url;
        this.subscriptionDate = subscriptionDate;
    }

    public static Subscriber of(com.pzold.pinger.model.Subscriber subscriber) {
        return new Subscriber(subscriber.getName(), subscriber.getUrl(), subscriber.getSubscriptionDate());
    }
}
