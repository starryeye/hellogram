package dev.practice.article.datasource.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CircuitBreakerConfig {


    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> imageCustomizer() {

        /**
         * "imageCb" CircuitBreaker 는 publisher 를 조작하여 로깅을 남기도록 설정
         * 나머지 상세 설정은 .yml 로 진행 (설정을 하지 않을 경우 기본 설정으로 되지 않는듯.. timelimitter 에 타임아웃 1초 아닌거 같음)
         */
        String[] targetCircuitBreakerIds = new String[]{"imageCb"};

        return reactiveResilience4JCircuitBreakerFactory -> {
            reactiveResilience4JCircuitBreakerFactory.addCircuitBreakerCustomizer(
                    getEventLogger(), targetCircuitBreakerIds
            );
        };
    }

    private Customizer<CircuitBreaker> getEventLogger() {

        return Customizer.once(
                circuitBreaker -> {

                    String circuitBreakerName = circuitBreaker.getName();
                    circuitBreaker.getEventPublisher()
                            .onSuccess(
                                    event -> log.info("circuit breaker : {}, success", circuitBreakerName)
                            )
                            .onError(
                                    event -> log.info("circuit breaker : {}, error : {}", circuitBreakerName, event.getThrowable().toString())
                            )
                            .onStateTransition(
                                    event -> log.info(
                                            "circuit breaker : {}, state changed from : {} to : {}",
                                            circuitBreakerName,
                                            event.getStateTransition().getFromState(),
                                            event.getStateTransition().getToState()
                                    )
                            )
                            .onSlowCallRateExceeded(
                                    event -> log.info("circuit breaker : {}, slow call rate exceeded : {}", circuitBreakerName, event.getSlowCallRate())
                            )
                            .onFailureRateExceeded(
                                    event -> log.info("circuit breaker : {}, failure rate exceeded: {}", circuitBreakerName, event.getFailureRate())
                            );
                },
                CircuitBreaker::getName
        );
    }
}
