package com.shevelyanchik.fitnessclub.newsservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAuthentication {
    private String username;
    private String authorities;
}
