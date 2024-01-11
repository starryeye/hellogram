package dev.practice.article.usecase;

import dev.practice.article.client.ArticleThumbnailClient;
import dev.practice.article.entity.Article;
import dev.practice.article.entity.ArticleThumbnail;
import dev.practice.article.publisher.event.ArticleEvent;
import dev.practice.article.publisher.ArticleEventPublisher;
import dev.practice.article.publisher.event.ArticleEventFactory;
import dev.practice.article.repository.ArticleRepository;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import javax.inject.Named;
import java.util.List;

@RequiredArgsConstructor
@Named // spring 에서 @Named 를 읽어서 spring bean 으로 등록해준다. (core 모듈에는 spring 의존성이 없다.)
public class CreateArticleUsecase {

    private final ArticleRepository articleRepository;
    private final ArticleThumbnailClient articleThumbnailClient;
    private final ArticleEventPublisher articleEventPublisher;

    @EqualsAndHashCode
    public static class Input {
        private final String title;
        private final String content;
        private final String creatorId;
        private final List<String> thumbnailImageIds;

        @Builder
        private Input(String title, String content, String creatorId, List<String> thumbnailImageIds) {
            this.title = title;
            this.content = content;
            this.creatorId = creatorId;
            this.thumbnailImageIds = thumbnailImageIds;
        }
    }

    public Mono<Article> execute(Input input) {

        /**
         * 전달 받은 article 정보(thumbnail ids 포함) 를 저장하고 (mongodb 저장)
         * thumbnail ids 로 image 들을 구해와서 (image server 요청)
         * 최종 article 을 응답한다.
         *
         * + 최종 응답하기 전 event 발행
         */

        Article article = createdArticleByInput(input);

        return articleRepository.save(article)
                .flatMap(this::loadThumbnails)
                .doOnNext(this::publishCreatedArticleEvent);
    }

    private Article createdArticleByInput(Input input) {
        return Article.create(
                input.title,
                input.content,
                input.thumbnailImageIds.stream()
                        .map(ArticleThumbnail::createByOnlyId)
                        .toList(),
                input.creatorId
        );
    }

    private Mono<Article> loadThumbnails(Article savedArticle) {

            List<String> thumbnailImageIds = savedArticle.getThumbnails().stream()
                    .map(ArticleThumbnail::getId)
                    .toList();

            return articleThumbnailClient.getArticleThumbnails(thumbnailImageIds)
                    .collectList()
                    .map(
                            gotArticleThumbnails -> Article.withArticleThumbnails(savedArticle, gotArticleThumbnails)
                    );
    }

    private void publishCreatedArticleEvent(Article completedArticle) {

        ArticleEvent createdArticleEvent = ArticleEventFactory.generateCreatedArticleEvent(completedArticle);

        articleEventPublisher.publish(createdArticleEvent);
    }
}
