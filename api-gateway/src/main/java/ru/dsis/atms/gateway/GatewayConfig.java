package ru.dsis.atms.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GatewayConfig {

    @Value("${atms.auth.service.server.baseUrl}")
    private String authServiceBaseUrl;

    @Value("${atms.auth.service.server.validation.endpoint}")
    private String authServiceValidationEndpoint;

    @Bean
    public WebClient authServiceClient() {
        return WebClient.builder()
                        .baseUrl(authServiceValidationEndpoint)
                        .build();
    }

    @Bean
    public AuthServiceRestClientProvider authServiceRestClientProvider() {
        return new AuthServiceRestClientProvider(authServiceBaseUrl);
    }
}
