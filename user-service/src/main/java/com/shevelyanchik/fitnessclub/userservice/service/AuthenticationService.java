package com.shevelyanchik.fitnessclub.userservice.service;

import com.shevelyanchik.fitnessclub.userservice.model.dto.AuthenticationRequestDto;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserDto;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;

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
     * @param authenticationRequestDTO AuthenticationRequestDto that contains user's email and password.
     * @return Map that contains user's email and auth token.
     * @throws AuthenticationException Throws if AuthenticationRequestDto contains invalid data.
     */
    Map<Object, Object> login(AuthenticationRequestDto authenticationRequestDTO) throws AuthenticationException;
}
