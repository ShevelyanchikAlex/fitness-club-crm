package com.shevelyanchik.fitnessclub.userservice.service;

import com.shevelyanchik.fitnessclub.userservice.model.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The UserService retrieving information about the users.
 *
 * @version 1.0.1
 */
public interface UserService {
    /**
     * If the user exists, this method will return a User that contains information about them.
     *
     * @param id User id.
     * @return User if the user exists, throws ServiceException otherwise.
     */
    UserDto findUserById(Long id);

    /**
     * If the user with such email exists, this method will return a User that contains information about them.
     *
     * @param email User email.
     * @return User if the user exists, throws ServiceException otherwise.
     */
    UserDto findUserByEmail(String email);

    /**
     * Returns all Users.
     *
     * @param pageable Pageable that is needed for pagination.
     * @return Users.
     */
    Page<UserDto> findAllUsers(Pageable pageable);

    /**
     * Returns number of Users.
     *
     * @return Number of Users.
     */
    Long countUsers();
}
