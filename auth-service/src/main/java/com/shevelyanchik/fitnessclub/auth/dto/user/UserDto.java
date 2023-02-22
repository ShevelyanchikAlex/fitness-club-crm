package com.shevelyanchik.fitnessclub.auth.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private Role role;
    private Status status;
}
