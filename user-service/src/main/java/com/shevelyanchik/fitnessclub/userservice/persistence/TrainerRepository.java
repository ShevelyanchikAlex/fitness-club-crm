package com.shevelyanchik.fitnessclub.userservice.persistence;

import com.shevelyanchik.fitnessclub.userservice.model.domain.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
}
