package com.shevelyanchik.fitnessclub.orderservice.e2e.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
