package com.shevelyanchik.fitnessclub.auth.utils;

import com.shevelyanchik.fitnessclub.auth.constant.EmailEventPayload;
import com.shevelyanchik.fitnessclub.auth.dto.user.UserDto;
import com.shevelyanchik.fitnessclub.kafkaconfig.dto.EmailEvent;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthenticationEventUtils {

    public EmailEvent createEmailEvent(UserDto userDto) {
        EmailEventPayload userCreatedEventPayload = EmailEventPayload.USER_HAS_BEEN_CREATED;
        String message = getEmailEventMessage(
                userCreatedEventPayload,
                userDto.getId(),
                userDto.getEmail()
        );

        return EmailEvent.builder()
                .subject(userCreatedEventPayload.getSubject())
                .message(message)
                .build();
    }

    private String getEmailEventMessage(EmailEventPayload emailEventPayload, Object... args) {
        return String.format(emailEventPayload.getMessageFormat(), args);
    }
}
