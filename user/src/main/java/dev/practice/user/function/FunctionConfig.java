package dev.practice.user.function;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.practice.user.event.CreatedArticleEvent;
import dev.practice.user.usecase.NewArticleAlertUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@RequiredArgsConstructor
@Configuration
public class FunctionConfig {

    private final NewArticleAlertUsecase newArticleAlertUsecase;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public Function<Flux<String>, Flux<String>> toFollowerMessageBinding() {

        return input -> input.flatMap(
                s -> {
                    try {

                        // input string parsing
                        CreatedArticleEvent createdArticleEvent = objectMapper.readValue(s, CreatedArticleEvent.class);

                        // usecase execute
                        return newArticleAlertUsecase.execute(
                                        createdArticleEvent.getArticleId(),
                                        createdArticleEvent.getCreatorUserId()
                                )
                                .<String>handle((createdNotificationEvent, sink) -> {
                                    try {

                                        // output string publish
                                        sink.next(objectMapper.writeValueAsString(createdNotificationEvent));

                                    } catch (JsonProcessingException e) {
                                        sink.error(new RuntimeException(e));
                                    }
                                });

                    } catch (JsonProcessingException e) {
                        return Flux.error(new RuntimeException(e));
                    }
                }
        );
    }
}
