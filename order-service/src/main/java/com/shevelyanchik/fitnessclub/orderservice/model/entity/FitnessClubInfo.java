package com.shevelyanchik.fitnessclub.orderservice.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "fitness_club_info")
@NoArgsConstructor
public class FitnessClubInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;
}
