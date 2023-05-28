package com.shevelyanchik.fitnessclub.newsservice.service;

import com.shevelyanchik.fitnessclub.newsservice.model.dto.NewsArticleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The NewsArticleService interface provides methods for managing news articles.
 *
 * @version 1.0.1
 */
public interface NewsArticleService {

    /**
     * Creates a new news article.
     *
     * @param newsArticleDto The NewsArticleDto object representing the news article to be created.
     * @return The created NewsArticleDto object.
     */
    NewsArticleDto createNewsArticle(NewsArticleDto newsArticleDto);

    /**
     * Updates an existing news article.
     *
     * @param updatedNewsArticleDto The NewsArticleDto object containing the updated news article data.
     * @return The updated NewsArticleDto object.
     */
    NewsArticleDto updateNewsArticle(NewsArticleDto updatedNewsArticleDto);

    /**
     * Retrieves a news article by its ID.
     *
     * @param id The ID of the news article to retrieve.
     * @return The NewsArticleDto object representing the retrieved news article.
     */
    NewsArticleDto findNewsArticleById(Long id);

    /**
     * Retrieves all news articles with pagination.
     *
     * @param pageable The pageable information for pagination and sorting.
     * @return A Page object containing the list of NewsArticleDto objects.
     */
    Page<NewsArticleDto> findAllNewsArticles(Pageable pageable);

    /**
     * Counts the total number of news articles.
     *
     * @return The total number of news articles.
     */
    Long countNewsArticles();

    /**
     * Deletes a news article by its ID.
     *
     * @param id The ID of the news article to delete.
     */
    void deleteNewsArticleById(Long id);

    /**
     * Deletes all news articles.
     */
    void deleteAllServices();

}
