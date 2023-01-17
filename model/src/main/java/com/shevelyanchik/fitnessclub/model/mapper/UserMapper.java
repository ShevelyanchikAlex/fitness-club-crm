package com.shevelyanchik.fitnessclub.model.mapper;

import com.shevelyanchik.fitnessclub.model.domain.user.User;
import com.shevelyanchik.fitnessclub.model.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
