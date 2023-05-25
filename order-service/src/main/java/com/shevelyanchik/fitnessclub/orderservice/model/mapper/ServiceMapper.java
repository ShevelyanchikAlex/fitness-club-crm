package com.shevelyanchik.fitnessclub.orderservice.model.mapper;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.ServiceDto;
import com.shevelyanchik.fitnessclub.orderservice.model.entity.Service;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    ServiceDto toDto(Service service);

    Service toEntity(ServiceDto serviceDto);
}
