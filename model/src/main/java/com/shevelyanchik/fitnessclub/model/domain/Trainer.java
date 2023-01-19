package com.shevelyanchik.fitnessclub.model.domain;

import com.shevelyanchik.fitnessclub.model.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "trainer")
@NoArgsConstructor
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "category")
    private String category;

    @Column(name = "kindOfSport")
    private String kindOfSport;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}
