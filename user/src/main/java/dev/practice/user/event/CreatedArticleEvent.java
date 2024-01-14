package dev.practice.user.event;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreatedArticleEvent {

    private final String articleId;
    private final String creatorUserId;

    @Builder
    private CreatedArticleEvent(String articleId, String creatorUserId) {
        this.articleId = articleId;
        this.creatorUserId = creatorUserId;
    }
}
