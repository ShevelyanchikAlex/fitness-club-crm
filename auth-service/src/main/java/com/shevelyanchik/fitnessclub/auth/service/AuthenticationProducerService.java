package com.shevelyanchik.fitnessclub.auth.service;

import com.shevelyanchik.fitnessclub.auth.dto.EmailEvent;

public interface AuthenticationProducerService {
    void sendMessage(EmailEvent event);
}
