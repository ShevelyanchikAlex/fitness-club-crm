package com.shevelyanchik.fitnessclub.orderservice.constant;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Permission {
    GUEST_PERMISSION,
    USER_PERMISSION,
    TRAINER_PERMISSION,
    ADMIN_PERMISSION;


    public static boolean existPermissionByName(String permissionName) {
        return Arrays.stream(Permission.values())
                .anyMatch(permission -> permission.name().equalsIgnoreCase(permissionName));
    }

}
