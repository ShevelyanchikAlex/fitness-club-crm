package com.shevelyanchik.fitnessclub.web.controller;

import com.shevelyanchik.fitnessclub.model.dto.UserDto;
import com.shevelyanchik.fitnessclub.service.UserService;
import com.shevelyanchik.fitnessclub.web.aop.CustomCacheable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public List<UserDto> findAll(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                 @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<UserDto> userPage = userService.findAll(PageRequest.of(pageIndex, size));
        return new ArrayList<>(userPage.getContent());
    }

    @CustomCacheable
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public UserDto findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public UserDto findByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @CustomCacheable
    @GetMapping("/count")
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public Long getUsersCount() {
        return userService.getUsersCount();
    }
}
