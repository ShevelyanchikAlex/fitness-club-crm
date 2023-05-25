package com.shevelyanchik.fitnessclub.userservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainerProfile {

    private Long id;

    private String category;

    private String kindOfSport;

    private UserProfile userProfile;

}
