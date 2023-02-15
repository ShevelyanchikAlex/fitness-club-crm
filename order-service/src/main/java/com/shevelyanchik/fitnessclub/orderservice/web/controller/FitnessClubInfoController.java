package com.shevelyanchik.fitnessclub.orderservice.web.controller;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.FitnessClubInfoDto;
import com.shevelyanchik.fitnessclub.orderservice.service.FitnessClubInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fitness-club-info")
public class FitnessClubInfoController {
    private final FitnessClubInfoService fitnessClubInfoService;

    @PostMapping
    public FitnessClubInfoDto save(@RequestBody FitnessClubInfoDto fitnessClubInfoDto) {
        return fitnessClubInfoService.createFitnessClubInfo(fitnessClubInfoDto);
    }

    @GetMapping
    public List<FitnessClubInfoDto> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<FitnessClubInfoDto> fitnessClubInfoDtoPage = fitnessClubInfoService.findAllFitnessClubInfos(PageRequest.of(page, size));
        return new ArrayList<>(fitnessClubInfoDtoPage.getContent());
    }

    @GetMapping("/{id}")
    public FitnessClubInfoDto findById(@PathVariable Long id) {
        return fitnessClubInfoService.findFitnessClubInfoById(id);
    }
}
