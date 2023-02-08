package com.shevelyanchik.fitnessclub.service.impl;

import com.shevelyanchik.fitnessclub.model.domain.Trainer;
import com.shevelyanchik.fitnessclub.model.dto.TrainerDto;
import com.shevelyanchik.fitnessclub.model.mapper.TrainerMapper;
import com.shevelyanchik.fitnessclub.persistence.TrainerRepository;
import com.shevelyanchik.fitnessclub.service.TrainerService;
import com.shevelyanchik.fitnessclub.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {
    private static final String TRAINER_NOT_FOUND = "trainer.not.found";
    private static final String TRAINER_VALIDATE_ERROR = "trainer.validate.error";

    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;

    @Override
    public TrainerDto save(TrainerDto trainerDto) {
        if (Objects.isNull(trainerDto.getUser())) {
            throw new ServiceException(TRAINER_VALIDATE_ERROR);
        }
        Trainer entity = trainerMapper.toEntity(trainerDto);
        Trainer savedTrainer = trainerRepository.save(entity);
        return trainerMapper.toDto(savedTrainer);
    }

    @Override
    public TrainerDto findById(Long id) {
        return trainerRepository
                .findById(id)
                .map(trainerMapper::toDto)
                .orElseThrow(() -> new ServiceException(TRAINER_NOT_FOUND));
    }

    @Override
    public Page<TrainerDto> findAll(Pageable pageable) {
        List<TrainerDto> trainerDtoList = trainerRepository
                .findAll()
                .stream()
                .map(trainerMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(trainerDtoList, pageable, trainerRepository.count());
    }

    @Override
    public Long getTrainersCount() {
        return trainerRepository.count();
    }
}
