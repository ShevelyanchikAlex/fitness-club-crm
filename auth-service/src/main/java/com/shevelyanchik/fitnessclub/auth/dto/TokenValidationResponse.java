package com.shevelyanchik.fitnessclub.auth.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@Builder
public class TokenValidationResponse {
    private String status;
    private boolean isAuthenticated;
    private String methodType;
    private String username;
    private List<GrantedAuthority> authorities;
}
