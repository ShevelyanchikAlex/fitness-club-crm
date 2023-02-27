package com.shevelyanchik.fitnessclub.emailservice.service;

import com.shevelyanchik.fitnessclub.emailservice.dto.OrderEventDto;

public interface OrderConsumerService {
    void consume(OrderEventDto event);
}
