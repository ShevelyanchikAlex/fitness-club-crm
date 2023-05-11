package com.shevelyanchik.fitnessclub.userservice.persistence;

import com.shevelyanchik.fitnessclub.userservice.constant.Role;
import com.shevelyanchik.fitnessclub.userservice.constant.Status;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserDto;
import com.shevelyanchik.fitnessclub.userservice.model.entity.User;
import com.shevelyanchik.fitnessclub.userservice.model.mapper.UserMapper;
import com.shevelyanchik.fitnessclub.userservice.model.mapper.UserMapperImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private final UserMapper userMapper = new UserMapperImpl();

    private final UserDto EXPECTED_USER_DTO = new UserDto(
            1L, "Name", "Surname", "passUser1",
            "test@gmail.com", "+375443321233", Role.USER, Status.ACTIVE);


    @AfterEach
    void cleanUsers() {
        userRepository.deleteAll();
    }


    @Test
    void testCreateUser() {
        //given
        User expectedUser = userMapper.toEntity(EXPECTED_USER_DTO);
        long expectedUsersCount = 1L;
        //when
        User actualUser = userRepository.save(expectedUser);
        long actualUsersCount = userRepository.count();
        //then
        Assertions.assertEquals(EXPECTED_USER_DTO.getEmail(), actualUser.getEmail());
        Assertions.assertEquals(expectedUsersCount, actualUsersCount);
    }

    @Test
    void testFindUserById() {
        //given
        User expectedUser = userMapper.toEntity(EXPECTED_USER_DTO);
        //when
        User savedUser = userRepository.save(expectedUser);
        User actualUser = userRepository.findById(savedUser.getId()).orElse(null);
        //then
        Assertions.assertNotNull(actualUser);
    }

    @Test
    void testFindUserByEmail() {
        //given
        User expectedUser = userMapper.toEntity(EXPECTED_USER_DTO);
        String expectedEmail = "test@gmail.com";
        //when
        userRepository.save(expectedUser);
        User actualUser = userRepository.findUserByEmail(expectedEmail).orElse(null);
        //then
        Assertions.assertNotNull(actualUser);
    }

    @Test
    void testFindAllUsers() {
        //given
        UserDto firstUserDto = new UserDto(1L, "Namefirst", "Surnamefirst", "passFirst1",
                "test1@gmail.com", "+375443321201", Role.USER, Status.ACTIVE);
        UserDto secondUserDto = new UserDto(2L, "Namesecond", "Surnamesecond", "passSecond2",
                "test2@gmail.com", "+375443321202", Role.USER, Status.ACTIVE);
        List<UserDto> expectedUserDtoList = new ArrayList<>(List.of(firstUserDto, secondUserDto));
        List<User> expectedUserList = expectedUserDtoList.stream()
                .map(userMapper::toEntity)
                .collect(Collectors.toList());
        long expectedUsersCount = 2L;
        //when
        userRepository.saveAll(expectedUserList);
        List<User> actualUsers = userRepository.findAll();
        //then
        Assertions.assertEquals(expectedUsersCount, actualUsers.size());
    }

    @Test
    void testCountUsers() {
        //given
        long expectedUsersCount = 0L;
        //when
        long actualUsersCount = userRepository.count();
        //then
        Assertions.assertEquals(expectedUsersCount, actualUsersCount);
    }

}
