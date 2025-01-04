package com.poc.springbootai.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ChatRequest {

    private String model;
    private List<Message> messages;

    // Getters and setters
    @Data
    @AllArgsConstructor
    public static class Message {
        private String role;
        private String content;
    }
}


