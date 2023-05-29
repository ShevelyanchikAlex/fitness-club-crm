package com.shevelyanchik.fitnessclub.orderservice.controller;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.FitnessClubInfoDto;
import com.shevelyanchik.fitnessclub.orderservice.service.FitnessClubInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order-service/fitness-club-info")
public class FitnessClubInfoController {

    private final FitnessClubInfoService fitnessClubInfoService;

    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    @PostMapping
    public ResponseEntity<FitnessClubInfoDto> createFitnessClubInfo(@Valid @RequestBody FitnessClubInfoDto fitnessClubInfoDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fitnessClubInfoService.createFitnessClubInfo(fitnessClubInfoDto));
    }

    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    @GetMapping
    public ResponseEntity<List<FitnessClubInfoDto>> findAllFitnessClubInfos(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<FitnessClubInfoDto> fitnessClubInfoDtoPage = fitnessClubInfoService.findAllFitnessClubInfos(PageRequest.of(page, size));
        return ResponseEntity.ok(fitnessClubInfoDtoPage.getContent());
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    @Cacheable(value = "fitness-club-info", key = "#id")
    public ResponseEntity<FitnessClubInfoDto> findFitnessClubInfoById(@PathVariable Long id) {
        return ResponseEntity.ok(fitnessClubInfoService.findFitnessClubInfoById(id));
    }

}
