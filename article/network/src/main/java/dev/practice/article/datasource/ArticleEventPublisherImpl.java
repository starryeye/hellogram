package dev.practice.article.datasource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.practice.article.publisher.ArticleEventPublisher;
import dev.practice.article.publisher.event.ArticleEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ArticleEventPublisherImpl implements ArticleEventPublisher {

    private final String BINDING_NAME = "articles-out-0";

    private final ObjectMapper objectMapper;
    private final StreamBridge streamBridge;

    @Override
    public void publish(ArticleEvent articleEvent) {

        try {

            String message = objectMapper.writeValueAsString(articleEvent);

            streamBridge.send(BINDING_NAME, message);

        } catch (JsonProcessingException e) {
            log.error("article object to string processing error..", e);
        }
    }
}
