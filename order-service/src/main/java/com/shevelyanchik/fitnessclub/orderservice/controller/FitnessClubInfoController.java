package com.shevelyanchik.fitnessclub.orderservice.controller;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.FitnessClubInfoDto;
import com.shevelyanchik.fitnessclub.orderservice.service.FitnessClubInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order-service/fitness-club-info")
public class FitnessClubInfoController {
    private final FitnessClubInfoService fitnessClubInfoService;

    @PostMapping
    public FitnessClubInfoDto createFitnessClubInfo(@Valid @RequestBody FitnessClubInfoDto fitnessClubInfoDto) {
        return fitnessClubInfoService.createFitnessClubInfo(fitnessClubInfoDto);
    }

    @GetMapping
    public List<FitnessClubInfoDto> findAllFitnessClubInfos(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<FitnessClubInfoDto> fitnessClubInfoDtoPage = fitnessClubInfoService.findAllFitnessClubInfos(PageRequest.of(page, size));
        return fitnessClubInfoDtoPage.getContent();
    }

    @GetMapping("/{id}")
    @Cacheable(value = "fitness-club-info", key = "#id")
    public FitnessClubInfoDto findFitnessClubInfoById(@PathVariable Long id) {
        return fitnessClubInfoService.findFitnessClubInfoById(id);
    }
}
