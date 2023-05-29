package com.shevelyanchik.fitnessclub.newsservice.util;

import com.shevelyanchik.fitnessclub.kafkaconfig.dto.EmailEvent;
import com.shevelyanchik.fitnessclub.newsservice.constant.EmailEventPayload;
import com.shevelyanchik.fitnessclub.newsservice.model.dto.NewsArticleDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class NewsEmailEventUtils {

    public EmailEvent createEmailEvent(NewsArticleDto newsArticleDto) {
        EmailEventPayload newsArticleCreatedEventPayload = EmailEventPayload.NEWS_ARTICLE_HAS_BEEN_CREATED;
        String message = getEmailEventMessage(
                newsArticleCreatedEventPayload,
                newsArticleDto.getTitle(),
                newsArticleDto.getAuthor()
        );

        return EmailEvent.builder()
                .subject(newsArticleCreatedEventPayload.getSubject())
                .message(message)
                .build();
    }

    private String getEmailEventMessage(EmailEventPayload emailEventPayload, Object... args) {
        return String.format(emailEventPayload.getMessageFormat(), args);
    }

}
