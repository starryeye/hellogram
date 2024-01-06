package dev.practice.article.repository;

import dev.practice.article.entity.Article;
import reactor.core.publisher.Mono;

public interface ArticleRepository {

    Mono<Article> save(Article article); // article 을 넘기면 영속화 시킨 article 을 반환한다.
}
