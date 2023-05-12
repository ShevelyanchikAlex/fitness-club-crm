package com.shevelyanchik.fitnessclub.userservice.integration.controller;

import com.shevelyanchik.fitnessclub.userservice.constant.Role;
import com.shevelyanchik.fitnessclub.userservice.constant.Status;
import com.shevelyanchik.fitnessclub.userservice.model.dto.TrainerDto;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserDto;
import com.shevelyanchik.fitnessclub.userservice.service.TrainerService;
import com.shevelyanchik.fitnessclub.userservice.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithMockUser(authorities = {"ADMIN_PERMISSION"})
@ActiveProfiles("test")
class TrainerControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private TrainerService trainerService;


    private final UserDto EXPECTED_USER_DTO = new UserDto(
            1L, "Name", "Surname", "passUser1",
            "test@gmail.com", "+375443321233", Role.USER, Status.ACTIVE);
    private final String TRAINER_API_ENDPOINT = "/api/v1/user-service/trainers";

    @BeforeEach
    public void setup() {
        testRestTemplate = testRestTemplate.withBasicAuth("", "");
    }

    @AfterEach
    void deleteTrainers() {
        trainerService.deleteAllTrainers();
    }

    @Test
    void testCreateTrainer() {
        //given
        TrainerDto expectedTrainerDto = prepareTrainerDto();
        testRestTemplate = new TestRestTemplate("user", "password");
        //when
        ResponseEntity<TrainerDto> response = testRestTemplate.postForEntity(TRAINER_API_ENDPOINT + "/create", expectedTrainerDto, TrainerDto.class);
        //then
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(expectedTrainerDto.getCategory(), response.getBody().getCategory());
    }

    @Test
    void testFindTrainerById() {
        //given
        TrainerDto expectedTrainerDto = prepareTrainerDto();
        //when
        ResponseEntity<TrainerDto> responseWithSavedTrainer = testRestTemplate.postForEntity(TRAINER_API_ENDPOINT + "/create", expectedTrainerDto, TrainerDto.class);
        ResponseEntity<TrainerDto> response = testRestTemplate.getForEntity(TRAINER_API_ENDPOINT + "/{id}", TrainerDto.class, Objects.requireNonNull(responseWithSavedTrainer.getBody()).getId());
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(expectedTrainerDto.getCategory(), response.getBody().getCategory());
    }

    @Test
    void testCountTrainers() {
        //given
        TrainerDto expectedTrainerDto = prepareTrainerDto();
        long expectedTrainersCount = 1L;
        //when
        testRestTemplate.postForEntity(TRAINER_API_ENDPOINT + "/create", expectedTrainerDto, TrainerDto.class);
        ResponseEntity<Long> response = testRestTemplate.getForEntity(TRAINER_API_ENDPOINT + "/count", Long.class);
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(expectedTrainersCount, response.getBody());
    }

    private TrainerDto prepareTrainerDto() {
        UserDto savedUser = userService.createUser(EXPECTED_USER_DTO);
        return new TrainerDto(1L, "Higher", "Box", savedUser);
    }

}
