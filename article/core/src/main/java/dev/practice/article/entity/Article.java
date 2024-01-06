package dev.practice.article.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class Article {

    private final String id;
    private final String title;
    private final String content;
    private final List<ArticleThumbnail> thumbnails;
    private final String creatorId;

    @Builder
    private Article(String id, String title, String content, List<ArticleThumbnail> thumbnails, String creatorId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.thumbnails = thumbnails;
        this.creatorId = creatorId;
    }

    public static Article create(String title, String content, List<ArticleThumbnail> thumbnails, String creatorId) {
        return Article.builder()
                .id(null)
                .title(title)
                .content(content)
                .thumbnails(thumbnails)
                .creatorId(creatorId)
                .build();
    }
}
