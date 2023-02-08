package com.shevelyanchik.fitnessclub.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FitnessClubInfoDto {
    private Long id;
    private String address;
    private String description;
}
