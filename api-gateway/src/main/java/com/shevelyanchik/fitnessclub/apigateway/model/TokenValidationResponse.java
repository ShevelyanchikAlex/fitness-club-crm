package com.shevelyanchik.fitnessclub.apigateway.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenValidationResponse {
    private String status;
    private boolean isAuthenticated;
    private String methodType;
    private String username;
    private List<GrantedAuthority> authorities;
}
