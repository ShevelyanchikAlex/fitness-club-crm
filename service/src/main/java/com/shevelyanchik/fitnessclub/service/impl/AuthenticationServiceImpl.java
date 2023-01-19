package com.shevelyanchik.fitnessclub.service.impl;

import com.shevelyanchik.fitnessclub.model.domain.user.User;
import com.shevelyanchik.fitnessclub.model.dto.AuthenticationRequestDTO;
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

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserService userService;

    private static final String EMAIL = "email";
    private static final String TOKEN = "token";

    @Override
    public UserDto signup(UserDto userDto) {
        return userService.save(userDto);
    }

    @Override
    public Map<Object, Object> login(AuthenticationRequestDTO authenticationRequestDTO) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequestDTO.getEmail(), authenticationRequestDTO.getPassword()));
        User user = userRepository.findByEmail(authenticationRequestDTO.getEmail()).orElseThrow(() ->
                new ServiceException("user.not.exist"));
        String token = jwtTokenProvider.createToken(authenticationRequestDTO.getEmail(), user.getRole().name());
        Map<Object, Object> response = new HashMap<>();
        response.put(EMAIL, authenticationRequestDTO.getEmail());
        response.put(TOKEN, token);
        return response;
    }
}
