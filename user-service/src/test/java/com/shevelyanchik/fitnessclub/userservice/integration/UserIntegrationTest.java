package com.shevelyanchik.fitnessclub.userservice.integration;

import com.shevelyanchik.fitnessclub.userservice.constant.Role;
import com.shevelyanchik.fitnessclub.userservice.constant.Status;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserDto;
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
class UserIntegrationTest {

    @Autowired
    private UserService userService;


    private final UserDto EXPECTED_USER_DTO = new UserDto(
            1L, "Name", "Surname", "passUser1",
            "test@gmail.com", "+375443321233", Role.USER, Status.ACTIVE);
    private final String EXPECTED_EMAIL = "test@gmail.com";


    @AfterEach
    void deleteUsers() {
        userService.deleteAllUsers();
    }

    @Test
    void testCreateUser() {
        //when
        UserDto actualUserDto = userService.createUser(EXPECTED_USER_DTO);
        //then
        Assertions.assertEquals(EXPECTED_USER_DTO.getEmail(), actualUserDto.getEmail());
    }

    @Test
    void testFindUserById() {
        //given
        UserDto savedUserDto = userService.createUser(EXPECTED_USER_DTO);
        //when
        UserDto actualUserDto = userService.findUserById(savedUserDto.getId());
        //then
        Assertions.assertEquals(EXPECTED_USER_DTO.getEmail(), actualUserDto.getEmail());
    }

    @Test
    void testFindUserByEmail() {
        //given
        userService.createUser(EXPECTED_USER_DTO);
        //when
        UserDto actualUserDto = userService.findUserByEmail(EXPECTED_EMAIL);
        //then
        Assertions.assertEquals(EXPECTED_USER_DTO.getEmail(), actualUserDto.getEmail());
    }

    @Test
    void testFindAllUsers() {
        //given
        List<UserDto> expectedUserDtoList = new ArrayList<>(List.of(EXPECTED_USER_DTO));
        Pageable expectedPageable = PageRequest.of(0, 10);
        Page<UserDto> expectedPage = new PageImpl<>(expectedUserDtoList, expectedPageable, 1L);
        //when
        userService.createUser(EXPECTED_USER_DTO);
        Page<UserDto> actualUsersPage = userService.findAllUsers(expectedPageable);
        //then
        Assertions.assertEquals(expectedPage.getTotalElements(), actualUsersPage.getTotalElements());
    }

    @Test
    void testExistsUserByEmail() {
        //given
        userService.createUser(EXPECTED_USER_DTO);
        //when
        boolean isUserExists = userService.existsUserByEmail(EXPECTED_EMAIL);
        //then
        Assertions.assertTrue(isUserExists);
    }

    @Test
    void testCountUsers() {
        //given
        userService.createUser(EXPECTED_USER_DTO);
        long expectedUsersCount = 1L;
        //when
        long actualUsersCount = userService.countUsers();
        //then
        Assertions.assertEquals(expectedUsersCount, actualUsersCount);
    }

}
