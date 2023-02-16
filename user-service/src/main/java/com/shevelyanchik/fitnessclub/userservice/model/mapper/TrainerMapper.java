package com.shevelyanchik.fitnessclub.userservice.model.mapper;

import com.shevelyanchik.fitnessclub.userservice.model.domain.Trainer;
import com.shevelyanchik.fitnessclub.userservice.model.dto.TrainerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TrainerMapper {
    TrainerDto toDto(Trainer trainer);

    Trainer toEntity(TrainerDto trainerDto);
}
