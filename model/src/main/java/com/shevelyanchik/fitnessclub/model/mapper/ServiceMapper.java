package com.shevelyanchik.fitnessclub.model.mapper;

import com.shevelyanchik.fitnessclub.model.domain.Service;
import com.shevelyanchik.fitnessclub.model.dto.ServiceDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    ServiceDto toDto(Service service);

    Service toEntity(ServiceDto serviceDto);
}
