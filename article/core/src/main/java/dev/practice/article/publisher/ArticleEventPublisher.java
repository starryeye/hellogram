package dev.practice.article.publisher;

import dev.practice.article.publisher.event.ArticleEvent;

public interface ArticleEventPublisher {

    void publish(ArticleEvent articleEvent);
}
