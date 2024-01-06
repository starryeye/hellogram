package dev.practice.article.controller;

import dev.practice.article.controller.request.CreateArticleRequest;
import dev.practice.article.controller.response.ArticleResponse;
import dev.practice.article.usecase.CreateArticleUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    private final CreateArticleUsecase createArticleUsecase;

    @PostMapping
    public Mono<ArticleResponse> createArticle(
            @RequestBody CreateArticleRequest request,
            @RequestHeader("X-USER-ID") String userId
    ) {

        CreateArticleUsecase.Input input = CreateArticleUsecase.Input.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .creatorId(userId)
                .thumbnailImageIds(request.getThumbnailIds())
                .build();

        return createArticleUsecase.execute(input)
                .map(ArticleResponse::of);

    }
}
