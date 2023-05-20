package com.shevelyanchik.fitnessclub.orderservice.model.dto;

import com.shevelyanchik.fitnessclub.orderservice.constant.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {

    private Long id;

    @FutureOrPresent
    private LocalDateTime trainingStartDateTime;

    @NotNull
    private ServiceDto serviceDto;

    @NotNull
    private Long trainerId;

    @NotNull
    private Long availableSpots;

    @NotNull
    private ServiceType serviceType;

}
