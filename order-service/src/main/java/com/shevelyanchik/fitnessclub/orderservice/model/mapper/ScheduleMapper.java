package com.shevelyanchik.fitnessclub.orderservice.model.mapper;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.ScheduleDto;
import com.shevelyanchik.fitnessclub.orderservice.model.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    @Mapping(source = "service", target = "serviceDto")
    ScheduleDto toDto(Schedule schedule);

    @Mapping(source = "serviceDto", target = "service")
    Schedule toEntity(ScheduleDto scheduleDto);

}
