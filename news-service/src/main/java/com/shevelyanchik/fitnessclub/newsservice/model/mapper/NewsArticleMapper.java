package com.shevelyanchik.fitnessclub.newsservice.model.mapper;

import com.shevelyanchik.fitnessclub.newsservice.model.dto.NewsArticleDto;
import com.shevelyanchik.fitnessclub.newsservice.model.entity.NewsArticle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewsArticleMapper {

    NewsArticleDto toDto(NewsArticle newsArticle);

    NewsArticle toEntity(NewsArticleDto newsArticleDto);

}
