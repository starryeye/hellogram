package dev.practice.article.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.practice.article.controller.request.CreateArticleRequest;
import dev.practice.article.entity.Article;
import dev.practice.article.entity.ArticleThumbnail;
import dev.practice.article.usecase.CreateArticleUsecase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Slf4j
@AutoConfigureWebTestClient
@WebFluxTest( // controller(presentation) layer(module) test
        controllers = ArticleController.class
)
class ArticleControllerTest {


    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateArticleUsecase mockCreateArticleUsecase;

    @DisplayName("ArticleController, context load test")
    @Test
    void contextLoad() {
    }

    @DisplayName("Article 을 생성한다.")
    @Test
    void createArticle() throws JsonProcessingException {

        // given
        String id = "123";
        String title = "test-title";
        String content = "test-content";
        String creatorId = "321";
        List<String> thumbnailImageIds = List.of("11", "22", "33");

        CreateArticleRequest createArticleRequest = CreateArticleRequest.builder()
                .title(title)
                .content(content)
                .thumbnailIds(thumbnailImageIds)
                .build();


        // stubbing
        CreateArticleUsecase.Input input = CreateArticleUsecase.Input.builder()
                .title(title)
                .content(content)
                .thumbnailImageIds(thumbnailImageIds)
                .creatorId(creatorId)
                .build();

        List<ArticleThumbnail> thumbnails = thumbnailImageIds.stream()
                .map(ArticleThumbnail::createByOnlyId)
                .toList();
        Article article = Article.builder()
                .id(id)
                .title(title)
                .content(content)
                .creatorId(creatorId)
                .thumbnails(thumbnails)
                .build();

        when(mockCreateArticleUsecase.execute(eq(input)))
                .thenReturn(Mono.just(article));


        // when
        // then
        EntityExchangeResult<byte[]> result = webTestClient.post()
                .uri("/api/v1/articles")
                .bodyValue(objectMapper.writeValueAsString(createArticleRequest))
                .header("Content-Type", "application/json")
                .header("X-USER-ID", creatorId)
                .exchange()
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.title").isEqualTo(title)
                .jsonPath("$.content").isEqualTo(content)
                .jsonPath("$.creatorId").isEqualTo(creatorId)
                .jsonPath("$.thumbnails").isArray()
                .returnResult();

        log.info("Result, HTTP Body = {}", new String(Objects.requireNonNull(result.getResponseBody())));
    }
}