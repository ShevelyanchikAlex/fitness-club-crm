package com.shevelyanchik.fitnessclub.userservice.service;

import com.shevelyanchik.fitnessclub.userservice.constant.Role;
import com.shevelyanchik.fitnessclub.userservice.constant.Status;
import com.shevelyanchik.fitnessclub.userservice.exception.EntityNotFoundException;
import com.shevelyanchik.fitnessclub.userservice.model.dto.TrainerDto;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserDto;
import com.shevelyanchik.fitnessclub.userservice.model.entity.Trainer;
import com.shevelyanchik.fitnessclub.userservice.model.mapper.TrainerMapper;
import com.shevelyanchik.fitnessclub.userservice.model.mapper.TrainerMapperImpl;
import com.shevelyanchik.fitnessclub.userservice.model.mapper.UserMapper;
import com.shevelyanchik.fitnessclub.userservice.model.mapper.UserMapperImpl;
import com.shevelyanchik.fitnessclub.userservice.persistence.TrainerRepository;
import com.shevelyanchik.fitnessclub.userservice.service.impl.TrainerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TrainerServiceTest {

    private static final UserDto EXPECTED_USER_DTO = new UserDto(
            1L, "Name", "Surname", "passUser1",
            "test@gmail.com", "+375443321233", Role.USER, Status.ACTIVE);

    private static final TrainerDto EXPECTED_TRAINER_DTO = new TrainerDto(
            1L, "Higher", "Box", EXPECTED_USER_DTO);

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @Mock
    private TrainerRepository trainerRepository;

    @Spy
    @InjectMocks
    private final TrainerMapper trainerMapper = new TrainerMapperImpl();

    @Spy
    private final UserMapper userMapper = new UserMapperImpl();


    @Test
    void testCreateTrainer() {
        //given
        Trainer expectedTrainer = trainerMapper.toEntity(EXPECTED_TRAINER_DTO);
        BDDMockito.given(trainerRepository.save(any())).willReturn(expectedTrainer);
        //when
        TrainerDto actualTrainerDto = trainerService.createTrainer(EXPECTED_TRAINER_DTO);
        //then
        BDDMockito.then(trainerRepository).should().save(any());
        Assertions.assertEquals(EXPECTED_TRAINER_DTO, actualTrainerDto);
    }

    @Test
    void testFindTrainerById() {
        //given
        Trainer expectedTrainer = trainerMapper.toEntity(EXPECTED_TRAINER_DTO);
        BDDMockito.given(trainerRepository.findById(any())).willReturn(Optional.of(expectedTrainer));
        //when
        TrainerDto actualTrainerDto = trainerService.findTrainerById(1L);
        //then
        BDDMockito.then(trainerRepository).should().findById(any());
        Assertions.assertEquals(EXPECTED_TRAINER_DTO, actualTrainerDto);
    }

    @Test
    void testFindTrainerByIdWithUnExistingId() {
        //given
        long expectedId = 1L;
        BDDMockito.given(trainerRepository.findById(any())).willThrow(EntityNotFoundException.class);
        //then
        Assertions.assertThrows(EntityNotFoundException.class, () -> trainerService.findTrainerById(expectedId));
        BDDMockito.then(trainerRepository).should().findById(expectedId);
    }

    @Test
    void testFindAllTrainers() {
        //given
        Trainer expectedTrainer = trainerMapper.toEntity(EXPECTED_TRAINER_DTO);
        List<Trainer> expectedTrainerList = new ArrayList<>(List.of(expectedTrainer));
        long expectedTrainersCount = 1L;
        Pageable expectedPageable = PageRequest.of(0, 10);
        Page<Trainer> expectedPage = new PageImpl<>(expectedTrainerList, expectedPageable, 1L);

        BDDMockito.given(trainerRepository.count()).willReturn(expectedTrainersCount);
        BDDMockito.given(trainerRepository.findAll(expectedPageable)).willReturn(expectedPage);
        //when
        Page<TrainerDto> actualTrainerDtoPage = trainerService.findAllTrainers(expectedPageable);
        //then
        BDDMockito.then(trainerRepository).should().count();
        BDDMockito.then(trainerRepository).should().findAll(expectedPageable);
        Assertions.assertEquals(expectedTrainerList.size(), actualTrainerDtoPage.getTotalElements());
    }

    @Test
    void testCountTrainers() {
        //given
        long expectedTrainersCount = 1L;
        BDDMockito.given(trainerRepository.count()).willReturn(expectedTrainersCount);
        //when
        long actualTrainersCount = trainerService.countTrainers();
        //then
        BDDMockito.then(trainerRepository).should().count();
        Assertions.assertEquals(expectedTrainersCount, actualTrainersCount);
    }
}