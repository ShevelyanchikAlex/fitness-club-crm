package com.shevelyanchik.fitnessclub.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequestDTO {
    private String email;
    private String password;
}
