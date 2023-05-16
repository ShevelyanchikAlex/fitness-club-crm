package com.shevelyanchik.fitnessclub.userservice.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shevelyanchik.fitnessclub.userservice.constant.Role;
import com.shevelyanchik.fitnessclub.userservice.constant.Status;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserDto;
import com.shevelyanchik.fitnessclub.userservice.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserService userService;


    private final UserDto EXPECTED_USER_DTO = new UserDto(
            1L, "Name", "Surname", "passUser1",
            "test@gmail.com", "+375443321233", Role.USER, Status.ACTIVE);
    private final String EXPECTED_EMAIL = "test@gmail.com";
    private final String USER_API_ENDPOINT = "/api/v1/user-service/users";


    @AfterEach
    void deleteUsers() {
        userService.deleteAllUsers();
    }

    @Test
    void testCreateUser() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(USER_API_ENDPOINT + "/create")
                                .content(mapper.writeValueAsString(EXPECTED_USER_DTO))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(EXPECTED_USER_DTO.getEmail()));
    }

    @Test
    void testFindUserById() throws Exception {
        UserDto savedUser = initUser();

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(USER_API_ENDPOINT + "/{id}", savedUser.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(EXPECTED_USER_DTO.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(EXPECTED_USER_DTO.getName()));
    }

    @Test
    void testFindUserByEmail() throws Exception {
        initUser();

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(USER_API_ENDPOINT + "/email/{email}", EXPECTED_EMAIL)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(EXPECTED_EMAIL))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(EXPECTED_USER_DTO.getName()));
    }

    @Test
    void testExistsUserByEmail() throws Exception {
        initUser();

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(USER_API_ENDPOINT + "/exists-by-email/{email}", EXPECTED_EMAIL)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testCountUsers() throws Exception {
        String expectedUsersCount = "1";
        initUser();

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(USER_API_ENDPOINT + "/count")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedUsersCount));
    }

    private UserDto initUser() {
        return userService.createUser(EXPECTED_USER_DTO);
    }

}
