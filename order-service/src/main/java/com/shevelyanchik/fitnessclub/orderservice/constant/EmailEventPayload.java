package com.shevelyanchik.fitnessclub.orderservice.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailEventPayload {
    ORDER_HAS_BEEN_CREATED("New Order has been created.", "New Order has been created with id: %s, userId: %s, trainerId: %s, serviceId: %s, orderStatus: %s.");

    private final String subject;
    private final String messageFormat;
}
