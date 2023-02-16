package com.shevelyanchik.fitnessclub.userservice.service.impl;

import com.shevelyanchik.fitnessclub.userservice.model.domain.Trainer;
import com.shevelyanchik.fitnessclub.userservice.model.dto.TrainerDto;
import com.shevelyanchik.fitnessclub.userservice.model.mapper.TrainerMapper;
import com.shevelyanchik.fitnessclub.userservice.persistence.TrainerRepository;
import com.shevelyanchik.fitnessclub.userservice.service.TrainerService;
import com.shevelyanchik.fitnessclub.userservice.service.exception.ServiceException;
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
    public TrainerDto createTrainer(TrainerDto trainerDto) {
        validateTrainer(trainerDto);
        Trainer trainer = trainerMapper.toEntity(trainerDto);
        Trainer savedTrainer = trainerRepository.save(trainer);
        return trainerMapper.toDto(savedTrainer);
    }

    @Override
    public TrainerDto findTrainerById(Long id) {
        return trainerRepository
                .findById(id)
                .map(trainerMapper::toDto)
                .orElseThrow(() -> new ServiceException(TRAINER_NOT_FOUND));
    }

    @Override
    public Page<TrainerDto> findAllTrainers(Pageable pageable) {
        List<TrainerDto> trainerDtoList = trainerRepository
                .findAll(pageable)
                .stream()
                .map(trainerMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(trainerDtoList, pageable, trainerRepository.count());
    }

    @Override
    public Long countTrainers() {
        return trainerRepository.count();
    }

    private void validateTrainer(TrainerDto trainerDto) {
        if (Objects.isNull(trainerDto.getUser())) {
            throw new ServiceException(TRAINER_VALIDATE_ERROR);
        }
    }
}
