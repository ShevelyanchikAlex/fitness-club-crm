package com.shevelyanchik.fitnessclub.userservice.util;

import com.shevelyanchik.fitnessclub.userservice.model.dto.TrainerDto;
import com.shevelyanchik.fitnessclub.userservice.model.dto.TrainerProfile;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserProfile;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TrainerUtils {

    public TrainerProfile buildTrainerProfile(TrainerDto trainerDto, UserProfile userProfile) {
        return TrainerProfile.builder()
                .id(trainerDto.getId())
                .category(trainerDto.getCategory())
                .kindOfSport(trainerDto.getKindOfSport())
                .userProfile(userProfile)
                .build();
    }

}
