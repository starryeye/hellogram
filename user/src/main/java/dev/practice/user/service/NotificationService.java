package dev.practice.user.service;

import dev.practice.user.event.CreatedNotificationEvent;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final String CREATED_ARTICLE_NOTIFICATION = "%s 님이 %s 게시글을 작성하였습니다.";

    public String getCreatedArticleMessage(String followeeId, String articleId) {
        return CREATED_ARTICLE_NOTIFICATION.formatted(followeeId, articleId);
    }

    public CreatedNotificationEvent getCreatedNotificationEvent(String to, String from, String message) {
        return CreatedNotificationEvent.builder()
                .to(to)
                .from(from)
                .message(message)
                .build();
    }
}
