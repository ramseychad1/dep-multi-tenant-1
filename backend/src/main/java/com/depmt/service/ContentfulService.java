package com.depmt.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Service
@Slf4j
public class ContentfulService {

    private final WebClient webClient;
    private final String spaceId;

    public ContentfulService(
            WebClient.Builder webClientBuilder,
            @Value("${app.external.contentful.api-key:}") String apiKey,
            @Value("${app.external.contentful.space-id:}") String spaceId) {
        this.spaceId = spaceId;
        this.webClient = webClientBuilder
                .baseUrl("https://api.contentful.com")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/vnd.contentful.management.v1+json")
                .build();
    }

    public Map<String, Object> getEntry(String entryId) {
        try {
            return webClient.get()
                    .uri("/spaces/{spaceId}/environments/master/entries/{entryId}", spaceId, entryId)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Failed to get Contentful entry {}: {}", entryId, e.getMessage());
            throw new RuntimeException("Failed to get entry from Contentful", e);
        }
    }

    public Map<String, Object> createEntry(String contentTypeId, Map<String, Object> fields) {
        try {
            return webClient.post()
                    .uri("/spaces/{spaceId}/environments/master/entries", spaceId)
                    .header("X-Contentful-Content-Type", contentTypeId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(Map.of("fields", fields))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Failed to create Contentful entry: {}", e.getMessage());
            throw new RuntimeException("Failed to create entry in Contentful", e);
        }
    }

    public Map<String, Object> updateEntry(String entryId, int version, Map<String, Object> fields) {
        try {
            return webClient.put()
                    .uri("/spaces/{spaceId}/environments/master/entries/{entryId}", spaceId, entryId)
                    .header("X-Contentful-Version", String.valueOf(version))
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(Map.of("fields", fields))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Failed to update Contentful entry {}: {}", entryId, e.getMessage());
            throw new RuntimeException("Failed to update entry in Contentful", e);
        }
    }

    public void publishEntry(String entryId, int version) {
        try {
            webClient.put()
                    .uri("/spaces/{spaceId}/environments/master/entries/{entryId}/published", spaceId, entryId)
                    .header("X-Contentful-Version", String.valueOf(version))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Failed to publish Contentful entry {}: {}", entryId, e.getMessage());
            throw new RuntimeException("Failed to publish entry in Contentful", e);
        }
    }

    public void updateProgramStatus(String entryId, String status) {
        try {
            Map<String, Object> entry = getEntry(entryId);
            Map<String, Object> sys = (Map<String, Object>) entry.get("sys");
            int version = (int) sys.get("version");

            Map<String, Object> fields = (Map<String, Object>) entry.get("fields");
            fields.put("status", Map.of("en-US", status));

            updateEntry(entryId, version, fields);
        } catch (Exception e) {
            log.error("Failed to update program status in Contentful: {}", e.getMessage());
            throw new RuntimeException("Failed to update program status in Contentful", e);
        }
    }
}
