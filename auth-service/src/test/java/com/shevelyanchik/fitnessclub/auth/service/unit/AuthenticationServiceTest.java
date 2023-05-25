package com.shevelyanchik.fitnessclub.auth.service.unit;

import com.shevelyanchik.fitnessclub.auth.client.UserServiceClient;
import com.shevelyanchik.fitnessclub.auth.dto.AuthenticationRequest;
import com.shevelyanchik.fitnessclub.auth.dto.AuthenticationResponse;
import com.shevelyanchik.fitnessclub.auth.dto.user.Role;
import com.shevelyanchik.fitnessclub.auth.dto.user.Status;
import com.shevelyanchik.fitnessclub.auth.dto.user.UserDto;
import com.shevelyanchik.fitnessclub.auth.exception.UnauthorizedException;
import com.shevelyanchik.fitnessclub.auth.exception.ValidationException;
import com.shevelyanchik.fitnessclub.auth.service.AuthenticationProducerService;
import com.shevelyanchik.fitnessclub.auth.service.impl.AuthenticationServiceImpl;
import com.shevelyanchik.fitnessclub.auth.service.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    private static final AuthenticationRequest EXPECTED_AUTH_REQUEST = new AuthenticationRequest(
            "test@gmail.com", "pass");

    private static final UserDto EXPECTED_USER_DTO = new UserDto(
            1L, "Name", "Surname", "passUser1",
            "test@gmail.com", "+375443321233", Role.USER, Status.ACTIVE);


    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private UserServiceClient userServiceClient;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationProducerService authenticationProducerService;


    @Test
    void testSignup() {
        //given
        BDDMockito.given(userServiceClient.existsUserByEmail(any())).willReturn(false);
        BDDMockito.given(userServiceClient.createUser(any())).willReturn(EXPECTED_USER_DTO);
        //when
        UserDto actualUserDto = authenticationService.signup(EXPECTED_USER_DTO);
        //then
        BDDMockito.then(userServiceClient).should().existsUserByEmail(any());
        BDDMockito.then(userServiceClient).should().createUser(any());
        Assertions.assertEquals(EXPECTED_USER_DTO, actualUserDto);
    }

    @Test
    void testSignupWithExistedUser() {
        //given
        BDDMockito.given(userServiceClient.existsUserByEmail(any())).willThrow(ValidationException.class);
        //then
        Assertions.assertThrows(ValidationException.class, () -> authenticationService.signup(EXPECTED_USER_DTO));
        BDDMockito.then(userServiceClient).should().existsUserByEmail(any());
    }

    @Test
    void testLogin() {
        //given
        String expected_token = "token";
        BDDMockito.given(authenticationManager.authenticate(any())).willReturn(new UsernamePasswordAuthenticationToken(
                EXPECTED_AUTH_REQUEST.getEmail(), EXPECTED_AUTH_REQUEST.getPassword()));
        BDDMockito.given(jwtTokenProvider.createToken(EXPECTED_USER_DTO.getEmail(), EXPECTED_USER_DTO.getRole())).willReturn(expected_token);
        BDDMockito.given(userServiceClient.findUserByEmail(any())).willReturn(EXPECTED_USER_DTO);
        //when
        AuthenticationResponse actualAuthenticationResponse = authenticationService.login(EXPECTED_AUTH_REQUEST);
        //then
        BDDMockito.then(authenticationManager).should().authenticate(any());
        BDDMockito.then(jwtTokenProvider).should().createToken(EXPECTED_USER_DTO.getEmail(), EXPECTED_USER_DTO.getRole());
        Assertions.assertEquals(expected_token, actualAuthenticationResponse.getToken());
    }

    @Test
    void testLoginWithInvalidAuthRequest() {
        //given
        BDDMockito.given(authenticationManager.authenticate(any())).willThrow(UnauthorizedException.class);
        //then
        Assertions.assertThrows(UnauthorizedException.class, () -> authenticationService.login(EXPECTED_AUTH_REQUEST));
        BDDMockito.then(authenticationManager).should().authenticate(any());
    }

}