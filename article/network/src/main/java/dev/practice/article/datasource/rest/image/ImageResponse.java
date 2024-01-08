package dev.practice.article.datasource.rest.image;

import dev.practice.article.entity.ArticleThumbnail;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ImageResponse {

    private final Long id;
    private final String url;
    private final Integer width;
    private final Integer height;

    @Builder
    private ImageResponse(Long id, String url, Integer width, Integer height) {
        this.id = id;
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public ArticleThumbnail toEntity() {
        return ArticleThumbnail.builder()
                .id(id.toString())
                .url(url)
                .width(width)
                .height(height)
                .build();
    }
}
