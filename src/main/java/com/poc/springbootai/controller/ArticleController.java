package com.poc.springbootai.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.poc.springbootai.domain.Article;
import com.poc.springbootai.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/articles")
    public Article generateArticle(@RequestParam String seedText) throws JsonProcessingException {
        return articleService.generateAndSaveArticle(seedText);
    }

    @GetMapping("/articles")
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }
}
