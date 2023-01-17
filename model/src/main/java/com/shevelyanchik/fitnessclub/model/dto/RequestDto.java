package com.shevelyanchik.fitnessclub.model.dto;

import com.shevelyanchik.fitnessclub.model.domain.Service;
import com.shevelyanchik.fitnessclub.model.domain.Trainer;
import com.shevelyanchik.fitnessclub.model.domain.request.RequestStatus;
import com.shevelyanchik.fitnessclub.model.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    private long id;
    private LocalDateTime createdDateTime;
    private LocalDateTime trainingStartDateTime;
    private User user;
    private Trainer trainer;
    private Service service;
    private RequestStatus requestStatus;
}
