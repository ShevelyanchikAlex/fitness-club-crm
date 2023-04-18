package com.shevelyanchik.fitnessclub.orderservice.model.dto;

import com.shevelyanchik.fitnessclub.orderservice.constant.OrderStatus;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.user.TrainerDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.user.UserDto;
import com.shevelyanchik.fitnessclub.orderservice.model.entity.Service;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Long id;
    private LocalDateTime createdDateTime;
    private LocalDateTime trainingStartDateTime;
    private UserDto userDto;
    private TrainerDto trainerDto;
    private Service service;
    private OrderStatus orderStatus;
}
