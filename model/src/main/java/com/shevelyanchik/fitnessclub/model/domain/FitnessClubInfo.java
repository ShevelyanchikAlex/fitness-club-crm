package com.shevelyanchik.fitnessclub.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "fitnessClubInfo")
@NoArgsConstructor
public class FitnessClubInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;
}
