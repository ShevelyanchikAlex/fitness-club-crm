package com.shevelyanchik.fitnessclub.orderservice.model.mapper;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderDto;
import com.shevelyanchik.fitnessclub.orderservice.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "service", target = "serviceDto")
    OrderDto toDto(Order order);

    @Mapping(source = "serviceDto", target = "service")
    Order toEntity(OrderDto orderDto);
}
