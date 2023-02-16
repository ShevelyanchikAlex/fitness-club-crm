package com.shevelyanchik.fitnessclub.orderservice.model.dto;

import com.shevelyanchik.fitnessclub.orderservice.model.domain.OrderStatus;
import com.shevelyanchik.fitnessclub.orderservice.model.domain.Service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private LocalDateTime createdDateTime;
    private LocalDateTime trainingStartDateTime;
    private Long userId;
    private Long trainerId;
    private Service service;
    private OrderStatus orderStatus;
}
