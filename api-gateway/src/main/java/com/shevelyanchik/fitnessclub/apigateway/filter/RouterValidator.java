package com.shevelyanchik.fitnessclub.apigateway.filter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RouterValidator {
    private static final List<String> openApiEndpoints = List.of(
            "/api/v1/auth-service/auth/signup",
            "/api/v1/auth-service/auth/login"
    );

    public static boolean isSecuredApiEndpoints(ServerHttpRequest request) {
        return openApiEndpoints.stream()
                .noneMatch(uri -> request.getURI().getPath().contains(uri));
    }
}
