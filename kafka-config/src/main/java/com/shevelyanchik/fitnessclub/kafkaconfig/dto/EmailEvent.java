package com.shevelyanchik.fitnessclub.kafkaconfig.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailEvent {
    private String subject;
    private String message;
}
