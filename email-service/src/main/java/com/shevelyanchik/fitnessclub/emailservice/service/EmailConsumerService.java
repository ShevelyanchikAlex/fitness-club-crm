package com.shevelyanchik.fitnessclub.emailservice.service;

import com.shevelyanchik.fitnessclub.emailservice.dto.EmailEvent;

public interface EmailConsumerService {
    void consume(EmailEvent event);
}
