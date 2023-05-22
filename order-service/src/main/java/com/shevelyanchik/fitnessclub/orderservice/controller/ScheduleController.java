package com.shevelyanchik.fitnessclub.orderservice.controller;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.ScheduleDto;
import com.shevelyanchik.fitnessclub.orderservice.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order-service/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;


    @PreAuthorize("hasAuthority('TRAINER_PERMISSION')")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduleDto createSchedule(@Valid @RequestBody ScheduleDto scheduleDto) {
        return scheduleService.createSchedule(scheduleDto);
    }

    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    @GetMapping
    public List<ScheduleDto> findAllSchedules(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                              @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<ScheduleDto> scheduleDtoPage = scheduleService.findAllSchedules(PageRequest.of(page, size));
        return scheduleDtoPage.getContent();
    }

    @PreAuthorize("hasAuthority('TRAINER_PERMISSION')")
    @GetMapping("/trainer/{trainerId}")
    public List<ScheduleDto> findAllSchedulesByTrainerId(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                         @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                         @PathVariable Long trainerId) {
        Page<ScheduleDto> scheduleDtoPage = scheduleService.findAllSchedulesByTrainerId(PageRequest.of(page, size), trainerId);
        return scheduleDtoPage.getContent();
    }

    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    @GetMapping("/{id}")
    public ScheduleDto findScheduleById(@PathVariable Long id) {
        return scheduleService.findScheduleById(id);
    }

    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    @GetMapping("/count")
    public Long countSchedules() {
        return scheduleService.countSchedules();
    }

    @PreAuthorize("hasAuthority('TRAINER_PERMISSION')")
    @GetMapping("/count/trainer/{trainerId}")
    public Long countSchedulesByTrainerId(@PathVariable Long trainerId) {
        return scheduleService.countSchedulesByTrainerId(trainerId);
    }

}
