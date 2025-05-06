package com.project.frontend.comprasSystem.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

public class ImageService {
    private final String API_URL = "http://localhost:8080/api/images?query=";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ImageService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public String getImageUrl(String query) {
        try {
            String response = restTemplate.getForObject(API_URL + query, String.class);
            JsonNode json = objectMapper.readTree(response);
            if (json.has("status") && json.get("status").asText().equals("success")) {
                return json.get("imageUrl").asText();
            }
        } catch (Exception e) {
            // Ignorar, se usará imagen por defecto
        }
        return null;
    }
} 