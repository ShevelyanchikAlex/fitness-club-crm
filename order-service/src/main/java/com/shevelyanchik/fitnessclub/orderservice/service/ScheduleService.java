package com.shevelyanchik.fitnessclub.orderservice.service;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.ScheduleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScheduleService {

    ScheduleDto createSchedule(ScheduleDto scheduleDto);

    ScheduleDto findScheduleById(Long id);

    Page<ScheduleDto> findAllSchedules(Pageable pageable);

    void deleteAllSchedules();

    Long countSchedules();

}
