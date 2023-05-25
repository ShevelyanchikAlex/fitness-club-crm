package com.shevelyanchik.fitnessclub.userservice.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shevelyanchik.fitnessclub.userservice.constant.Role;
import com.shevelyanchik.fitnessclub.userservice.constant.Status;
import com.shevelyanchik.fitnessclub.userservice.model.dto.TrainerDto;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserDto;
import com.shevelyanchik.fitnessclub.userservice.service.TrainerService;
import com.shevelyanchik.fitnessclub.userservice.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TrainerControllerIntegrationTest {

    private static final UserDto EXPECTED_USER_DTO = new UserDto(
            1L, "Name", "Surname", "passUser1",
            "test@gmail.com", "+375443321233", "", Role.ADMIN, Status.ACTIVE);
    private static final String TRAINER_API_ENDPOINT = "/api/v1/user-service/trainers";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserService userService;

    @Autowired
    private TrainerService trainerService;


    @AfterEach
    void deleteTrainers() {
        trainerService.deleteAllTrainers();
    }

    @Test
    void testCreateTrainer() throws Exception {
        UserDto savedUser = userService.createUser(EXPECTED_USER_DTO);
        TrainerDto expectedTrainerDto = new TrainerDto(1L, "Higher", "Box", savedUser);
        HttpHeaders headers = initHeaders(savedUser);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(TRAINER_API_ENDPOINT + "/create")
                                .content(mapper.writeValueAsString(expectedTrainerDto))
                                .headers(headers)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.category")
                        .value(expectedTrainerDto.getCategory()));
    }

    @Test
    void testFindTrainerById() throws Exception {
        TrainerDto expectedTrainerDto = initTrainer();

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(TRAINER_API_ENDPOINT + "/{id}", expectedTrainerDto.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.category")
                        .value(expectedTrainerDto.getCategory()));
    }

    @Test
    void testCountTrainers() throws Exception {
        String expectedUsersCount = "1";
        initTrainer();

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(TRAINER_API_ENDPOINT + "/count")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedUsersCount));
    }


    private TrainerDto initTrainer() {
        UserDto savedUser = userService.createUser(EXPECTED_USER_DTO);
        TrainerDto trainerDto = new TrainerDto(1L, "Higher", "Box", savedUser);
        return trainerService.createTrainer(trainerDto);
    }

    private HttpHeaders initHeaders(UserDto userDto) {
        HttpHeaders headers = new HttpHeaders();
        String authorities =
                userDto
                        .getRole()
                        .getPermissions()
                        .stream()
                        .map(Enum::name)
                        .collect(Collectors.joining(","));

        headers.add("username", userDto.getEmail());
        headers.add("authorities", authorities);
        return headers;
    }

}
