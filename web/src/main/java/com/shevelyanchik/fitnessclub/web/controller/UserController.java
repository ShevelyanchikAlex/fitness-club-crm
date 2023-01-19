package com.shevelyanchik.fitnessclub.web.controller;

import com.shevelyanchik.fitnessclub.model.dto.UserDto;
import com.shevelyanchik.fitnessclub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/email/{email}")
    public UserDto findByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/count")
    public Long getUsersCount() {
        return userService.getUsersCount();
    }
}
