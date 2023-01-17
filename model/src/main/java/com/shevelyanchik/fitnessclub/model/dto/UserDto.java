package com.shevelyanchik.fitnessclub.model.dto;

import com.shevelyanchik.fitnessclub.model.domain.user.Role;
import com.shevelyanchik.fitnessclub.model.domain.user.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private Role role;
    private Status status;
}
