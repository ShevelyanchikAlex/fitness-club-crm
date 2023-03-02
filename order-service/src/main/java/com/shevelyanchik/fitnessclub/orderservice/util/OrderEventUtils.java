package com.shevelyanchik.fitnessclub.orderservice.util;

import com.shevelyanchik.fitnessclub.orderservice.constant.EmailEventPayload;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderEventUtils {

    public static String getEmailEventMessage(EmailEventPayload emailEventPayload, Object... args) {
        return String.format(emailEventPayload.getMessageFormat(), args);
    }
}
