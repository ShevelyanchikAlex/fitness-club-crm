package com.shevelyanchik.fitnessclub.orderservice.util;

import com.shevelyanchik.fitnessclub.kafkaconfig.dto.EmailEvent;
import com.shevelyanchik.fitnessclub.orderservice.constant.EmailEventPayload;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderEventUtils {

    public static EmailEvent createEmailEvent(OrderDto orderDto) {
        EmailEventPayload orderCreatedEventPayload = EmailEventPayload.ORDER_HAS_BEEN_CREATED;
        String message = getEmailEventMessage(
                orderCreatedEventPayload,
                orderDto.getId(),
                orderDto.getUserId(),
                orderDto.getTrainerId(),
                orderDto.getServiceDto().getId(),
                orderDto.getOrderStatus()
        );

        return EmailEvent.builder()
                .subject(orderCreatedEventPayload.getSubject())
                .message(message)
                .build();
    }

    private static String getEmailEventMessage(EmailEventPayload emailEventPayload, Object... args) {
        return String.format(emailEventPayload.getMessageFormat(), args);
    }

}
