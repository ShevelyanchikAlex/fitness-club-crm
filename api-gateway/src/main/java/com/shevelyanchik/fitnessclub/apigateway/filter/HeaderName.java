package com.shevelyanchik.fitnessclub.apigateway.filter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HeaderName {
    public static final String USERNAME = "username";
    public static final String AUTHORITIES = "authorities";
}
