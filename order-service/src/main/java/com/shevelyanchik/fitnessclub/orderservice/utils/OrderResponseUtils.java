package com.shevelyanchik.fitnessclub.orderservice.utils;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderResponseDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.user.TrainerDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.user.UserDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderResponseUtils {

    public static OrderResponseDto createOrderResponseDto(OrderDto orderDto, UserDto userDto, TrainerDto trainerDto) {
        return OrderResponseDto.builder()
                .id(orderDto.getId())
                .createdDateTime(orderDto.getCreatedDateTime())
                .trainingStartDateTime(orderDto.getTrainingStartDateTime())
                .userDto(userDto)
                .trainerDto(trainerDto)
                .service(orderDto.getService())
                .orderStatus(orderDto.getOrderStatus())
                .build();
    }

}
