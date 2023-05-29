package com.shevelyanchik.fitnessclub.newsservice.controller;

import com.shevelyanchik.fitnessclub.newsservice.model.dto.NewsArticleDto;
import com.shevelyanchik.fitnessclub.newsservice.service.NewsArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/news-service/news")
public class NewsArticleController {

    private final NewsArticleService newsArticleService;

    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    @PostMapping("/create")
    public ResponseEntity<NewsArticleDto> createNewsArticle(@Valid @RequestBody NewsArticleDto newsArticleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(newsArticleService.createNewsArticle(newsArticleDto));
    }

    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    @PatchMapping("/update")
    public ResponseEntity<NewsArticleDto> updateNewsArticle(@Valid @RequestBody NewsArticleDto updatedNewsArticle) {
        return ResponseEntity.ok(newsArticleService.updateNewsArticle(updatedNewsArticle));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public ResponseEntity<List<NewsArticleDto>> findAllNewsArticles(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<NewsArticleDto> newsArticleDtoPage = newsArticleService.findAllNewsArticles(PageRequest.of(page, size));
        return ResponseEntity.ok(newsArticleDtoPage.getContent());
    }

    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    @GetMapping("/news-api")
    public Flux<NewsArticleDto> findNewsFromNewsApiByCategory(
            @RequestParam(name = "category", defaultValue = "health") String category,
            @RequestParam(name = "daysOffset", defaultValue = "20") Long daysOffset,
            @RequestParam(name = "country", defaultValue = "us") String country) {
        return newsArticleService.findNewsFromNewsApiByCategory(category, daysOffset, country);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public ResponseEntity<NewsArticleDto> findNewsArticleDtoById(@PathVariable Long id) {
        return ResponseEntity.ok(newsArticleService.findNewsArticleById(id));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/count")
    public ResponseEntity<Long> countNewsArticles() {
        return ResponseEntity.ok(newsArticleService.countNewsArticles());
    }

    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    @DeleteMapping("/{id}")
    public void deleteNewsArticleById(@PathVariable Long id) {
        newsArticleService.deleteNewsArticleById(id);
    }

}
