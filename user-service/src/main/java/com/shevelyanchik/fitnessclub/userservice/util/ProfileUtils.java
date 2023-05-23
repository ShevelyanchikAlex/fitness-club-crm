package com.shevelyanchik.fitnessclub.userservice.util;

import com.shevelyanchik.fitnessclub.userservice.model.dto.FilePayload;
import com.shevelyanchik.fitnessclub.userservice.model.dto.TrainerDto;
import com.shevelyanchik.fitnessclub.userservice.model.dto.TrainerProfile;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserProfile;
import com.shevelyanchik.fitnessclub.userservice.model.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProfileUtils {

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

    public TrainerProfile buildTrainerProfile(TrainerDto trainerDto, UserProfile userProfile) {
        return TrainerProfile.builder()
                .id(trainerDto.getId())
                .category(trainerDto.getCategory())
                .kindOfSport(trainerDto.getKindOfSport())
                .userProfile(userProfile)
                .build();
    }

}
