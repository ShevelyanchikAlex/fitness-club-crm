package com.shevelyanchik.fitnessclub.orderservice.constant;

import com.shevelyanchik.fitnessclub.orderservice.exception.ValidationException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OrderStatus {
    CONFIRMED,
    IN_PROCESSING,
    REJECTED;

    public static OrderStatus getOrderStatusByName(String orderStatusName) {
        return Arrays.stream(OrderStatus.values())
                .filter(orderStatus -> orderStatus.name().equalsIgnoreCase(orderStatusName))
                .findFirst()
                .orElseThrow(() -> new ValidationException("Not supported OrderStatus: " + orderStatusName));
    }

}
