package com.shevelyanchik.fitnessclub.orderservice.service.impl;

import com.shevelyanchik.fitnessclub.orderservice.exception.EntityNotFoundException;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.ScheduleDto;
import com.shevelyanchik.fitnessclub.orderservice.model.entity.Schedule;
import com.shevelyanchik.fitnessclub.orderservice.model.mapper.ScheduleMapper;
import com.shevelyanchik.fitnessclub.orderservice.persistence.ScheduleRepository;
import com.shevelyanchik.fitnessclub.orderservice.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    @Override
    @Transactional
    public ScheduleDto createSchedule(ScheduleDto scheduleDto) {
        Schedule schedule = scheduleMapper.toEntity(scheduleDto);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return scheduleMapper.toDto(savedSchedule);
    }

    @Override
    @Transactional
    public ScheduleDto updateSchedule(ScheduleDto updatedScheduleDto) {
        ScheduleDto actualScheduleDto = findScheduleById(updatedScheduleDto.getId());
        actualScheduleDto.setServiceDto(updatedScheduleDto.getServiceDto());
        actualScheduleDto.setServiceType(updatedScheduleDto.getServiceType());
        actualScheduleDto.setAvailableSpots(updatedScheduleDto.getAvailableSpots());
        actualScheduleDto.setTrainerId(updatedScheduleDto.getTrainerId());
        actualScheduleDto.setTrainingStartDateTime(updatedScheduleDto.getTrainingStartDateTime());
        Schedule preUpdatedSchedule = scheduleMapper.toEntity(actualScheduleDto);
        Schedule updatedSchedule = scheduleRepository.save(preUpdatedSchedule);
        return scheduleMapper.toDto(updatedSchedule);
    }

    @Override
    @Transactional
    public void updateScheduleAvailableSpotsById(Long id, Long availableSpots) {
        try {
            scheduleRepository.updateScheduleAvailableSpotsById(id, availableSpots);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException("Schedule not found with id: " + id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleDto findScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .map(scheduleMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleDto findScheduleByTrainerIdAndTrainingStartDateTime(
            Long trainerId, LocalDateTime trainingStartDateTime) {
        return scheduleRepository.findScheduleByTrainerIdAndTrainingStartDateTime(
                        trainerId, trainingStartDateTime)
                .map(scheduleMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found with trainerId: " + trainerId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ScheduleDto> findAllSchedules(Pageable pageable) {
        List<ScheduleDto> scheduleDtoList = scheduleRepository.findAll(pageable).stream()
                .map(scheduleMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(scheduleDtoList, pageable, scheduleRepository.count());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ScheduleDto> findAllSchedulesByTrainerId(Pageable pageable, Long trainerId) {
        List<ScheduleDto> scheduleDtoList = scheduleRepository.findAllSchedulesByTrainerId(pageable, trainerId).stream()
                .map(scheduleMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(scheduleDtoList, pageable, scheduleRepository.count());
    }

    @Override
    @Transactional(readOnly = true)
    public Long countSchedules() {
        return scheduleRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Long countSchedulesByTrainerId(Long trainerId) {
        return scheduleRepository.countSchedulesByTrainerId(trainerId);
    }

    @Override
    @Transactional
    public void deleteAllSchedules() {
        scheduleRepository.deleteAll();
    }

}
