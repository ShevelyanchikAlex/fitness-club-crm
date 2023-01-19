package com.shevelyanchik.fitnessclub.web.controller;

import com.shevelyanchik.fitnessclub.model.dto.FitnessClubInfoDto;
import com.shevelyanchik.fitnessclub.service.FitnessClubInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fitness-club-info")
public class FitnessClubInfoController {
    private final FitnessClubInfoService fitnessClubInfoService;


    @PostMapping
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public FitnessClubInfoDto save(@RequestBody FitnessClubInfoDto fitnessClubInfoDto) {
        return fitnessClubInfoService.save(fitnessClubInfoDto);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public List<FitnessClubInfoDto> findAll(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<FitnessClubInfoDto> fitnessClubInfoDtoPage = fitnessClubInfoService.findAll(PageRequest.of(pageIndex, size));
        return new ArrayList<>(fitnessClubInfoDtoPage.getContent());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public FitnessClubInfoDto findById(@PathVariable Long id) {
        return fitnessClubInfoService.findById(id);
    }
}
