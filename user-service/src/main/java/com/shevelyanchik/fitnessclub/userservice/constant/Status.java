package com.shevelyanchik.fitnessclub.userservice.constant;

import com.shevelyanchik.fitnessclub.userservice.exception.ValidationException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Status {
    ACTIVE,
    BANNED;

    public static Status getStatusByName(String statusName) {
        return Arrays.stream(Status.values())
                .filter(status -> status.name().equalsIgnoreCase(statusName))
                .findFirst()
                .orElseThrow(() -> new ValidationException("Not supported Status: " + statusName));
    }
}
