package com.poc.springbootai.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatResponse {
    private String id;
    private String object;
    private Long created;
    private String model;
    private List<Choice> choices;
    private Usage usage;
    private String systemFingerprint;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Choice {
        private int index;
        private Message message;
        private String logprobs;
        private String finishReason;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Message {
            private String role;
            private String content;
            private String refusal;
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Usage {
        private int promptTokens;
        private int completionTokens;
        private int totalTokens;

        @JsonProperty("prompt_tokens_details")
        private TokenDetails promptTokensDetails;

        @JsonProperty("completion_tokens_details")
        private TokenDetails completionTokensDetails;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class TokenDetails {
            private int cachedTokens;
            private int audioTokens;
            private int reasoningTokens;
            private int acceptedPredictionTokens;
            private int rejectedPredictionTokens;
        }
    }
}


