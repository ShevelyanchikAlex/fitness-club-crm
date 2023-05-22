package com.shevelyanchik.fitnessclub.userservice.controller;

import com.shevelyanchik.fitnessclub.userservice.model.dto.TrainerDto;
import com.shevelyanchik.fitnessclub.userservice.model.dto.TrainerProfile;
import com.shevelyanchik.fitnessclub.userservice.service.TrainerService;
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
@RequestMapping("/api/v1/user-service/trainers")
public class TrainerController {
    private final TrainerService trainerService;

    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public TrainerDto createTrainer(@Valid @RequestBody TrainerDto trainerDto) {
        return trainerService.createTrainer(trainerDto);
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public List<TrainerDto> findAllTrainers(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<TrainerDto> trainerPage = trainerService.findAllTrainers(PageRequest.of(page, size));
        return trainerPage.getContent();
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/trainer-profiles")
    public List<TrainerProfile> findAllTrainerProfiles(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                       @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<TrainerProfile> trainerPage = trainerService.findAllTrainerProfiles(PageRequest.of(page, size));
        return trainerPage.getContent();
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public TrainerDto findTrainerById(@PathVariable Long id) {
        return trainerService.findTrainerById(id);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/email/{email}")
    public TrainerDto findTrainerByEmail(@PathVariable String email) {
        return trainerService.findTrainerByEmail(email);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/count")
    public Long countTrainers() {
        return trainerService.countTrainers();
    }
}
