package com.shevelyanchik.fitnessclub.web.controller;

import com.shevelyanchik.fitnessclub.model.dto.TrainerDto;
import com.shevelyanchik.fitnessclub.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/trainers")
public class TrainerController {
    private final TrainerService trainerService;

    @GetMapping
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public List<TrainerDto> findAll(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                    @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<TrainerDto> trainerPage = trainerService.findAll(PageRequest.of(pageIndex, size));
        return new ArrayList<>(trainerPage.getContent());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public TrainerDto findById(@PathVariable Long id) {
        return trainerService.findById(id);
    }

    @GetMapping("/count")
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public Long getTrainersCount() {
        return trainerService.getTrainersCount();
    }
}
