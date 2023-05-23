package com.shevelyanchik.fitnessclub.orderservice.service;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.ScheduleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface ScheduleService {
    /**
     * Creates a new schedule with the provided schedule data.
     *
     * @param scheduleDto The ScheduleDto object containing the schedule data.
     * @return The ScheduleDto object representing the created schedule.
     */
    ScheduleDto createSchedule(ScheduleDto scheduleDto);

    /**
     * Updates the available spots of a schedule identified by its ID.
     *
     * @param id             The ID of the schedule to update.
     * @param availableSpots The new available spots value for the schedule.
     */
    void updateScheduleAvailableSpotsById(Long id, Long availableSpots);

    /**
     * Finds a schedule by its ID.
     *
     * @param id The ID of the schedule to find.
     * @return The ScheduleDto object representing the found schedule.
     */
    ScheduleDto findScheduleById(Long id);

    /**
     * Finds a schedule by trainer ID and training start date and time.
     *
     * @param trainerId             The ID of the trainer associated with the schedule.
     * @param trainingStartDateTime The start date and time of the training associated with the schedule.
     * @return The ScheduleDto object representing the found schedule.
     */
    ScheduleDto findScheduleByTrainerIdAndTrainingStartDateTime(Long trainerId, LocalDateTime trainingStartDateTime);

    /**
     * Retrieves all schedules in a pageable format.
     *
     * @param pageable The pageable information for pagination and sorting.
     * @return A Page object containing the ScheduleDto objects for the retrieved schedules.
     */
    Page<ScheduleDto> findAllSchedules(Pageable pageable);

    /**
     * Retrieves all schedules associated with a specific trainer ID in a pageable format.
     *
     * @param pageable  The pageable information for pagination and sorting.
     * @param trainerId The ID of the trainer to retrieve schedules for.
     * @return A Page object containing the ScheduleDto objects for the retrieved schedules.
     */
    Page<ScheduleDto> findAllSchedulesByTrainerId(Pageable pageable, Long trainerId);

    /**
     * Counts the total number of schedules.
     *
     * @return The count of all schedules.
     */
    Long countSchedules();

    /**
     * Counts the total number of schedules associated with a specific trainer ID.
     *
     * @param trainerId The ID of the trainer to count schedules for.
     * @return The count of schedules for the specified trainer.
     */
    Long countSchedulesByTrainerId(Long trainerId);

    /**
     * Deletes all schedules.
     */
    void deleteAllSchedules();

}
