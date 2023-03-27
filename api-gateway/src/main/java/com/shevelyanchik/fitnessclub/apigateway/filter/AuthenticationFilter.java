package com.shevelyanchik.fitnessclub.apigateway.filter;

import com.shevelyanchik.fitnessclub.apigateway.model.GrantedAuthority;
import com.shevelyanchik.fitnessclub.apigateway.model.TokenValidationResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private static final String BASE_URL = System.getenv("AUTH_SERVICE_URL");
    private static final String VALIDATE_TOKEN_ENDPOINT = "/api/v1/auth-service/auth/validateToken";

    private final WebClient.Builder webClientBuilder;

    public AuthenticationFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> Mono
                .fromCallable(exchange::getRequest)
                .filter(RouterValidator::isSecuredApiEndpoints)
                .map(request -> {
                    String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                    if (Objects.isNull(token)) {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                    }
                    return token;
                })
                .flatMap(token ->
                        webClientBuilder
                                .baseUrl(BASE_URL)
                                .build()
                                .get()
                                .uri(VALIDATE_TOKEN_ENDPOINT)
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .retrieve()
                                .bodyToMono(TokenValidationResponse.class)
                )
                .doOnNext(tokenValidationResponse ->
                        exchange.getRequest()
                                .mutate()
                                .header(HeaderName.USERNAME, tokenValidationResponse.getUsername())
                                .header(
                                        HeaderName.AUTHORITIES,
                                        tokenValidationResponse
                                                .getAuthorities()
                                                .stream()
                                                .map(GrantedAuthority::getAuthority)
                                                .reduce("", (a, b) -> a + "," + b)
                                )
                )
                .thenReturn(exchange)
                .doOnError(error -> log.error("Auth error", error))
                .onErrorResume(error ->
                        Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED))
                )
                .flatMap(chain::filter);
    }

    @NoArgsConstructor
    public static class Config {
    }
}