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

    @Value("${atms.auth.service.server.public.api.validation.endpoint}")
    private String authServicePublicApiValidationEndpoint;

    @Bean
    public WebClient authServiceClient() {
        return WebClient.builder()
                        .baseUrl(authServiceBaseUrl + authServiceValidationEndpoint)
                        .build();
    }

    @Bean(name = "authServicePublicApiValidationClient")
    public WebClient authServicePublicApiValidationClient() {
        return WebClient.builder()
                .baseUrl(authServiceBaseUrl + authServicePublicApiValidationEndpoint)
                .build();
    }

    @Bean
    public AuthServiceRestClientProvider authServiceRestClientProvider() {
        return new AuthServiceRestClientProvider(authServiceBaseUrl);
    }
}
