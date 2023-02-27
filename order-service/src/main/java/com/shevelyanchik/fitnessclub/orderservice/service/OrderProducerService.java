package com.shevelyanchik.fitnessclub.orderservice.service;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.EmailEvent;

public interface OrderProducerService {
    void sendMessage(EmailEvent event);
}
