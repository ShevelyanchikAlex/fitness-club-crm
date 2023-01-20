package com.shevelyanchik.fitnessclub.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shevelyanchik.fitnessclub.model.domain.user.User;
import com.shevelyanchik.fitnessclub.model.dto.AuthenticationRequestDto;
import com.shevelyanchik.fitnessclub.model.dto.AuthenticationResponseDto;
import com.shevelyanchik.fitnessclub.model.dto.UserDto;
import com.shevelyanchik.fitnessclub.persistence.UserRepository;
import com.shevelyanchik.fitnessclub.service.AuthenticationService;
import com.shevelyanchik.fitnessclub.service.UserService;
import com.shevelyanchik.fitnessclub.service.exception.ServiceException;
import com.shevelyanchik.fitnessclub.service.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final String USER_NOT_EXIST = "user.not.exist";
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Override
    public UserDto signup(UserDto userDto) {
        return userService.save(userDto);
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
                .findByEmail(authenticationRequestDTO.getEmail())
                .orElseThrow(() -> new ServiceException(USER_NOT_EXIST));

        String token = jwtTokenProvider.createToken(authenticationRequestDTO.getEmail(), user.getRole().name());
        AuthenticationResponseDto response = new AuthenticationResponseDto(authenticationRequestDTO.getEmail(), token);
        return objectMapper.convertValue(response, new TypeReference<>() {
        });
    }
}
