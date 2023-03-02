package com.shevelyanchik.fitnessclub.auth.util;

import com.shevelyanchik.fitnessclub.auth.constant.EmailEventPayload;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthenticationEventUtils {

    public static String getEmailEventMessage(EmailEventPayload emailEventPayload, Object... args) {
        return String.format(emailEventPayload.getMessageFormat(), args);
    }
}
