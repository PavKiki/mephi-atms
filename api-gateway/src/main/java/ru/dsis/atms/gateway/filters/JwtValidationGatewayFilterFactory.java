package ru.dsis.atms.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class JwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<JwtValidationGatewayFilterFactory.Config> {

    private static final Logger log = LoggerFactory.getLogger(JwtValidationGatewayFilterFactory.class);

    @Autowired
    private WebClient authServiceClient;

    public JwtValidationGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info(config.getLogMessage());

            String token = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (token == null || token.isEmpty()) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return authServiceClient.get()
                                    .header("Authorization", token)
                                    .retrieve()
                                    .onStatus(HttpStatusCode::isError, clientResponse -> Mono.error(new RuntimeException("Unauthorized")))
                                    .toBodilessEntity()
                                    .flatMap(response -> chain.filter(exchange));
        };
    }

    public static class Config {
        private String logMessage;

        public Config(String logMessage) {
            this.logMessage = logMessage;
        }

        public String getLogMessage() {
            return logMessage;
        }

        public void setLogMessage(String logMessage) {
            this.logMessage = logMessage;
        }
    }
}