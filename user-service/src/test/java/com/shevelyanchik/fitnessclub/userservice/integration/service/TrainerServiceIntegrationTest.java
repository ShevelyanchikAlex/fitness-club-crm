package com.shevelyanchik.fitnessclub.userservice.integration.service;

import com.shevelyanchik.fitnessclub.userservice.constant.Role;
import com.shevelyanchik.fitnessclub.userservice.constant.Status;
import com.shevelyanchik.fitnessclub.userservice.model.dto.TrainerDto;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserDto;
import com.shevelyanchik.fitnessclub.userservice.service.TrainerService;
import com.shevelyanchik.fitnessclub.userservice.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class TrainerServiceIntegrationTest {

    private static final UserDto EXPECTED_USER_DTO = new UserDto(
            1L, "Name", "Surname", "passUser1",
            "test@gmail.com", "+375443321233", "", Role.USER, Status.ACTIVE);


    @Autowired
    private UserService userService;

    @Autowired
    private TrainerService trainerService;


    @AfterEach
    void deleteTrainers() {
        trainerService.deleteAllTrainers();
    }

    @Test
    void testCreateTrainer() {
        //given
        TrainerDto expectedTrainerDto = prepareTrainerDto();
        //when
        TrainerDto actualTrainerDto = trainerService.createTrainer(expectedTrainerDto);
        //then
        Assertions.assertEquals(expectedTrainerDto.getCategory(), actualTrainerDto.getCategory());
    }

    @Test
    void testFindTrainerById() {
        //given
        TrainerDto expectedTrainerDto = prepareTrainerDto();
        //when
        TrainerDto savedTrainerDto = trainerService.createTrainer(expectedTrainerDto);
        TrainerDto actualTrainerDto = trainerService.findTrainerById(savedTrainerDto.getId());
        //then
        Assertions.assertNotNull(actualTrainerDto);
    }


    @Test
    void testFindAllTrainers() {
        //given
        TrainerDto expectedTrainerDto = prepareTrainerDto();
        List<TrainerDto> expectedTrainerList = new ArrayList<>(List.of(expectedTrainerDto));
        Pageable expectedPageable = PageRequest.of(0, 10);
        Page<TrainerDto> expectedPage = new PageImpl<>(expectedTrainerList, expectedPageable, 1L);
        //when
        trainerService.createTrainer(expectedTrainerDto);
        Page<TrainerDto> actualTrainerDtoPage = trainerService.findAllTrainers(expectedPageable);
        //then
        Assertions.assertEquals(expectedPage.getTotalElements(), actualTrainerDtoPage.getTotalElements());
    }

    @Test
    void testCountTrainers() {
        //given
        TrainerDto expectedTrainerDto = prepareTrainerDto();
        long expectedTrainersCount = 1L;
        //when
        trainerService.createTrainer(expectedTrainerDto);
        long actualTrainersCount = trainerService.countTrainers();
        //then
        Assertions.assertEquals(expectedTrainersCount, actualTrainersCount);
    }


    private TrainerDto prepareTrainerDto() {
        UserDto savedUser = userService.createUser(EXPECTED_USER_DTO);
        return new TrainerDto(1L, "Higher", "Box", savedUser);
    }

}
