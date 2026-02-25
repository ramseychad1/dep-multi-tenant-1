package com.depmt.controller;

import com.depmt.dto.ErrorResponse;
import com.depmt.service.PdfAnalysisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/pdf-analysis")
@RequiredArgsConstructor
public class PdfAnalysisController {

    private final PdfAnalysisService pdfAnalysisService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<?> analyzePdf(
            @RequestParam("file") MultipartFile file,
            @RequestParam("aiProvider") String aiProvider) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("BAD_REQUEST", "PDF file is required"));
            }

            if (!"application/pdf".equals(file.getContentType())) {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("BAD_REQUEST", "File must be a PDF"));
            }

            String schema = pdfAnalysisService.analyzeWithAi(file, aiProvider);
            // Parse to object so Spring/Jackson serializes valid JSON
            Object schemaObj = objectMapper.readValue(schema, Object.class);
            return ResponseEntity.ok(schemaObj);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("BAD_REQUEST", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("AI_SERVICE_ERROR", "Failed to analyze PDF: " + e.getMessage()));
        }
    }
}
