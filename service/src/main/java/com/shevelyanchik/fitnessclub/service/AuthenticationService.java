package com.shevelyanchik.fitnessclub.service;

import com.shevelyanchik.fitnessclub.model.dto.AuthenticationRequestDto;
import com.shevelyanchik.fitnessclub.model.dto.UserDto;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;

public interface AuthenticationService {
    UserDto signup(UserDto userDto);

    Map<Object, Object> login(AuthenticationRequestDto authenticationRequestDTO) throws AuthenticationException;
}
