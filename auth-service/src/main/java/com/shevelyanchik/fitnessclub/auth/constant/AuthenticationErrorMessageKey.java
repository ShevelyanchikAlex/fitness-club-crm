package com.shevelyanchik.fitnessclub.auth.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthenticationErrorMessageKey {

    public static final String RESOURCE_ALREADY_EXIST = "resource.already.exist";
    public static final String USER_VALIDATE_ERROR = "user.validate.error";
}
