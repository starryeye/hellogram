package dev.practice.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.util.List;

// CheckTokenGatewayFilterFactory 이므로 "CheckToken" 으로 접근 가능 함
@Component
public class CheckTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<CheckTokenGatewayFilterFactory.Config> {

    public static class Config {
        private String tokenHeader; //fully expanded, predicates > args 에 해당
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("tokenHeader"); // shortcut 방식 사용 가능 하도록 함
    }

    @Override
    public GatewayFilter apply(Config config) {
        return null;
    }
}
