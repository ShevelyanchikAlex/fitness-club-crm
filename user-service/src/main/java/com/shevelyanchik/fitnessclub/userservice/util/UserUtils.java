package com.shevelyanchik.fitnessclub.userservice.util;

import com.shevelyanchik.fitnessclub.userservice.model.dto.FilePayload;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserProfile;
import com.shevelyanchik.fitnessclub.userservice.model.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserUtils {

    public UserProfile buildUserProfile(User user, FilePayload filePayload) {
        return UserProfile.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .profileImagePayload(filePayload)
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .role(user.getRole())
                .build();
    }

}
