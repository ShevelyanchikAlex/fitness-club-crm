package com.shevelyanchik.fitnessclub.userservice.integration.controller;

import com.shevelyanchik.fitnessclub.userservice.constant.Role;
import com.shevelyanchik.fitnessclub.userservice.constant.Status;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserDto;
import com.shevelyanchik.fitnessclub.userservice.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

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
    void testCreateUser() {
        //when
        ResponseEntity<UserDto> response = testRestTemplate.postForEntity(USER_API_ENDPOINT + "/create", EXPECTED_USER_DTO, UserDto.class);
        //then
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(EXPECTED_USER_DTO.getEmail(), response.getBody().getEmail());
    }

    @Test
    void testFindUserById() {
        //when
        ResponseEntity<UserDto> responseWithSavedUser = testRestTemplate.postForEntity(USER_API_ENDPOINT + "/create", EXPECTED_USER_DTO, UserDto.class);
        ResponseEntity<UserDto> response = testRestTemplate.getForEntity(USER_API_ENDPOINT + "/{id}", UserDto.class, Objects.requireNonNull(responseWithSavedUser.getBody()).getId());
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(EXPECTED_USER_DTO.getEmail(), response.getBody().getEmail());
    }

    @Test
    void testFindUserByEmail() {
        //when
        testRestTemplate.postForEntity(USER_API_ENDPOINT + "/create", EXPECTED_USER_DTO, UserDto.class);
        ResponseEntity<UserDto> response = testRestTemplate.getForEntity(USER_API_ENDPOINT + "/email/{email}", UserDto.class, EXPECTED_EMAIL);
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(EXPECTED_USER_DTO.getEmail(), response.getBody().getEmail());
    }

    @Test
    void testExistsUserByEmail() {
        //when
        testRestTemplate.postForEntity(USER_API_ENDPOINT + "/create", EXPECTED_USER_DTO, UserDto.class);
        ResponseEntity<Boolean> response = testRestTemplate.getForEntity(USER_API_ENDPOINT + "/exists-by-email/{email}", Boolean.class, EXPECTED_EMAIL);
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getBody());
    }

    @Test
    void testCountUsers() {
        //given
        long expectedUsersCount = 1L;
        //when
        testRestTemplate.postForEntity(USER_API_ENDPOINT + "/create", EXPECTED_USER_DTO, UserDto.class);
        ResponseEntity<Long> response = testRestTemplate.getForEntity(USER_API_ENDPOINT + "/count", Long.class);
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(expectedUsersCount, response.getBody());
    }

}
