package com.shevelyanchik.fitnessclub.newsservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shevelyanchik.fitnessclub.newsservice.exception.EntityNotFoundException;
import com.shevelyanchik.fitnessclub.newsservice.exception.ValidationException;
import com.shevelyanchik.fitnessclub.newsservice.model.dto.NewsApiResponse;
import com.shevelyanchik.fitnessclub.newsservice.model.dto.NewsArticleDto;
import com.shevelyanchik.fitnessclub.newsservice.model.entity.NewsArticle;
import com.shevelyanchik.fitnessclub.newsservice.model.mapper.NewsApiArticleMapper;
import com.shevelyanchik.fitnessclub.newsservice.model.mapper.NewsArticleMapper;
import com.shevelyanchik.fitnessclub.newsservice.persistence.NewsArticleRepository;
import com.shevelyanchik.fitnessclub.newsservice.service.NewsArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NewsArticleServiceImpl implements NewsArticleService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final NewsArticleRepository newsArticleRepository;
    private final NewsArticleMapper newsArticleMapper;
    private final NewsApiArticleMapper newsApiArticleMapper;
    private final WebClient webClient;

    @Value("${news-api.url}")
    private String newsApiUrl;
    @Value("${news-api.api-key}")
    private String newsApiKey;


    @Autowired
    public NewsArticleServiceImpl(
            NewsArticleRepository newsArticleRepository,
            NewsArticleMapper newsArticleMapper,
            NewsApiArticleMapper newsApiArticleMapper,
            WebClient.Builder webClientBuilder) {
        this.newsArticleRepository = newsArticleRepository;
        this.newsArticleMapper = newsArticleMapper;
        this.newsApiArticleMapper = newsApiArticleMapper;
        this.webClient = webClientBuilder.build();
    }

    @Override
    @Transactional
    public NewsArticleDto createNewsArticle(NewsArticleDto newsArticleDto) {
        NewsArticle newsArticle = newsArticleMapper.toEntity(newsArticleDto);
        NewsArticle savedNewsArticle = newsArticleRepository.save(newsArticle);
        return newsArticleMapper.toDto(savedNewsArticle);
    }

    @Override
    @Transactional
    public NewsArticleDto updateNewsArticle(NewsArticleDto updatedNewsArticleDto) {
        NewsArticleDto actualNewsArticleDto = findNewsArticleById(updatedNewsArticleDto.getId());
        actualNewsArticleDto.setTitle(updatedNewsArticleDto.getTitle());
        actualNewsArticleDto.setAuthor(updatedNewsArticleDto.getAuthor());
        actualNewsArticleDto.setContent(updatedNewsArticleDto.getContent());
        actualNewsArticleDto.setImageUrl(updatedNewsArticleDto.getImageUrl());
        NewsArticle preUpdatedNewsArticle = newsArticleMapper.toEntity(actualNewsArticleDto);
        NewsArticle updatedNewsArticle = newsArticleRepository.save(preUpdatedNewsArticle);
        return newsArticleMapper.toDto(updatedNewsArticle);
    }

    @Override
    @Transactional(readOnly = true)
    public NewsArticleDto findNewsArticleById(Long id) {
        return newsArticleRepository.findById(id)
                .map(newsArticleMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("News Article not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NewsArticleDto> findAllNewsArticles(Pageable pageable) {
        List<NewsArticleDto> newsArticleDtoList = newsArticleRepository.findAll(pageable).stream()
                .map(newsArticleMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(newsArticleDtoList, pageable, newsArticleRepository.count());
    }

    @Override
    public Flux<NewsArticleDto> findNewsFromNewsApiByCategory(String category, Long daysOffset, String country) {
        LocalDate startDate = LocalDate.now().minusDays(daysOffset);
        String formattedDate = startDate.format(DATE_FORMATTER);

        return webClient.get()
                .uri(newsApiUrl, newsApiKey, category, formattedDate, country)
                .retrieve()
                .bodyToMono(NewsApiResponse.class)
                .flatMapIterable(NewsApiResponse::getArticles)
                .map(newsApiArticleMapper::toNewsArticleDto)
                .switchIfEmpty(Flux.just(new NewsArticleDto()))
                .onErrorMap(JsonProcessingException.class, ex -> new ValidationException("Invalid response from NewsAPI"));
    }

    @Override
    @Transactional(readOnly = true)
    public Long countNewsArticles() {
        return newsArticleRepository.count();
    }

    @Override
    @Transactional
    public void deleteNewsArticleById(Long id) {
        if (!newsArticleRepository.existsById(id)) {
            throw new EntityNotFoundException("News Article not found with id: " + id);
        }
        newsArticleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllServices() {
        newsArticleRepository.deleteAll();
    }

}
