package com.shevelyanchik.fitnessclub.apigateway.config;

import com.shevelyanchik.fitnessclub.apigateway.filter.AuthenticationFilter;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GatewayConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public RouteLocator routes(
            RouteLocatorBuilder builder,
            AuthenticationFilter authFilter) {
        return builder.routes()
                .route("user-service", r -> r.path("/api/v1/user-service/**")
                        .filters(f -> f.filter(authFilter.apply(
                                new AuthenticationFilter.Config())))
                        .uri("lb://user-service"))
                .route("order-service", r -> r.path("/api/v1/order-service/**")
                        .filters(f -> f.filter(authFilter.apply(
                                new AuthenticationFilter.Config())))
                        .uri("lb://order-service"))
                .route("auth-service", r -> r.path("/api/v1/auth-service/**")
                        .filters(f -> f.filter(authFilter.apply(
                                new AuthenticationFilter.Config())))
                        .uri("lb://auth-service"))
                .route("news-service", r -> r.path("/api/v1/news-service/**")
                        .filters(f -> f.filter(authFilter.apply(
                                new AuthenticationFilter.Config())))
                        .uri("lb://news-service"))
                .build();
    }
}
