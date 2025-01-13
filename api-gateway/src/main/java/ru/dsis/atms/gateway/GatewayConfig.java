package ru.dsis.atms.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GatewayConfig {

    @Value("${atms.auth.service.server.endpoint}")
    private String authServiceEndpoint;

    @Bean
    public WebClient authServiceClient() {
        return WebClient.builder()
                        .baseUrl(authServiceEndpoint)
                        .build();
    }
}
