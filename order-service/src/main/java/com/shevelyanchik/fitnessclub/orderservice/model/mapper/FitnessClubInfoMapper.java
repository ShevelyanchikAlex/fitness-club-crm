package com.shevelyanchik.fitnessclub.orderservice.model.mapper;

import com.shevelyanchik.fitnessclub.orderservice.model.domain.FitnessClubInfo;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.FitnessClubInfoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FitnessClubInfoMapper {
    FitnessClubInfoDto toDto(FitnessClubInfo fitnessClubInfo);

    FitnessClubInfo toEntity(FitnessClubInfoDto fitnessClubInfoDto);
}
