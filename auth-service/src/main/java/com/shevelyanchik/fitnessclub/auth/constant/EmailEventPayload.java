package com.shevelyanchik.fitnessclub.auth.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailEventPayload {
    USER_HAS_BEEN_CREATED("New User has been created.", "New User with id: %s, email: %s");

    private final String subject;
    private final String messageFormat;
}
