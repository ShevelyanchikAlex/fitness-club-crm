package com.shevelyanchik.fitnessclub.orderservice.service;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.FitnessClubInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FitnessClubInfoService {
    FitnessClubInfoDto save(FitnessClubInfoDto fitnessClubInfoDto);

    FitnessClubInfoDto findById(Long id);

    Page<FitnessClubInfoDto> findAll(Pageable pageable);
}
