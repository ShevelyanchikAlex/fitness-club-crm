package com.shevelyanchik.fitnessclub.orderservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEventDto {
    private String message;
    private Long orderId;
    private String status;
}
