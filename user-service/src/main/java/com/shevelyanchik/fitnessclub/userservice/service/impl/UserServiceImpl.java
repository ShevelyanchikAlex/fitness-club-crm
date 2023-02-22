package com.shevelyanchik.fitnessclub.userservice.service.impl;

import com.shevelyanchik.fitnessclub.userservice.model.domain.User;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserDto;
import com.shevelyanchik.fitnessclub.userservice.model.mapper.UserMapper;
import com.shevelyanchik.fitnessclub.userservice.persistence.UserRepository;
import com.shevelyanchik.fitnessclub.userservice.service.UserService;
import com.shevelyanchik.fitnessclub.userservice.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String USER_NOT_FOUND = "user.not.found";

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto findUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ServiceException(USER_NOT_FOUND));
    }

    @Override
    public UserDto findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ServiceException(USER_NOT_FOUND));
    }

    @Override
    public Page<UserDto> findAllUsers(Pageable pageable) {
        List<UserDto> users = userRepository.findAll(pageable).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(users, pageable, userRepository.count());
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Override
    public Long countUsers() {
        return userRepository.count();
    }
}
