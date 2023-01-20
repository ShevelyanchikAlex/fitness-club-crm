package com.shevelyanchik.fitnessclub.service.impl;

import com.shevelyanchik.fitnessclub.model.domain.user.Role;
import com.shevelyanchik.fitnessclub.model.domain.user.Status;
import com.shevelyanchik.fitnessclub.model.dto.UserDto;
import com.shevelyanchik.fitnessclub.model.mapper.UserMapper;
import com.shevelyanchik.fitnessclub.persistence.UserRepository;
import com.shevelyanchik.fitnessclub.service.UserService;
import com.shevelyanchik.fitnessclub.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String RESOURCE_ALREADY_EXIST = "resource.already.exist";
    private static final String USER_NOT_FOUND = "user.not.found";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto save(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ServiceException(RESOURCE_ALREADY_EXIST);
        }
        userDto.setRole(Role.USER);
        userDto.setStatus(Status.ACTIVE);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto)));
    }

    @Override
    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ServiceException(USER_NOT_FOUND));
    }

    @Override
    public UserDto findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ServiceException(USER_NOT_FOUND));
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        List<UserDto> users = userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(users, pageable, userRepository.count());
    }

    @Override
    public Long getUsersCount() {
        return userRepository.count();
    }
}
