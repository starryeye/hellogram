package dev.practice.article.usecase;

import dev.practice.article.client.ArticleThumbnailClient;
import dev.practice.article.entity.Article;
import dev.practice.article.entity.ArticleThumbnail;
import dev.practice.article.publisher.ArticleEventPublisher;
import dev.practice.article.publisher.event.CreatedArticleEvent;
import dev.practice.article.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateArticleUsecaseTest {

    @InjectMocks
    private CreateArticleUsecase createArticleUsecase;

    @Mock
    private ArticleRepository mockArticleRepository;

    @Mock
    private ArticleThumbnailClient mockArticleThumbnailClient;

    @Mock
    private ArticleEventPublisher mockArticleEventPublisher;

    @DisplayName("Article 을 저장하고 썸네일을 로드하고 이벤트를 발행한다.")
    @Test
    void execute() {

        // given
        String title = "test-title";
        String content = "test-content";
        String creatorId = "321";
        List<String> thumbnailImageIds = List.of("11", "22", "33");

        CreateArticleUsecase.Input input = CreateArticleUsecase.Input.builder()
                .title(title)
                .content(content)
                .creatorId(creatorId)
                .thumbnailImageIds(thumbnailImageIds)
                .build();

        // stubbing
        String id = "123";
        Article savedArticle = Article.builder()
                .id(id)
                .title(title)
                .content(content)
                .creatorId(creatorId)
                .thumbnails(
                        thumbnailImageIds.stream()
                                .map(ArticleThumbnail::createByOnlyId)
                                .toList()
                )
                .build();
        when(mockArticleRepository.save(any()))
                .thenReturn(Mono.just(savedArticle));

        List<ArticleThumbnail> loadedArticleThumbnails = thumbnailImageIds.stream()
                .map(
                        thumbnailId -> ArticleThumbnail.builder()
                                .id(thumbnailId)
                                .url("http://practice.dev/images/" + thumbnailId)
                                .width(999)
                                .height(999)
                                .build()
                ).toList();
        when(mockArticleThumbnailClient.getArticleThumbnails(eq(thumbnailImageIds)))
                .thenReturn(Flux.fromIterable(loadedArticleThumbnails));

        CreatedArticleEvent articleEvent = CreatedArticleEvent.builder()
                .articleId(id)
                .creatorUserId(creatorId)
                .build();

        doNothing().when(mockArticleEventPublisher).publish(eq(articleEvent)); // 없어도 될 것 처럼 보이지만.. publish 파라미터 값 확인을 수행함 (equals overriding 필요)


        // when
        Mono<Article> result = createArticleUsecase.execute(input);

        // then
        StepVerifier.create(result)
                .assertNext(
                        article -> {
                            assertEquals(title, article.getTitle());
                            assertEquals(content, article.getContent());
                            assertEquals(creatorId, article.getCreatorId());

                            List<String> actualThumbnailIds = article.getThumbnails().stream()
                                    .map(ArticleThumbnail::getId)
                                    .toList();
                            assertEquals(thumbnailImageIds, actualThumbnailIds);
                        }
                )
                .verifyComplete();
    }
}