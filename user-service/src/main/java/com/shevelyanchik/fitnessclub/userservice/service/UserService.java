package com.shevelyanchik.fitnessclub.userservice.service;

import com.shevelyanchik.fitnessclub.userservice.model.dto.UserDto;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * The UserService retrieving information about the users.
 *
 * @version 1.1.2
 */
public interface UserService {
    /**
     * Creates a new user using the provided user DTO.
     *
     * @param userDto The user DTO containing the user information.
     * @return The created user DTO.
     */
    UserDto createUser(UserDto userDto);

    /**
     * Updates an existing user with the provided updated user DTO.
     *
     * @param updatedUserDto The updated user DTO containing the modified user information.
     * @return The updated user DTO.
     */
    UserDto updateUser(UserDto updatedUserDto);

    /**
     * Updates the status of a user identified by the specified ID.
     *
     * @param id         The ID of the user to update.
     * @param statusName The new status name to assign to the user.
     */
    void updateUserStatusById(Long id, String statusName);

    /**
     * Updates the role of a user identified by the specified ID.
     *
     * @param id       The ID of the user to update.
     * @param roleName The new role name to assign to the user.
     */
    void updateUserRoleById(Long id, String roleName);

    /**
     * Updates the email of a user identified by the actual email.
     *
     * @param actualEmail  The actual email of the user to update.
     * @param updatedEmail The updated email to assign to the user.
     */
    void updateUserEmailByActualEmail(String actualEmail, String updatedEmail);

    /**
     * Updates the profile image of a user identified by the email.
     *
     * @param email        The email of the user to update.
     * @param profileImage The new profile image file to assign to the user.
     * @return The updated user DTO with the modified profile image.
     */
    UserDto updateUserProfileImageByEmail(String email, MultipartFile profileImage);

    /**
     * Finds a user by the specified ID.
     *
     * @param id The ID of the user to find.
     * @return The user DTO corresponding to the ID.
     */
    UserDto findUserById(Long id);

    /**
     * Finds a user by the specified email.
     *
     * @param email The email of the user to find.
     * @return The user DTO corresponding to the email.
     */
    UserDto findUserByEmail(String email);

    /**
     * Finds the user profile by the specified email.
     *
     * @param email The email of the user to find the profile for.
     * @return The user profile corresponding to the email.
     */
    UserProfile findUserProfileByEmail(String email);

    /**
     * Retrieves a paginated list of all users.
     *
     * @param pageable The pageable information for pagination and sorting.
     * @return A page of user DTOs.
     */
    Page<UserDto> findAllUsers(Pageable pageable);

    /**
     * Checks if a user with the specified email exists.
     *
     * @param email The email to check for existence.
     * @return True if a user with the email exists, false otherwise.
     */
    boolean existsUserByEmail(String email);

    /**
     * Checks if a user with the specified ID exists.
     *
     * @param id The ID to check for existence.
     * @return True if a user with the ID exists, false otherwise.
     */
    boolean existsUserById(Long id);

    /**
     * Counts the total number of users.
     *
     * @return The count of users.
     */
    Long countUsers();


    /**
     * Deletes all users.
     */
    void deleteAllUsers();

}
