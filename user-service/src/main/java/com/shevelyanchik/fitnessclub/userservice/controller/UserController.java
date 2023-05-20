package com.shevelyanchik.fitnessclub.userservice.controller;

import com.shevelyanchik.fitnessclub.userservice.model.dto.UserDto;
import com.shevelyanchik.fitnessclub.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-service/users")
public class UserController {
    private final UserService userService;

    @PreAuthorize("permitAll()")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    @PatchMapping("/update")
    public UserDto updateUser(@Valid @RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
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

    @PreAuthorize("hasAuthority('TRAINER_PERMISSION')")
    @GetMapping
    public List<UserDto> findAllUsers(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                      @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<UserDto> userPage = userService.findAllUsers(PageRequest.of(page, size));
        return userPage.getContent();
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public UserDto findUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/email/{email}")
    public UserDto findUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/exists-by-email/{email}")
    public boolean existsUserByEmail(@PathVariable String email) {
        return userService.existsUserByEmail(email);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/count")
    public Long countUsers() {
        return userService.countUsers();
    }
}
