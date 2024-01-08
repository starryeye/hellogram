package dev.practice.article.datasource;

import dev.practice.article.client.ArticleThumbnailClient;
import dev.practice.article.datasource.rest.image.ImageClient;
import dev.practice.article.datasource.rest.image.ImageResponse;
import dev.practice.article.entity.ArticleThumbnail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ArticleThumbnailClientImpl implements ArticleThumbnailClient {

    private final ImageClient imageClient;

    @Override
    public Flux<ArticleThumbnail> getArticleThumbnails(List<String> articleThumbnailIds) {

        return imageClient.getImagesByIds(articleThumbnailIds)
                .map(ImageResponse::toEntity);
    }
}
