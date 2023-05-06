package com.shevelyanchik.fitnessclub.userservice.model.dto;

import com.shevelyanchik.fitnessclub.userservice.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerDto {

    private Long id;

    @NotBlank
    private String category;

    @NotBlank
    private String kindOfSport;

    @NotNull
    private User user;

}
