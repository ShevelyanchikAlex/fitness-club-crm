package com.shevelyanchik.fitnessclub.auth.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidatorRegex {

    public static final String NAME_REGEX = "^([А-Я][а-яё]{1,30}|[A-Z][a-z]{1,30})$";
    public static final String SURNAME_REGEX = "^([А-Я][а-яё]{1,40}|[A-Z][a-z]{1,40})$";
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d#$@!%&*?]{8,30}$";
    public static final String PHONE_NUMBER_REGEX = "^\\+375(17|29|33|44)[0-9]{7}$";

}
