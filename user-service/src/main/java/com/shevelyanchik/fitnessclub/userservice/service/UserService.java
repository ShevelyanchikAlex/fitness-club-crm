package com.shevelyanchik.fitnessclub.userservice.service;

import com.shevelyanchik.fitnessclub.userservice.model.dto.UserDto;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * The UserService retrieving information about the users.
 *
 * @version 1.0.1
 */
public interface UserService {
    /**
     * Creates new User.
     *
     * @param userDto UserDto that contains all information about User.
     * @return User if userDto is not null and User with same email is not exist, throws ServiceException otherwise.
     */
    UserDto createUser(UserDto userDto);

    /**
     * Updates User.
     *
     * @param userDto updated UserDto.
     * @return UserDto if userDto is valid.
     */
    UserDto updateUser(UserDto userDto);

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
     * Finds User with ProfileImage.
     *
     * @param email User email.
     * @return UserResponse.
     */
    UserProfile findUserProfileByEmail(String email);

    /**
     * Returns all Users.
     *
     * @param pageable Pageable that is needed for pagination.
     * @return Users.
     */
    Page<UserDto> findAllUsers(Pageable pageable);

    /**
     * Updates User Status.
     *
     * @param id         User id.
     * @param statusName User status name.
     */
    void updateUserStatusById(Long id, String statusName);

    /**
     * Updates User role.
     *
     * @param id       User id.
     * @param roleName User role name.
     */
    void updateUserRoleById(Long id, String roleName);

    /**
     * Updates User ProfileImage.
     *
     * @param email        User email.
     * @param profileImage ProfileImage.
     * @return UserDto with added ProfileImage.
     */
    UserDto updateUserProfileImageByEmail(String email, MultipartFile profileImage);

    /**
     * Checks the existing User with email.
     *
     * @param email User email.
     * @return true if User with this email exist, false otherwise.
     */
    boolean existsUserByEmail(String email);

    /**
     * Returns number of Users.
     *
     * @return Number of Users.
     */
    Long countUsers();


    /**
     * Deletes all Users.
     */
    void deleteAllUsers();
}
