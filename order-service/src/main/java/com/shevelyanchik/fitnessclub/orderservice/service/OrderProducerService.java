package com.shevelyanchik.fitnessclub.orderservice.service;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderEventDto;

public interface OrderProducerService {
    void sendMessage(OrderEventDto event);
}
