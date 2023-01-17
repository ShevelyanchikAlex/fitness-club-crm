package com.shevelyanchik.fitnessclub.model.domain;

import com.shevelyanchik.fitnessclub.model.domain.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Trainer extends User {
    @Column(name = "category")
    private String category;

    @Column(name = "kindOfSport")
    private String kindOfSport;
}
