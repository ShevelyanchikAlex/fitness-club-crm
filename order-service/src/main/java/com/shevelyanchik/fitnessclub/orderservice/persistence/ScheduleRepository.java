package com.shevelyanchik.fitnessclub.orderservice.persistence;

import com.shevelyanchik.fitnessclub.orderservice.model.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Optional<Schedule> findScheduleByTrainerIdAndTrainingStartDateTime(Long trainerId, LocalDateTime trainingStartDateTime);

    Page<Schedule> findAllByTrainerId(Pageable pageable, Long trainerId);

    @Modifying
    @Query("UPDATE Schedule s SET s.availableSpots = :availableSpots WHERE s.id = :id")
    void updateScheduleAvailableSpotsById(Long id, Long availableSpots);

    Long countSchedulesByTrainerId(Long trainerId);

}
