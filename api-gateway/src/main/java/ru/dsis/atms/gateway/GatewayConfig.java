//package ru.dsis.atms.gateway;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class GatewayConfig {
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("login", r -> r.path("/api/auth/login")
//                        .filters(f -> f.stripPrefix(1))
//                        .uri("http://localhost:50001"))
//                .route("users-service", r -> r.path("/api/users/**", "/api/roles/**", "/api/permissions/**")
//                        .filters(f -> f.stripPrefix(1))
//                        .uri("http://localhost:50002"))
//                .build();
//    }
//}