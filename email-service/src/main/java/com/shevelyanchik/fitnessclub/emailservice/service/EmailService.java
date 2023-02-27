package com.shevelyanchik.fitnessclub.emailservice.service;

import com.shevelyanchik.fitnessclub.emailservice.dto.EmailDetails;

public interface EmailService {
    void sendEmail(EmailDetails emailDetails);
}
