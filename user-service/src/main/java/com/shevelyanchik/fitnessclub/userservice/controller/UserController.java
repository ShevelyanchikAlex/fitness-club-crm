package com.shevelyanchik.fitnessclub.userservice.controller;

import com.shevelyanchik.fitnessclub.userservice.model.dto.UserDto;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserProfile;
import com.shevelyanchik.fitnessclub.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-service/users")
public class UserController {

    private final UserService userService;

    @PreAuthorize("permitAll()")
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));
    }

    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    @PatchMapping("/update")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto updatedUserDto) {
        return ResponseEntity.ok(userService.updateUser(updatedUserDto));
    }

    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    @PatchMapping("/update/{actualEmail}/email")
    public void updateUserEmail(@PathVariable String actualEmail,
                                @RequestParam("updatedEmail") String updatedEmail) {
        userService.updateUserEmailByActualEmail(actualEmail, updatedEmail);
    }

    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    @PatchMapping("/update/{id}/status")
    public void updateUserStatus(@PathVariable Long id, @RequestParam("status") String status) {
        userService.updateUserStatusById(id, status);
    }

    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    @PatchMapping("/update/{id}/role")
    public void updateUserRole(@PathVariable Long id, @RequestParam("role") String role) {
        userService.updateUserRoleById(id, role);
    }

    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    @PostMapping(path = "/update/{email}/profile-image", consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UserDto> updateUserProfileImage(@PathVariable String email,
                                                          @RequestParam("profileImage") MultipartFile profileImage) {
        return ResponseEntity.ok(userService.updateUserProfileImageByEmail(email, profileImage));
    }

    @PreAuthorize("hasAuthority('TRAINER_PERMISSION')")
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> findAllUsers(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                      @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("surname").ascending());
        Page<UserDto> userPage = userService.findAllUsers(pageable);
        return ResponseEntity.ok(userPage.getContent());
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> findUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    @GetMapping("/{email}/with-profile-image")
    public ResponseEntity<UserProfile> findUserWithProfileImageByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.findUserProfileByEmail(email));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/exists-by-email/{email}")
    public ResponseEntity<Boolean> existsUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.existsUserByEmail(email));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/count")
    public ResponseEntity<Long> countUsers() {
        return ResponseEntity.ok(userService.countUsers());
    }

}
