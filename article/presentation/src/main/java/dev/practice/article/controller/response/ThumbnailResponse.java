package dev.practice.article.controller.response;

import dev.practice.article.entity.ArticleThumbnail;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ThumbnailResponse {

    private final String id;
    private final String url;
    private final Integer width;
    private final Integer height;

    @Builder
    private ThumbnailResponse(String id, String url, Integer width, Integer height) {
        this.id = id;
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public static ThumbnailResponse of(ArticleThumbnail articleThumbnail) {
        return ThumbnailResponse.builder()
                .id(articleThumbnail.getId())
                .url(articleThumbnail.getUrl())
                .width(articleThumbnail.getWidth())
                .height(articleThumbnail.getHeight())
                .build();
    }
}
