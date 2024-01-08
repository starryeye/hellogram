package dev.practice.article.client;

import dev.practice.article.entity.ArticleThumbnail;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ArticleThumbnailClient {

    Flux<ArticleThumbnail> getArticleThumbnails(List<String> ArticleThumbnailIds);
}
