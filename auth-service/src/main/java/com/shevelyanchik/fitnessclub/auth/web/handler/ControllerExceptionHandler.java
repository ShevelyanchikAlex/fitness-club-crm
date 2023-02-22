package com.shevelyanchik.fitnessclub.auth.web.handler;

import com.shevelyanchik.fitnessclub.auth.service.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

@ResponseBody
@ControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {
    private static final String BAD_REQUEST_MESSAGE_KEY = "bad.request";
    private final MessageSource messageSource;

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleServiceException(AuthenticationException exception) {
        String errorMessage = messageSource.getMessage(exception.getMessage(), new Object[]{}, Locale.ENGLISH);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleIllegalArgumentException() {
        String errorMessage = messageSource.getMessage(BAD_REQUEST_MESSAGE_KEY, new Object[]{}, Locale.ENGLISH);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
