package ru.dsis.atms.gateway;

import org.springframework.web.client.RestClient;

public class AuthServiceRestClientProvider {
    private final String baseUrl;

    public AuthServiceRestClientProvider(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public RestClient getClient(String endpoint) {
        return RestClient.builder()
                         .baseUrl(baseUrl + endpoint)
                         .build();
    }
}
