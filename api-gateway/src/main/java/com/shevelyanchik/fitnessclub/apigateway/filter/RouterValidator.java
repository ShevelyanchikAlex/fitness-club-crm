package com.shevelyanchik.fitnessclub.apigateway.filter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RouterValidator {
    private static final List<String> OPEN_API_ENDPOINTS = List.of(
            "/api/v1/auth-service/auth/signup",
            "/api/v1/auth-service/auth/login",
            "/api/v1/user-service/trainers",
            "/api/v1/user-service/trainers/count",
            "/api/v1/order-service/services",
            "/api/v1/order-service/services/count",
            "/api/v1/news-service/news/all",
            "/api/v1/news-service/news/count"
    );

    public static boolean isSecuredApiEndpoints(ServerHttpRequest request) {
        return OPEN_API_ENDPOINTS.stream()
                .noneMatch(uri -> request.getURI().getPath().contains(uri));
    }
}
