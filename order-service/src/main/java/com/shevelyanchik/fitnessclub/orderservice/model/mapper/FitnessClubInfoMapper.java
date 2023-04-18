package com.shevelyanchik.fitnessclub.orderservice.model.mapper;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.FitnessClubInfoDto;
import com.shevelyanchik.fitnessclub.orderservice.model.entity.FitnessClubInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FitnessClubInfoMapper {
    FitnessClubInfoDto toDto(FitnessClubInfo fitnessClubInfo);

    FitnessClubInfo toEntity(FitnessClubInfoDto fitnessClubInfoDto);
}
