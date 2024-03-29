package com.shevelyanchik.fitnessclub.userservice.unit.service;

import com.shevelyanchik.fitnessclub.userservice.constant.Role;
import com.shevelyanchik.fitnessclub.userservice.constant.Status;
import com.shevelyanchik.fitnessclub.userservice.exception.EntityNotFoundException;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserDto;
import com.shevelyanchik.fitnessclub.userservice.model.entity.User;
import com.shevelyanchik.fitnessclub.userservice.model.mapper.UserMapper;
import com.shevelyanchik.fitnessclub.userservice.model.mapper.UserMapperImpl;
import com.shevelyanchik.fitnessclub.userservice.repository.UserRepository;
import com.shevelyanchik.fitnessclub.userservice.service.impl.UserServiceImpl;
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
class UserServiceTest {

    private static final UserDto EXPECTED_USER_DTO = new UserDto(
            1L, "Name", "Surname", "passUser1",
            "test@gmail.com", "+375443321233", "", Role.USER, Status.ACTIVE);
    private static final String EXPECTED_EMAIL = "test@gmail.com";

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private final UserMapper userMapper = new UserMapperImpl();


    @Test
    void testCreateUser() {
        //given
        User expectedUser = userMapper.toEntity(EXPECTED_USER_DTO);
        BDDMockito.given(userRepository.save(any())).willReturn(expectedUser);
        //when
        UserDto actualUserDto = userService.createUser(EXPECTED_USER_DTO);
        //then
        BDDMockito.then(userRepository).should().save(any());
        Assertions.assertEquals(EXPECTED_USER_DTO, actualUserDto);
    }

    @Test
    void testFindUserById() {
        //given
        User expectedUser = userMapper.toEntity(EXPECTED_USER_DTO);
        BDDMockito.given(userRepository.findById(any())).willReturn(Optional.of(expectedUser));
        //when
        UserDto actualUserDto = userService.findUserById(1L);
        //then
        BDDMockito.then(userRepository).should().findById(any());
        Assertions.assertEquals(EXPECTED_USER_DTO, actualUserDto);
    }

    @Test
    void testFindUserByIdWithUnExistingId() {
        //given
        long expectedId = 1L;
        BDDMockito.given(userRepository.findById(any())).willThrow(EntityNotFoundException.class);
        //then
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.findUserById(expectedId));
        BDDMockito.then(userRepository).should().findById(expectedId);
    }

    @Test
    void testFindUserByEmail() {
        //given
        User expectedUser = userMapper.toEntity(EXPECTED_USER_DTO);
        BDDMockito.given(userRepository.findUserByEmail(EXPECTED_EMAIL)).willReturn(Optional.of(expectedUser));
        //when
        UserDto actualUserDto = userService.findUserByEmail(EXPECTED_EMAIL);
        //then
        BDDMockito.then(userRepository).should().findUserByEmail(EXPECTED_EMAIL);
        Assertions.assertEquals(EXPECTED_USER_DTO, actualUserDto);
    }

    @Test
    void testFindUserByEmailWithExistingEmail() {
        //given
        BDDMockito.given(userRepository.findUserByEmail(any())).willThrow(EntityNotFoundException.class);
        //then
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.findUserByEmail(EXPECTED_EMAIL));
        BDDMockito.then(userRepository).should().findUserByEmail(EXPECTED_EMAIL);
    }

    @Test
    void testFindAllUsers() {
        //given
        UserDto firstUserDto = new UserDto(1L, "Namefirst", "Surnamefirst", "passFirst1",
                "test1@gmail.com", "+375443321201", "", Role.USER, Status.ACTIVE);
        UserDto secondUserDto = new UserDto(2L, "Namesecond", "Surnamesecond", "passSecond2",
                "test2@gmail.com", "+375443321202", "", Role.USER, Status.ACTIVE);
        List<UserDto> expectedUserDtoList = new ArrayList<>(List.of(firstUserDto, secondUserDto));
        User expectedFirstUser = userMapper.toEntity(firstUserDto);
        User expectedSecondUser = userMapper.toEntity(secondUserDto);
        List<User> expectedUserList = new ArrayList<>(List.of(expectedFirstUser, expectedSecondUser));
        long expectedUsersCount = 1L;
        Pageable expectedPageable = PageRequest.of(0, 10);
        Page<User> expectedPage = new PageImpl<>(expectedUserList, expectedPageable, 1L);

        BDDMockito.given(userRepository.count()).willReturn(expectedUsersCount);
        BDDMockito.given(userRepository.findAll(expectedPageable)).willReturn(expectedPage);
        //when
        Page<UserDto> actualUsersPage = userService.findAllUsers(expectedPageable);
        //then
        BDDMockito.then(userRepository).should().count();
        BDDMockito.then(userRepository).should().findAll(expectedPageable);
        Assertions.assertEquals(expectedUserDtoList.size(), actualUsersPage.getTotalElements());
    }

    @Test
    void testExistsUserByEmail() {
        //given
        BDDMockito.given(userRepository.existsUserByEmail(EXPECTED_EMAIL)).willReturn(true);
        //when
        boolean isUserExists = userService.existsUserByEmail(EXPECTED_EMAIL);
        //then
        BDDMockito.then(userRepository).should().existsUserByEmail(EXPECTED_EMAIL);
        Assertions.assertTrue(isUserExists);
    }

    @Test
    void testCountUsers() {
        //given
        long expectedUsersCount = 1L;
        BDDMockito.given(userRepository.count()).willReturn(expectedUsersCount);
        //when
        long actualUsersCount = userService.countUsers();
        //then
        BDDMockito.then(userRepository).should().count();
        Assertions.assertEquals(expectedUsersCount, actualUsersCount);
    }

}