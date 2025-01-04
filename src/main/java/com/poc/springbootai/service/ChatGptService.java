package com.poc.springbootai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.springbootai.domain.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class ChatGptService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${chatgpt.api.url}")
    private String apiUrl;

    @Value("${chatgpt.api.key}")
    private String apiKey;

    @Value("${chatgpt.model}")
    private String model;

    @Autowired
    public ChatGptService(RestTemplate restTemplate,
                          ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public ChatResponse generateTitle() throws JsonProcessingException {
        if (Objects.isNull(apiKey) || apiKey.isBlank()) {
            throw new IllegalArgumentException("API key is not set or is blank.");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", List.of(
                Map.of("role", "user", "content", "Generate a title")
        ));
        log.info("Request Body: {}", new ObjectMapper().writeValueAsString(requestBody));
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Error: " + response.getBody());
            }
            return parseChatResponse(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Failed to call OpenAI API: " + e.getMessage(), e);
        }
    }

    public ChatResponse parseChatResponse(String jsonResponse) throws Exception {
        return objectMapper.readValue(jsonResponse, ChatResponse.class);
    }
}
