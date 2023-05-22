package com.shevelyanchik.fitnessclub.orderservice.service;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.ScheduleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface ScheduleService {

    ScheduleDto createSchedule(ScheduleDto scheduleDto);

    void updateScheduleAvailableSpotsById(Long id, Long availableSpots);

    ScheduleDto findScheduleById(Long id);

    ScheduleDto findScheduleByTrainerIdAndTrainingStartDateTime(Long trainerId, LocalDateTime trainingStartDateTime);

    Page<ScheduleDto> findAllSchedules(Pageable pageable);

    Page<ScheduleDto> findAllSchedulesByTrainerId(Pageable pageable, Long trainerId);

    void deleteAllSchedules();

    Long countSchedules();

    Long countSchedulesByTrainerId(Long trainerId);

}
