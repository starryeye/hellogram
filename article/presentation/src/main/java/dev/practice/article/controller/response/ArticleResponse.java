package dev.practice.article.controller.response;

import dev.practice.article.entity.Article;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ArticleResponse {

    private final String id;
    private final String title;
    private final String content;
    private final String creatorId; // 생성한 유저 id
    private final List<ThumbnailResponse> thumbnails;

    @Builder
    private ArticleResponse(String id, String title, String content, String creatorId, List<ThumbnailResponse> thumbnails) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creatorId = creatorId;
        this.thumbnails = thumbnails;
    }

    public static ArticleResponse of(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .creatorId(article.getCreatorId())
                .thumbnails(
                        article.getThumbnails().stream()
                                .map(ThumbnailResponse::of)
                                .toList()
                )
                .build();
    }
}
