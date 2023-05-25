package com.shevelyanchik.fitnessclub.auth.dto.user;

import com.shevelyanchik.fitnessclub.auth.dto.ValidatorRegex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotEmpty
    @Pattern(regexp = ValidatorRegex.NAME_REGEX)
    private String name;

    @NotEmpty
    @Pattern(regexp = ValidatorRegex.SURNAME_REGEX)
    private String surname;

    @NotEmpty
    @Pattern(regexp = ValidatorRegex.PASSWORD_REGEX)
    private String password;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    @Pattern(regexp = ValidatorRegex.PHONE_NUMBER_REGEX)
    private String phoneNumber;

    private Role role;

    private Status status;
}
