package com.shevelyanchik.fitnessclub.service;

import com.shevelyanchik.fitnessclub.model.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDto save(UserDto userDto);

    UserDto findById(Long id);

    UserDto findByEmail(String email);

    Page<UserDto> findAll(Pageable pageable);

    Long getUsersCount();
}
