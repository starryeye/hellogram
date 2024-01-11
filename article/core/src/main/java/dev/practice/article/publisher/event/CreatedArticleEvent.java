package dev.practice.article.publisher.event;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class CreatedArticleEvent implements ArticleEvent{

    private final String articleId;
    private final String creatorUserId;

    @Builder
    private CreatedArticleEvent(String articleId, String creatorUserId) {
        this.articleId = articleId;
        this.creatorUserId = creatorUserId;
    }
}
