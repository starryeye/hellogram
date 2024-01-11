package dev.practice.article.publisher.event;

import dev.practice.article.entity.Article;

public class ArticleEventFactory {

    public static ArticleEvent generateCreatedArticleEvent(Article article) {
        return CreatedArticleEvent.builder()
                .articleId(article.getId())
                .creatorUserId(article.getCreatorId())
                .build();
    }
}
