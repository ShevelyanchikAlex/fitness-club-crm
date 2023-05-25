package com.shevelyanchik.fitnessclub.userservice.constant;

import com.shevelyanchik.fitnessclub.userservice.exception.ValidationException;
import lombok.Getter;

import java.util.Arrays;
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

    public static Role getRoleByName(String roleName) {
        return Arrays.stream(Role.values())
                .filter(role -> role.name().equalsIgnoreCase(roleName))
                .findFirst()
                .orElseThrow(() -> new ValidationException("Not supported Role: " + roleName));
    }

}
