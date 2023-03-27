package com.shevelyanchik.fitnessclub.apigateway.config;

import com.shevelyanchik.fitnessclub.apigateway.filter.AuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(
            RouteLocatorBuilder builder,
            AuthenticationFilter authFilter) {
        String authUrl = System.getenv("AUTH_SERVICE_URL");
        String userUrl = System.getenv("USER_SERVICE_URL");
        String orderUrl = System.getenv("ORDER_SERVICE_URL");

        return builder.routes()
                .route("user-service", r -> r.path("/api/v1/user-service/**")
                        .filters(f -> f.filter(authFilter.apply(
                                new AuthenticationFilter.Config())))
                        .uri(userUrl))
                .route("order-service", r -> r.path("/api/v1/order-service/**")
                        .filters(f -> f.filter(authFilter.apply(
                                new AuthenticationFilter.Config())))
                        .uri(orderUrl))
                .route("auth-service", r -> r.path("/api/v1/auth-service/**")
                        .filters(f -> f.filter(authFilter.apply(
                                new AuthenticationFilter.Config())))
                        .uri(authUrl))
                .build();
    }
}
