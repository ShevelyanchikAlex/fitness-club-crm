package com.shevelyanchik.fitnessclub.newsservice.model.mapper;

import com.shevelyanchik.fitnessclub.newsservice.model.dto.NewsApiArticle;
import com.shevelyanchik.fitnessclub.newsservice.model.dto.NewsArticleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface NewsApiArticleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "urlToImage", target = "imageUrl")
    @Mapping(target = "createdDateTime", expression = "java(mapDateTime(newsApiArticle.getPublishedAt()))")
    NewsArticleDto toNewsArticleDto(NewsApiArticle newsApiArticle);

    default LocalDateTime mapDateTime(String dateTime) {
        DateTimeFormatter sourceFormatter = DateTimeFormatter.ISO_INSTANT;
        Instant instant = Instant.from(sourceFormatter.parse(dateTime));
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

}
