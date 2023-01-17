package com.shevelyanchik.fitnessclub.persistence;

import com.shevelyanchik.fitnessclub.model.domain.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
}
