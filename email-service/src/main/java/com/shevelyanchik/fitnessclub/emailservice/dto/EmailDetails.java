package com.shevelyanchik.fitnessclub.emailservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {
    private String subject;
    private String recipient;
    private String message;
}
