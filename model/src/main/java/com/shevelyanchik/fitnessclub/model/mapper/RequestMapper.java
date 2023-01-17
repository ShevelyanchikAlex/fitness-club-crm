package com.shevelyanchik.fitnessclub.model.mapper;

import com.shevelyanchik.fitnessclub.model.domain.request.Request;
import com.shevelyanchik.fitnessclub.model.dto.RequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestMapper {
    RequestDto toDto(Request request);

    Request toEntity(RequestDto requestDto);
}
