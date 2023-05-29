package com.shevelyanchik.fitnessclub.newsservice.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailEventPayload {
    NEWS_ARTICLE_HAS_BEEN_CREATED("News Article has been created.", "Title: %s, author: %s");

    private final String subject;
    private final String messageFormat;
}
