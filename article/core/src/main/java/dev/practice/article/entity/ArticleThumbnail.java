package dev.practice.article.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ArticleThumbnail {

    private final String id;
    private final String url;
    private final Integer width;
    private final Integer height;

    @Builder
    private ArticleThumbnail(String id, String url, Integer width, Integer height) {
        this.id = id;
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public static ArticleThumbnail createById(String id) {
        return ArticleThumbnail.builder()
                .id(id)
                .url("")
                .width(0)
                .height(0)
                .build();
    }
}
