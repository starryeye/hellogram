package dev.practice.article.datasource;

import dev.practice.article.client.ArticleThumbnailClient;
import dev.practice.article.datasource.rest.image.ImageClient;
import dev.practice.article.datasource.rest.image.ImageResponse;
import dev.practice.article.entity.ArticleThumbnail;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
@Component
public class ArticleThumbnailClientImpl implements ArticleThumbnailClient {

    private final ImageClient imageClient;
//    private final ReactiveCircuitBreakerFactory cbf;
    private final ReactiveResilience4JCircuitBreakerFactory cbf;

    @Override
    public Flux<ArticleThumbnail> getArticleThumbnails(List<String> articleThumbnailIds) {

        return imageClient.getImagesByIds(articleThumbnailIds)
                .transform(
                        toRun -> {
                            ReactiveCircuitBreaker imageCb = cbf.create("imageCb");
                            return imageCb.run(toRun, fallback(articleThumbnailIds));
                        }
                ).map(ImageResponse::toEntity);
    }

    private static Function<Throwable, Flux<ImageResponse>> fallback(List<String> articleThumbnailIds) {
        return throwable -> Flux.fromIterable(articleThumbnailIds)
                .map(ImageResponse::fallback);
    }
}
