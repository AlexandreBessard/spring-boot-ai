package com.poc.springbootai.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.poc.springbootai.domain.ChatRequest;
import com.poc.springbootai.domain.ChatResponse;
import com.poc.springbootai.service.ChatGptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ChatController {

    private final ChatGptService chatGptService;

    @Autowired
    public ChatController(ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String prompt) throws JsonProcessingException {
        return chatGptService.generateResponse(prompt);
    }
}
