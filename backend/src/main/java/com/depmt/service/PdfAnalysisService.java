package com.depmt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class PdfAnalysisService {

    @Value("${app.ai.claude.api-key:#{null}}")
    private String claudeApiKey;

    @Value("${app.ai.gemini.api-key:#{null}}")
    private String geminiApiKey;

    private final WebClient.Builder webClientBuilder;

    public String analyzeWithAi(MultipartFile file, String aiProvider) throws IOException {
        List<String> base64Images = convertPdfToImages(file);

        return switch (aiProvider.toLowerCase()) {
            case "claude" -> callClaudeApi(base64Images);
            case "gemini" -> callGeminiApi(base64Images);
            default -> throw new IllegalArgumentException("Unsupported AI provider: " + aiProvider);
        };
    }

    List<String> convertPdfToImages(MultipartFile file) throws IOException {
        List<String> base64Images = new ArrayList<>();

        try (PDDocument document = Loader.loadPDF(file.getBytes())) {
            PDFRenderer renderer = new PDFRenderer(document);

            for (int page = 0; page < document.getNumberOfPages(); page++) {
                BufferedImage image = renderer.renderImageWithDPI(page, 200);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                String base64 = Base64.getEncoder().encodeToString(baos.toByteArray());
                base64Images.add(base64);
            }
        }

        return base64Images;
    }

    private String callClaudeApi(List<String> base64Images) {
        WebClient client = webClientBuilder.baseUrl("https://api.anthropic.com").build();

        List<Map<String, Object>> contentBlocks = new ArrayList<>();
        for (String img : base64Images) {
            contentBlocks.add(Map.of(
                    "type", "image",
                    "source", Map.of(
                            "type", "base64",
                            "media_type", "image/png",
                            "data", img
                    )
            ));
        }
        contentBlocks.add(Map.of(
                "type", "text",
                "text", getSchemaGenerationPrompt()
        ));

        Map<String, Object> requestBody = Map.of(
                "model", "claude-sonnet-4-20250514",
                "max_tokens", 4096,
                "messages", List.of(Map.of(
                        "role", "user",
                        "content", contentBlocks
                ))
        );

        try {
            Map response = client.post()
                    .uri("/v1/messages")
                    .header("x-api-key", claudeApiKey)
                    .header("anthropic-version", "2023-06-01")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response != null && response.containsKey("content")) {
                List<Map<String, Object>> content = (List<Map<String, Object>>) response.get("content");
                for (Map<String, Object> block : content) {
                    if ("text".equals(block.get("type"))) {
                        return extractJsonFromResponse((String) block.get("text"));
                    }
                }
            }
            throw new RuntimeException("No text content in Claude response");
        } catch (Exception e) {
            log.error("Claude API call failed", e);
            throw new RuntimeException("AI_SERVICE_ERROR: Failed to generate schema from Claude", e);
        }
    }

    private String callGeminiApi(List<String> base64Images) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + geminiApiKey;
        WebClient client = webClientBuilder.build();

        List<Map<String, Object>> parts = new ArrayList<>();
        for (String img : base64Images) {
            parts.add(Map.of(
                    "inline_data", Map.of(
                            "mime_type", "image/png",
                            "data", img
                    )
            ));
        }
        parts.add(Map.of("text", getSchemaGenerationPrompt()));

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(Map.of("parts", parts))
        );

        try {
            Map response = client.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response != null && response.containsKey("candidates")) {
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
                if (!candidates.isEmpty()) {
                    Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
                    List<Map<String, Object>> responseParts = (List<Map<String, Object>>) content.get("parts");
                    for (Map<String, Object> part : responseParts) {
                        if (part.containsKey("text")) {
                            return extractJsonFromResponse((String) part.get("text"));
                        }
                    }
                }
            }
            throw new RuntimeException("No text content in Gemini response");
        } catch (Exception e) {
            log.error("Gemini API call failed", e);
            throw new RuntimeException("AI_SERVICE_ERROR: Failed to generate schema from Gemini", e);
        }
    }

    private String getSchemaGenerationPrompt() {
        return """
                Analyze this enrollment form PDF and generate a JSON schema that represents the form's structure.

                The schema should be a JSON object with a "sections" array, where each section has:
                - "title": the section heading
                - "fields": an array of field objects, each with:
                  - "name": a camelCase field identifier
                  - "label": the human-readable label from the form
                  - "type": one of "text", "email", "phone", "date", "select", "checkbox", "radio", "textarea", "number", "ssn", "address"
                  - "required": boolean indicating if the field appears required
                  - "options": (for select/radio/checkbox) array of option strings
                  - "placeholder": suggested placeholder text

                Return ONLY valid JSON, no markdown formatting or explanation.
                """;
    }

    private String extractJsonFromResponse(String text) {
        String trimmed = text.trim();
        if (trimmed.startsWith("```json")) {
            trimmed = trimmed.substring(7);
        } else if (trimmed.startsWith("```")) {
            trimmed = trimmed.substring(3);
        }
        if (trimmed.endsWith("```")) {
            trimmed = trimmed.substring(0, trimmed.length() - 3);
        }
        return trimmed.trim();
    }
}
