package com.shevelyanchik.fitnessclub.auth.service;

import com.shevelyanchik.fitnessclub.auth.dto.AuthenticationRequest;
import com.shevelyanchik.fitnessclub.auth.dto.AuthenticationResponse;
import com.shevelyanchik.fitnessclub.auth.dto.user.UserDto;

/**
 * The AuthenticationService provides information useful for forcing a user to log in or sign up.
 *
 * @version 1.0.1
 */
public interface AuthenticationService {
    /**
     * Signs up a user with the provided user data.
     *
     * @param userDto The UserDto object containing the user data.
     * @return The UserDto object representing the signed-up user.
     */
    UserDto signup(UserDto userDto);

    /**
     * Logs in a user using the provided authentication request.
     *
     * @param authenticationRequest The AuthenticationRequest object containing the login credentials.
     * @return The AuthenticationResponse object representing the login result.
     */
    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
}
