package com.poc.springbootai.repository;

import com.poc.springbootai.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    // Custom query methods can be defined here
}
