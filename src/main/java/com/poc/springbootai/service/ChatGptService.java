package com.poc.springbootai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.springbootai.domain.ChatResponse;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class ChatGptService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${chatgpt.api.url}")
    private String apiUrl;

    @Value("${chatgpt.api.key}")
    private String apiKey;

    @Value("${chatgpt.model}")
    private String model;

    @Autowired
    public ChatGptService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    // PostConstruct is called after the properties are injected
    @PostConstruct
    public void init() {
        // Now the properties are available for use
        log.info("ChatGPT API URL: {}", apiUrl);
        log.info("ChatGPT API Key: {}", apiKey);
        log.info("ChatGPT Model: {}", model);
    }


    public Mono<ChatResponse> generateTitle() throws JsonProcessingException {
        if (Objects.isNull(apiKey) || apiKey.isBlank()) {
            return Mono.error(new IllegalArgumentException("API key is not set or is blank."));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", List.of(
                Map.of("role", "user", "content", "Generate a title")
        ));

        log.info("Request Body: {}", objectMapper.writeValueAsString(requestBody));

        // Use WebClient to send the request asynchronously
        return webClient.post()
                .uri(apiUrl) // The URI is already set in the WebClient builder with apiUrl
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(responseBody -> {
                    try {
                        ChatResponse chatResponse = parseChatResponse(responseBody);
                        return Mono.just(chatResponse);
                    } catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException("Failed to parse chat response", e));
                    }
                });
    }

    public ChatResponse parseChatResponse(String jsonResponse) throws JsonProcessingException {
        return objectMapper.readValue(jsonResponse, ChatResponse.class);
    }
}
