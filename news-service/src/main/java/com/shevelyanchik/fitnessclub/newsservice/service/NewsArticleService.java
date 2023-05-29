package com.shevelyanchik.fitnessclub.newsservice.service;

import com.shevelyanchik.fitnessclub.newsservice.model.dto.NewsArticleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

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
     * Retrieves news articles from the NewsAPI based on the specified category, days offset, and country.
     *
     * @param category   The category of news articles to retrieve (business, entertainment, general, health, science, sports, technology).
     * @param daysOffset The number of days to offset the search by (e.g. 1 would retrieve news articles from yesterday).
     * @param country    The two-letter country code to limit the search to (e.g. "us" for United States).
     * @return A Flux stream of NewsArticleDto objects representing the retrieved news articles.
     */
    Flux<NewsArticleDto> findNewsFromNewsApiByCategory(String category, Long daysOffset, String country);

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
