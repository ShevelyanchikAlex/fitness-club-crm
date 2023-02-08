package com.shevelyanchik.fitnessclub.model.dto;

import com.shevelyanchik.fitnessclub.model.domain.user.User;
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
    private User user;
}
