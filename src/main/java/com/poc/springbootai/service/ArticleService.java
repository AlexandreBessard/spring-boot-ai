package com.poc.springbootai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.poc.springbootai.domain.Article;
import com.poc.springbootai.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ChatGptService chatGptService;

    @Autowired
    public ArticleService(ArticleRepository articleRepository,
                          ChatGptService chatGptService) {
        this.articleRepository = articleRepository;
        this.chatGptService = chatGptService;
    }

    public Article generateAndSaveArticle(String seedText) throws JsonProcessingException {
        String generatedContent = chatGptService.generateResponse(seedText);
        String generatedTitle = "Generated Title: " + seedText.substring(0, Math.min(10, seedText.length())) + "...";
        Article article = new Article(generatedTitle, generatedContent);
        return articleRepository.save(article);
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

}
