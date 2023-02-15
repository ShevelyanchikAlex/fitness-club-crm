package com.shevelyanchik.fitnessclub.userservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shevelyanchik.fitnessclub.userservice.model.domain.Role;
import com.shevelyanchik.fitnessclub.userservice.model.domain.Status;
import com.shevelyanchik.fitnessclub.userservice.model.domain.User;
import com.shevelyanchik.fitnessclub.userservice.model.dto.AuthenticationRequestDto;
import com.shevelyanchik.fitnessclub.userservice.model.dto.AuthenticationResponseDto;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserDto;
import com.shevelyanchik.fitnessclub.userservice.model.mapper.UserMapper;
import com.shevelyanchik.fitnessclub.userservice.persistence.UserRepository;
import com.shevelyanchik.fitnessclub.userservice.service.AuthenticationService;
import com.shevelyanchik.fitnessclub.userservice.service.exception.ServiceException;
import com.shevelyanchik.fitnessclub.userservice.service.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final String USER_VALIDATE_ERROR = "user.validate.error";
    private static final String USER_NOT_EXIST = "user.not.exist";
    private static final String RESOURCE_ALREADY_EXIST = "resource.already.exist";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    @Override
    public UserDto signup(UserDto userDto) {
        validateUser(userDto);
        userDto.setRole(Role.USER);
        userDto.setStatus(Status.ACTIVE);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User user = userMapper.toEntity(userDto);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public Map<Object, Object> login(AuthenticationRequestDto authenticationRequestDTO) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        authenticationRequestDTO.getEmail(),
                        authenticationRequestDTO.getPassword()
                );

        authenticationManager.authenticate(authentication);

        User user = userRepository
                .findUserByEmail(authenticationRequestDTO.getEmail())
                .orElseThrow(() -> new ServiceException(USER_NOT_EXIST));

        String token = jwtTokenProvider.createToken(authenticationRequestDTO.getEmail(), user.getRole().name());
        AuthenticationResponseDto response = new AuthenticationResponseDto(authenticationRequestDTO.getEmail(), token);
        return objectMapper.convertValue(response, new TypeReference<>() {
        });
    }

    private void validateUser(UserDto userDto) {
        if (Objects.isNull(userDto)) {
            throw new ServiceException(USER_VALIDATE_ERROR);
        }
        if (userRepository.existsUserByEmail(userDto.getEmail())) {
            throw new ServiceException(RESOURCE_ALREADY_EXIST);
        }
    }
}
