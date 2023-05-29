package com.shevelyanchik.fitnessclub.userservice.controller;

import com.shevelyanchik.fitnessclub.userservice.model.dto.TrainerDto;
import com.shevelyanchik.fitnessclub.userservice.model.dto.TrainerProfile;
import com.shevelyanchik.fitnessclub.userservice.service.TrainerService;
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
@RequestMapping("/api/v1/user-service/trainers")
public class TrainerController {

    private static final String CATEGORY_FIELD = "category";

    private final TrainerService trainerService;

    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    @PostMapping("/create")
    public ResponseEntity<TrainerDto> createTrainer(@Valid @RequestBody TrainerDto trainerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trainerService.createTrainer(trainerDto));
    }

    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    @PatchMapping("/update")
    public ResponseEntity<TrainerDto> updateTrainer(@Valid @RequestBody TrainerDto updatedTrainerDto) {
        return ResponseEntity.ok(trainerService.updateTrainer(updatedTrainerDto));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public ResponseEntity<List<TrainerDto>> findAllTrainers(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(CATEGORY_FIELD).descending());
        Page<TrainerDto> trainerPage = trainerService.findAllTrainers(pageable);
        return ResponseEntity.ok(trainerPage.getContent());
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/trainer-profile/all")
    public ResponseEntity<List<TrainerProfile>> findAllTrainerProfiles(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                       @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(CATEGORY_FIELD).descending());
        Page<TrainerProfile> trainerPage = trainerService.findAllTrainerProfiles(pageable);
        return ResponseEntity.ok(trainerPage.getContent());
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public ResponseEntity<TrainerDto> findTrainerById(@PathVariable Long id) {
        return ResponseEntity.ok(trainerService.findTrainerById(id));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/email/{email}")
    public ResponseEntity<TrainerDto> findTrainerByEmail(@PathVariable String email) {
        return ResponseEntity.ok(trainerService.findTrainerByEmail(email));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/count")
    public ResponseEntity<Long> countTrainers() {
        return ResponseEntity.ok(trainerService.countTrainers());
    }

}
