package com.shevelyanchik.fitnessclub.auth.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthenticationErrorMessageKey {
    RESOURCE_ALREADY_EXIST("resource.already.exist"),
    USER_VALIDATE_ERROR("user.validate.error");

    private final String key;
}
