package com.pzold.pinger.dto;

public enum Metric {
    SUBSCRIBED("New users subscribed"),
    UNSUBSCRIBED("Users unsubscribed"),
    PING_SUCCESS("Pings successful"),
    PING_FAILURE("Pings failed");

    private final String message;

    Metric(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
