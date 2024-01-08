package dev.practice.article.datasource.rest.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ImageClient {

    private final WebClient imageWebClient;

    public Flux<ImageResponse> getImagesByIds(List<String> imageIds) {

        String requestParam = String.join(",", imageIds);

        return imageWebClient.get()
                .uri("/api/v1/images?imageIds=" + requestParam)
                .retrieve()
                .bodyToFlux(ImageResponse.class);
    }
}
