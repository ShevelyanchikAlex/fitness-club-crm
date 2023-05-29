package com.shevelyanchik.fitnessclub.newsservice.persistence;

import com.shevelyanchik.fitnessclub.newsservice.model.entity.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {
}
