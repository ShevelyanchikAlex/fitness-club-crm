package com.shevelyanchik.fitnessclub.model.mapper;

import com.shevelyanchik.fitnessclub.model.domain.FitnessClubInfo;
import com.shevelyanchik.fitnessclub.model.dto.FitnessClubInfoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FitnessClubInfoMapper {
    FitnessClubInfoDto toDto(FitnessClubInfo fitnessClubInfo);

    FitnessClubInfo toEntity(FitnessClubInfoDto fitnessClubInfoDto);
}
