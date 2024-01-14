package dev.practice.user.event;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreatedNotificationEvent {

    private final String from;
    private final String to;
    private final String message;

    @Builder
    private CreatedNotificationEvent(String from, String to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
    }
}
