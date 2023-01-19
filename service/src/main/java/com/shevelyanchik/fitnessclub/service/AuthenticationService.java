package com.shevelyanchik.fitnessclub.service;

import com.shevelyanchik.fitnessclub.model.dto.AuthenticationRequestDTO;
import com.shevelyanchik.fitnessclub.model.dto.UserDto;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;

public interface AuthenticationService {
    UserDto signup(UserDto userDto);

    Map<Object, Object> login(AuthenticationRequestDTO authenticationRequestDTO) throws AuthenticationException;
}
