package com.shevelyanchik.fitnessclub.orderservice.model.dto;

import com.shevelyanchik.fitnessclub.orderservice.constant.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;

    private LocalDateTime createdDateTime;

    @FutureOrPresent
    private LocalDateTime trainingStartDateTime;

    @NotNull
    private Long userId;

    @NotNull
    private Long trainerId;

    @NotNull
    private ServiceDto serviceDto;

    private OrderStatus orderStatus;
}
