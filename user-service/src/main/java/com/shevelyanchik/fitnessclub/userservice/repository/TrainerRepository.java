package com.shevelyanchik.fitnessclub.userservice.repository;

import com.shevelyanchik.fitnessclub.userservice.model.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    Optional<Trainer> findTrainerByUserEmail(String email);

}
