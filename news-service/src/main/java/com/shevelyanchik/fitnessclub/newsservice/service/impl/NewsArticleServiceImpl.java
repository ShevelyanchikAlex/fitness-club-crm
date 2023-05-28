package com.shevelyanchik.fitnessclub.newsservice.service.impl;

import com.shevelyanchik.fitnessclub.newsservice.exception.EntityNotFoundException;
import com.shevelyanchik.fitnessclub.newsservice.model.dto.NewsArticleDto;
import com.shevelyanchik.fitnessclub.newsservice.model.entity.NewsArticle;
import com.shevelyanchik.fitnessclub.newsservice.model.mapper.NewsArticleMapper;
import com.shevelyanchik.fitnessclub.newsservice.persistence.NewsArticleRepository;
import com.shevelyanchik.fitnessclub.newsservice.service.NewsArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsArticleServiceImpl implements NewsArticleService {

    private final NewsArticleRepository newsArticleRepository;
    private final NewsArticleMapper newsArticleMapper;

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
