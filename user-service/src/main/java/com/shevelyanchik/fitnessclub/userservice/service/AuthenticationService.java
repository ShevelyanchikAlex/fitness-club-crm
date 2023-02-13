package com.shevelyanchik.fitnessclub.userservice.service;

import com.shevelyanchik.fitnessclub.userservice.model.dto.AuthenticationRequestDto;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserDto;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;

public interface AuthenticationService {
    UserDto signup(UserDto userDto);

    Map<Object, Object> login(AuthenticationRequestDto authenticationRequestDTO) throws AuthenticationException;
}
