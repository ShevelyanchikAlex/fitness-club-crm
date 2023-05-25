package com.shevelyanchik.fitnessclub.orderservice.constant;

import lombok.Getter;

import java.util.Set;

@Getter
public enum Role {
    GUEST(Set.of(Permission.GUEST_PERMISSION)),
    USER(Set.of(Permission.USER_PERMISSION)),
    TRAINER(Set.of(Permission.USER_PERMISSION, Permission.TRAINER_PERMISSION)),
    ADMIN(Set.of(Permission.USER_PERMISSION, Permission.TRAINER_PERMISSION, Permission.ADMIN_PERMISSION));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
