package com.shevelyanchik.fitnessclub.userservice.model.constants;

import lombok.Getter;

import java.util.Set;

@Getter
public enum Role {
    USER(Set.of(Permission.USER_PERMISSION)),
    ADMIN(Set.of(Permission.USER_PERMISSION, Permission.ADMIN_PERMISSION));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
