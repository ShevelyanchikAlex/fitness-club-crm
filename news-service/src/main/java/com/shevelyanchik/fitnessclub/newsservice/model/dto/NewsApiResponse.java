package com.shevelyanchik.fitnessclub.newsservice.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class NewsApiResponse {
    private List<NewsApiArticle> articles;
}
