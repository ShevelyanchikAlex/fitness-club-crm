package com.shevelyanchik.fitnessclub.auth.web.controller;

import com.shevelyanchik.fitnessclub.auth.attribute.AttributeName;
import com.shevelyanchik.fitnessclub.auth.dto.TokenValidationResponse;
import lombok.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth-service/auth/validateToken")
@RequiredArgsConstructor
public class TokenValidationController {

    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TokenValidationResponse> validateToken(HttpServletRequest request) {
        User user = (User) request.getAttribute(AttributeName.USERNAME);
        String username = user.getUsername();
        List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) request.getAttribute(AttributeName.AUTHORITIES);
        return ResponseEntity.ok(
                TokenValidationResponse.builder()
                        .status(HttpStatus.OK.getReasonPhrase())
                        .methodType(HttpMethod.GET.name())
                        .username(username)
                        .authorities(grantedAuthorities)
                        .isAuthenticated(true)
                        .build()
        );
    }
}
