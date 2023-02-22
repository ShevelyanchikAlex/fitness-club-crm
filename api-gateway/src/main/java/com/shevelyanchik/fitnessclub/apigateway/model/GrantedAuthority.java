package com.shevelyanchik.fitnessclub.apigateway.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrantedAuthority {
    private String authority;
}
