package dev.practice.gateway.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

// CheckTokenGatewayFilterFactory 이므로 filters 에 "CheckToken" 으로 접근 가능 함
@Component
public class CheckTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<CheckTokenGatewayFilterFactory.Config> {

    @Setter
    @Getter
    public static class Config {
        private String tokenHeaderName; // tokenHeaderName 이라는 변수 명은 fully expanded, predicates > args 에 해당
    }

    public CheckTokenGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("tokenHeaderName"); // shortcut 방식 사용 가능 하도록 함
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            /**
             * tokenHeaderName 이라는 변수의 값을 접근, exchange.getRequest().getHeaders().get(XXX) 가 @Nullable 이다.
             *
             * 응답 값(list) 자체가 null 인 경우도 체크 해줘야하고, list 내부 element 가 없는 경우도 체크하고 싶음..
             *
             * 헤더가 null 인 경우..
             * exchange.getRequest().getHeaders().get(config.tokenHeaderName)이 null 을 반환하면,
             * Optional.ofNullable 은 빈 Optional 객체를 반환한다.
             * 이 경우, filter, flatMap, map 연산은 수행되지 않고, orElseGet 이 실행된다.
             *
             * 헤더가 비어 있는 경우..
             * 헤더 리스트가 비어 있으면,
             * filter 연산에서 false 를 반환하고, 이어지는 flatMap 과 map 은 실행되지 않으며, orElseGet 이 실행된다.
             *
             * userId가 null인 경우..
             * flatMap 을 통해 userId 를 추출하는 과정에서 null 이 반환되면, 이후의 map 연산은 실행되지 않고, orElseGet 이 실행된다.
             */
            return Optional.ofNullable(exchange.getRequest().getHeaders().get(config.tokenHeaderName))
                    .filter(tokenHeaders -> !tokenHeaders.isEmpty())
                    .flatMap(tokenHeaders -> getUserIdByTokenHeader(tokenHeaders.get(0))) // X-I-AM 에서 X-USER-ID 로 변환및 optional 체인으로 flat
                    .map(
                            userId -> {
                                ServerHttpRequest nextRequest = exchange.getRequest()
                                        .mutate()
                                        .header("X-USER-ID", userId)
                                        .build();
                                return exchange
                                        .mutate()
                                        .request(nextRequest)
                                        .build();
                            }
                    )
                    .map(nextExchange -> chain.filter(nextExchange))
                    .orElseGet(() -> chain.filter(exchange));
        };
    }

    // gateway X-I-AM 을 article X-USER-ID 로 매핑
    private static final Map<String, String> tokenUserMap;

    static {
        tokenUserMap = Map.of("123123", "100");
    }

    private Optional<String> getUserIdByTokenHeader(String tokenHeader) {
        return Optional.ofNullable(tokenUserMap.get(tokenHeader));
    }
}
