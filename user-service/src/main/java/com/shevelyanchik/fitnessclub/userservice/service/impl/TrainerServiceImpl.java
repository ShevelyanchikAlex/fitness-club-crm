package com.shevelyanchik.fitnessclub.userservice.service.impl;

import com.shevelyanchik.fitnessclub.userservice.constant.Role;
import com.shevelyanchik.fitnessclub.userservice.exception.EntityNotFoundException;
import com.shevelyanchik.fitnessclub.userservice.model.dto.TrainerDto;
import com.shevelyanchik.fitnessclub.userservice.model.dto.TrainerProfile;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserProfile;
import com.shevelyanchik.fitnessclub.userservice.model.entity.Trainer;
import com.shevelyanchik.fitnessclub.userservice.model.mapper.TrainerMapper;
import com.shevelyanchik.fitnessclub.userservice.persistence.TrainerRepository;
import com.shevelyanchik.fitnessclub.userservice.service.TrainerService;
import com.shevelyanchik.fitnessclub.userservice.service.UserService;
import com.shevelyanchik.fitnessclub.userservice.util.ProfileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;
    private final UserService userService;


    @Override
    @Transactional
    public TrainerDto createTrainer(TrainerDto trainerDto) {
        Trainer trainer = trainerMapper.toEntity(trainerDto);
        Trainer savedTrainer = trainerRepository.save(trainer);
        userService.updateUserRoleById(savedTrainer.getUser().getId(), Role.TRAINER.name());
        return trainerMapper.toDto(savedTrainer);
    }

    @Override
    @Transactional
    public TrainerDto updateTrainer(TrainerDto updatedTrainerDto) {
        TrainerDto actualTrainerDto = findTrainerById(updatedTrainerDto.getId());
        actualTrainerDto.setCategory(updatedTrainerDto.getCategory());
        actualTrainerDto.setKindOfSport(updatedTrainerDto.getKindOfSport());
        Trainer preUpdatedTrainer = trainerMapper.toEntity(actualTrainerDto);
        Trainer updatedTrainer = trainerRepository.save(preUpdatedTrainer);
        return trainerMapper.toDto(updatedTrainer);
    }

    @Override
    @Transactional(readOnly = true)
    public TrainerDto findTrainerById(Long id) {
        return trainerRepository.findById(id)
                .map(trainerMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public TrainerDto findTrainerByEmail(String email) {
        return trainerRepository.findTrainerByUserEmail(email)
                .map(trainerMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found with email: " + email));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TrainerDto> findAllTrainers(Pageable pageable) {
        List<TrainerDto> trainerDtoList = trainerRepository.findAll(pageable).stream()
                .map(trainerMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(trainerDtoList, pageable, trainerRepository.count());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TrainerProfile> findAllTrainerProfiles(Pageable pageable) {
        List<TrainerDto> trainerDtoList = trainerRepository.findAll(pageable).stream()
                .map(trainerMapper::toDto)
                .collect(Collectors.toList());

        List<TrainerProfile> trainerProfileList = new ArrayList<>();
        trainerDtoList.forEach(trainerDto -> {
            UserProfile userProfile = userService.findUserProfileByEmail(
                    trainerDto.getUserDto().getEmail());
            trainerProfileList.add(ProfileUtils.buildTrainerProfile(trainerDto, userProfile));
        });
        return new PageImpl<>(trainerProfileList, pageable, trainerProfileList.size());
    }

    @Override
    @Transactional(readOnly = true)
    public Long countTrainers() {
        return trainerRepository.count();
    }

    @Override
    @Transactional
    public void deleteAllTrainers() {
        trainerRepository.deleteAll();
    }

}
