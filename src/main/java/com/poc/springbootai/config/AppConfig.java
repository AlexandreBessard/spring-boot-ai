package com.poc.springbootai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    // Used for making HTTP to send requests to the API.
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
