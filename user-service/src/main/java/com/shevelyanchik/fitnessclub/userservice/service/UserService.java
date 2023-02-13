package com.shevelyanchik.fitnessclub.userservice.service;

import com.shevelyanchik.fitnessclub.userservice.model.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDto findById(Long id);

    UserDto findByEmail(String email);

    Page<UserDto> findAll(Pageable pageable);

    Long getUsersCount();
}
