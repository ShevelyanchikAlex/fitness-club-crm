package com.shevelyanchik.fitnessclub.auth.exception;

import lombok.Getter;

@Getter
public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super(message);
    }

}
