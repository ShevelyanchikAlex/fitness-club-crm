package com.shevelyanchik.fitnessclub.orderservice.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerDto {
    private Long id;
    private String category;
    private String kindOfSport;
    private UserDto userDto;
}
