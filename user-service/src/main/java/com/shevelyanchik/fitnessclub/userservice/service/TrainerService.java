package com.shevelyanchik.fitnessclub.userservice.service;

import com.shevelyanchik.fitnessclub.userservice.model.dto.TrainerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The TrainerService retrieving information about the trainers.
 *
 * @version 1.0.1
 */
public interface TrainerService {
    /**
     * Creates new Trainer.
     *
     * @param trainerDto TrainerDto that contains all information about Trainer.
     * @return Trainer if trainerDto is not null, throws ServiceException otherwise.
     */
    TrainerDto createTrainer(TrainerDto trainerDto);

    /**
     * If the trainer exists, this method will return a Trainer that contains information about them.
     *
     * @param id Trainer id.
     * @return Trainer if the trainer exists, throws ServiceException otherwise.
     */
    TrainerDto findTrainerById(Long id);

    /**
     * Returns all Trainers.
     *
     * @param pageable Pageable that is needed for pagination.
     * @return Trainers.
     */
    Page<TrainerDto> findAllTrainers(Pageable pageable);

    /**
     * Returns number of Trainers.
     *
     * @return Number of Trainers.
     */
    Long countTrainers();
}
