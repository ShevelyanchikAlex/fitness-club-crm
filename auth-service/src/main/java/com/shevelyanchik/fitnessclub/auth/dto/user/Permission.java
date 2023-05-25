package com.shevelyanchik.fitnessclub.auth.dto.user;

import lombok.Getter;

@Getter
public enum Permission {
    GUEST_PERMISSION,
    USER_PERMISSION,
    TRAINER_PERMISSION,
    ADMIN_PERMISSION
}
