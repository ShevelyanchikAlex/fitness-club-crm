package com.shevelyanchik.fitnessclub.orderservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FitnessClubInfoDto {
    private Long id;

    @NotBlank
    private String address;

    @NotBlank
    private String description;
}
