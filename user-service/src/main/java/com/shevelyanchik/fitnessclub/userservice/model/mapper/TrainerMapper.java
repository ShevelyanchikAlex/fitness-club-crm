package com.shevelyanchik.fitnessclub.userservice.model.mapper;

import com.shevelyanchik.fitnessclub.userservice.model.dto.TrainerDto;
import com.shevelyanchik.fitnessclub.userservice.model.entity.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TrainerMapper {

    @Mapping(source = "user", target = "userDto")
    TrainerDto toDto(Trainer trainer);

    @Mapping(source = "userDto", target = "user")
    Trainer toEntity(TrainerDto trainerDto);
}
