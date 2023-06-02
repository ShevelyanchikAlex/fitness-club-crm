package com.shevelyanchik.fitnessclub.userservice.service;

import com.shevelyanchik.fitnessclub.userservice.model.dto.TrainerDto;
import com.shevelyanchik.fitnessclub.userservice.model.dto.TrainerProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The TrainerService interface provides methods for managing trainers.
 *
 * @version 1.1.1
 */
public interface TrainerService {
    /**
     * Creates a new trainer using the provided trainer DTO.
     *
     * @param trainerDto The trainer DTO containing the trainer information.
     * @return The created trainer DTO.
     */
    TrainerDto createTrainer(TrainerDto trainerDto);

    /**
     * Updates an existing trainer with the provided updated trainer DTO.
     *
     * @param updatedTrainerDto The updated trainer DTO containing the modified trainer information.
     * @return The updated trainer DTO.
     */
    TrainerDto updateTrainer(TrainerDto updatedTrainerDto);

    /**
     * Finds a trainer by the specified ID.
     *
     * @param id The ID of the trainer to find.
     * @return The trainer DTO corresponding to the ID.
     */
    TrainerDto findTrainerById(Long id);

    /**
     * Finds a trainer by the specified email.
     *
     * @param email The email of the trainer to find.
     * @return The trainer DTO corresponding to the email.
     */
    TrainerDto findTrainerByEmail(String email);

    /**
     * Retrieves a paginated list of all trainers.
     *
     * @param pageable The pageable information for pagination and sorting.
     * @return A page of trainer DTOs.
     */
    Page<TrainerDto> findAllTrainers(Pageable pageable);

    /**
     * Retrieves a paginated list of all trainer profiles.
     *
     * @param pageable The pageable information for pagination and sorting.
     * @return A page of trainer profiles.
     */
    Page<TrainerProfile> findAllTrainerProfiles(Pageable pageable);

    /**
     * Counts the total number of trainers.
     *
     * @return The count of trainers.
     */
    Long countTrainers();


    /**
     * Deletes all trainers.
     */
    void deleteAllTrainers();

}
