package com.shevelyanchik.fitnessclub.auth.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shevelyanchik.fitnessclub.auth.client.UserServiceClient;
import com.shevelyanchik.fitnessclub.auth.dto.*;
import com.shevelyanchik.fitnessclub.auth.dto.user.Role;
import com.shevelyanchik.fitnessclub.auth.dto.user.Status;
import com.shevelyanchik.fitnessclub.auth.dto.user.UserDto;
import com.shevelyanchik.fitnessclub.auth.service.AuthenticationService;
import com.shevelyanchik.fitnessclub.auth.service.security.jwt.JwtTokenProvider;
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
    private static final String RESOURCE_ALREADY_EXIST = "resource.already.exist";
    private final UserServiceClient userServiceClient;
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
        return userServiceClient.createUser(userDto);
    }

    @Override
    public Map<Object, Object> login(AuthenticationRequestDto authenticationRequestDTO) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        authenticationRequestDTO.getEmail(),
                        authenticationRequestDTO.getPassword()
                );

        authenticationManager.authenticate(authentication);

        UserDto userDto = userServiceClient
                .findUserByEmail(authenticationRequestDTO.getEmail());

        String token = jwtTokenProvider
                .createToken(authenticationRequestDTO.getEmail(), userDto.getRole());
        AuthenticationResponseDto response = new AuthenticationResponseDto(authenticationRequestDTO.getEmail(), token);
        return objectMapper.convertValue(response, new TypeReference<>() {
        });
    }

    private void validateUser(UserDto userDto) {
        if (Objects.isNull(userDto)) {
            throw new com.shevelyanchik.fitnessclub.auth.exception.AuthenticationException(USER_VALIDATE_ERROR);
        }
        if (userServiceClient.existsUserByEmail(userDto.getEmail())) {
            throw new com.shevelyanchik.fitnessclub.auth.exception.AuthenticationException(RESOURCE_ALREADY_EXIST);
        }
    }
}
