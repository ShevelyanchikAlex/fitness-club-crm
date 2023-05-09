package com.shevelyanchik.fitnessclub.auth.service.impl;

import com.shevelyanchik.fitnessclub.auth.client.UserServiceClient;
import com.shevelyanchik.fitnessclub.auth.dto.AuthenticationRequest;
import com.shevelyanchik.fitnessclub.auth.dto.AuthenticationResponse;
import com.shevelyanchik.fitnessclub.auth.dto.user.Role;
import com.shevelyanchik.fitnessclub.auth.dto.user.Status;
import com.shevelyanchik.fitnessclub.auth.dto.user.UserDto;
import com.shevelyanchik.fitnessclub.auth.exception.UnauthorizedException;
import com.shevelyanchik.fitnessclub.auth.exception.ValidationException;
import com.shevelyanchik.fitnessclub.auth.service.AuthenticationProducerService;
import com.shevelyanchik.fitnessclub.auth.service.AuthenticationService;
import com.shevelyanchik.fitnessclub.auth.service.security.jwt.JwtTokenProvider;
import com.shevelyanchik.fitnessclub.auth.util.AuthenticationEventUtils;
import com.shevelyanchik.fitnessclub.kafkaconfig.dto.EmailEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserServiceClient userServiceClient;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProducerService authenticationProducerService;


    @Override
    public UserDto signup(UserDto userDto) {
        if (userServiceClient.existsUserByEmail(userDto.getEmail())) {
            throw new ValidationException("User with same email already exist");
        }

        UserDto savedUserDto = userServiceClient.createUser(configureUserDto(userDto));
        EmailEvent emailEvent = AuthenticationEventUtils.createEmailEvent(savedUserDto);
        authenticationProducerService.sendMessage(emailEvent);
        return savedUserDto;
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        String email = authenticationRequest.getEmail();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                email, authenticationRequest.getPassword());

        authenticateToken(authenticationToken);
        UserDto userDto = userServiceClient.findUserByEmail(email);
        String token = jwtTokenProvider.createToken(email, userDto.getRole());
        return new AuthenticationResponse(email, token);
    }

    private UserDto configureUserDto(UserDto userDto) {
        userDto.setRole(Role.USER);
        userDto.setStatus(Status.ACTIVE);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userDto;
    }

    private void authenticateToken(UsernamePasswordAuthenticationToken authenticationToken) {
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException ex) {
            throw new UnauthorizedException("Invalid email/password combination");
        }
    }

}
