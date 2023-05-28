package com.shevelyanchik.fitnessclub.orderservice.controller;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.ScheduleDto;
import com.shevelyanchik.fitnessclub.orderservice.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order-service/schedules")
public class ScheduleController {

    private static final String TRAINING_START_DATETIME_FIELD = "trainingStartDateTime";

    private final ScheduleService scheduleService;


    @PreAuthorize("hasAuthority('TRAINER_PERMISSION')")
    @PostMapping("/create")
    public ResponseEntity<ScheduleDto> createSchedule(@Valid @RequestBody ScheduleDto scheduleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(scheduleDto));
    }

    @PreAuthorize("hasAuthority('TRAINER_PERMISSION')")
    @PatchMapping("/update")
    public ResponseEntity<ScheduleDto> updateSchedule(@Valid @RequestBody ScheduleDto updatedSchedule) {
        return ResponseEntity.ok(scheduleService.updateSchedule(updatedSchedule));
    }

    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    @GetMapping("/all")
    public ResponseEntity<List<ScheduleDto>> findAllSchedules(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                              @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(TRAINING_START_DATETIME_FIELD).descending());
        Page<ScheduleDto> scheduleDtoPage = scheduleService.findAllSchedules(pageable);
        return ResponseEntity.ok(scheduleDtoPage.getContent());
    }

    @PreAuthorize("hasAuthority('TRAINER_PERMISSION')")
    @GetMapping("/trainer/{trainerId}")
    public ResponseEntity<List<ScheduleDto>> findAllSchedulesByTrainerId(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                         @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                                         @PathVariable Long trainerId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(TRAINING_START_DATETIME_FIELD).descending());
        Page<ScheduleDto> scheduleDtoPage = scheduleService.findAllSchedulesByTrainerId(pageable, trainerId);
        return ResponseEntity.ok(scheduleDtoPage.getContent());
    }

    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDto> findScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.findScheduleById(id));
    }

    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    @GetMapping("/count")
    public ResponseEntity<Long> countSchedules() {
        return ResponseEntity.ok(scheduleService.countSchedules());
    }

    @PreAuthorize("hasAuthority('TRAINER_PERMISSION')")
    @GetMapping("/count/trainer/{trainerId}")
    public ResponseEntity<Long> countSchedulesByTrainerId(@PathVariable Long trainerId) {
        return ResponseEntity.ok(scheduleService.countSchedulesByTrainerId(trainerId));
    }

}
