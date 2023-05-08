package com.shevelyanchik.fitnessclub.auth.service;

import com.shevelyanchik.fitnessclub.auth.dto.AuthenticationRequest;
import com.shevelyanchik.fitnessclub.auth.dto.AuthenticationResponse;
import com.shevelyanchik.fitnessclub.auth.dto.user.UserDto;
import org.springframework.security.core.AuthenticationException;

/**
 * The AuthenticationService provides information useful for forcing a user to log in or sign up.
 *
 * @version 1.0.1
 */
public interface AuthenticationService {
    /**
     * Creates new User.
     *
     * @param userDto UserDto that contains all information about User.
     * @return User if userDto is not null and User with same email is not exist, throws ServiceException otherwise.
     */
    UserDto signup(UserDto userDto);

    /**
     * Returns user's email and auth token.
     *
     * @param authenticationRequest AuthenticationRequest that contains user's email and password.
     * @return Map that contains user's email and auth token.
     * @throws AuthenticationException Throws if AuthenticationRequest contains invalid data.
     */
    AuthenticationResponse login(AuthenticationRequest authenticationRequest) throws AuthenticationException;
}
