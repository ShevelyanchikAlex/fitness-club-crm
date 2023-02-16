package com.shevelyanchik.fitnessclub.userservice.model.mapper;

import com.shevelyanchik.fitnessclub.userservice.model.domain.User;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
