package com.shevelyanchik.fitnessclub.orderservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDto {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @Positive
    private BigDecimal price;
}
