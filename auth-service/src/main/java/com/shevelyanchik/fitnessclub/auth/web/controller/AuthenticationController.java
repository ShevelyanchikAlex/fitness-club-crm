package com.shevelyanchik.fitnessclub.auth.web.controller;

import com.shevelyanchik.fitnessclub.auth.dto.AuthenticationRequestDto;
import com.shevelyanchik.fitnessclub.auth.dto.user.UserDto;
import com.shevelyanchik.fitnessclub.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/auth-service/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public UserDto signup(@RequestBody UserDto userDto) {
        return authenticationService.signup(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDto authenticationRequestDTO) {
        try {
            return ResponseEntity.ok(authenticationService.login(authenticationRequestDTO));
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(httpServletRequest, httpServletResponse, null);
    }
}
