package com.shevelyanchik.fitnessclub.service;

import com.shevelyanchik.fitnessclub.model.dto.TrainerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TrainerService {
    TrainerDto save(TrainerDto trainerDto);

    TrainerDto findById(Long id);

    Page<TrainerDto> findAll(Pageable pageable);

    Long getTrainersCount();
}
