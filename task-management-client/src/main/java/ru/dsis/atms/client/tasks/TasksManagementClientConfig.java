package ru.dsis.atms.client.tasks;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TasksManagementClientConfig {

    @Value("${atms.web.task.management.base.url}")
    private String baseUrl;

    @Value("${atms.web.task.management.authentication.token}")
    private String authToken;

    @Bean
    public RestTemplate createInsecureRestTemplate() {
        try {
            var sslContext = SSLContexts.custom()
                                        .loadTrustMaterial(null, TrustAllStrategy.INSTANCE)
                                        .build();

            var sslConnectionSocketFactory = SSLConnectionSocketFactoryBuilder.create()
                                                                              .setSslContext(sslContext)
                                                                              .setHostnameVerifier((s, sslSession) -> true)
                                                                              .build();

            var poolingHttpClientConnectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                                                                                              .setSSLSocketFactory(sslConnectionSocketFactory)
                                                                                              .build();

            CloseableHttpClient httpClient = HttpClients.custom()
                                                        .setConnectionManager(poolingHttpClientConnectionManager)
                                                        .build();

            HttpComponentsClientHttpRequestFactory factory =  new HttpComponentsClientHttpRequestFactory(httpClient);

            return new RestTemplate(factory);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create insecure RestTemplate", e);
        }
    }

    @Bean
    public RestClient restClient() {
        return RestClient.create(createInsecureRestTemplate());
    }

    @Bean
    TasksManagementClient tasksManagementClient() {
        return new TasksManagementClient(restClient(), baseUrl, authToken);
    }
}
