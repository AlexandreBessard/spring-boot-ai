package com.poc.springbootai.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.poc.springbootai.domain.Article;
import com.poc.springbootai.domain.ChatResponse;
import com.poc.springbootai.repository.ArticleRepository;
import com.poc.springbootai.service.ChatGptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatGptService chatGptService;
    private final ArticleRepository articleRepository;

    @Autowired
    public ChatController(ChatGptService chatGptService,
                          ArticleRepository articleRepository) {
        this.chatGptService = chatGptService;
        this.articleRepository = articleRepository;
    }

    @GetMapping("/generateTitle")
    public String generateTitle() throws JsonProcessingException {
        ChatResponse response = chatGptService.generateTitle();
        String title = response.getChoices().getFirst().getMessage().getContent();
        Article article = new Article(title, null);
        articleRepository.save(article);
        return article.getTitle();
    }
}
