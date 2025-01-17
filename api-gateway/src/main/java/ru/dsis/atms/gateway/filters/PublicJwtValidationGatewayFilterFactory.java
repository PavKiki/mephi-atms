package ru.dsis.atms.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.dsis.atms.gateway.AuthServiceRestClientProvider;

@Component
public class PublicJwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<PublicJwtValidationGatewayFilterFactory.Config> {

    private static final Logger log = LoggerFactory.getLogger(PublicJwtValidationGatewayFilterFactory.class);

    @Value("${atms.auth.service.server.public.api.validation.endpoint}")
    private String authServicePublicApiEndpoint;

    @Autowired
    private AuthServiceRestClientProvider authServiceRestClientProvider;

    public PublicJwtValidationGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(PublicJwtValidationGatewayFilterFactory.Config config) {
        var api = authServiceRestClientProvider.getClient(authServicePublicApiEndpoint);

        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (token == null || token.isEmpty()) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return api.method(HttpMethod.GET).exchange((request, response) -> {
                if (response.getStatusCode().isError()) {
                    return Mono.error(new RuntimeException("Unauthorized"));
                }
                return chain.filter(exchange);
            });
        };
    }

    public static class Config {}
}
