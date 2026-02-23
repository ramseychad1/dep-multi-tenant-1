package com.depmt.controller;

import com.depmt.dto.ErrorResponse;
import com.depmt.dto.FormSubmissionDto;
import com.depmt.entity.EnrollmentProgram;
import com.depmt.entity.FormSubmission;
import com.depmt.repository.EnrollmentProgramRepository;
import com.depmt.repository.FormSubmissionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FormSubmissionController {

    private final FormSubmissionRepository formSubmissionRepository;
    private final EnrollmentProgramRepository enrollmentProgramRepository;
    private final ObjectMapper objectMapper;

    @PostMapping("/form-submissions")
    public ResponseEntity<?> createSubmission(@Valid @RequestBody FormSubmissionDto dto) {
        EnrollmentProgram program = enrollmentProgramRepository
                .findByIdAndDeletedAtIsNull(dto.getEnrollmentProgramId())
                .orElse(null);

        if (program == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT_FOUND", "Enrollment program not found"));
        }

        if (!"Active".equals(program.getStatus())) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("PROGRAM_INACTIVE", "This enrollment program is not active"));
        }

        try {
            FormSubmission submission = new FormSubmission();
            submission.setEnrollmentProgramId(dto.getEnrollmentProgramId());
            submission.setSubmissionData(objectMapper.writeValueAsString(dto.getSubmissionData()));

            FormSubmission saved = formSubmissionRepository.save(submission);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("id", saved.getId());
            response.put("enrollment_program_id", saved.getEnrollmentProgramId());
            response.put("submitted_at", saved.getSubmittedAt());
            response.put("initial_status", saved.getInitialStatus());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("INTERNAL_ERROR", "Failed to create submission"));
        }
    }

    @GetMapping("/enrollment-programs/{enrollmentProgramId}/form-submissions")
    public ResponseEntity<?> getSubmissions(@PathVariable UUID enrollmentProgramId) {
        List<FormSubmission> submissions = formSubmissionRepository
                .findByEnrollmentProgramIdAndDeletedAtIsNull(enrollmentProgramId);

        List<Map<String, Object>> data = submissions.stream().map(s -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("id", s.getId());
            m.put("submitted_at", s.getSubmittedAt());
            m.put("initial_status", s.getInitialStatus());
            return m;
        }).toList();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("data", data);
        response.put("total", data.size());
        response.put("page", 1);
        response.put("pageSize", data.size());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/form-submissions/{id}")
    public ResponseEntity<?> getSubmission(@PathVariable UUID id) {
        return formSubmissionRepository.findById(id)
                .map(s -> {
                    Map<String, Object> response = new LinkedHashMap<>();
                    response.put("id", s.getId());
                    response.put("enrollment_program_id", s.getEnrollmentProgramId());
                    response.put("submission_data", s.getSubmissionData());
                    response.put("submitted_at", s.getSubmittedAt());
                    response.put("initial_status", s.getInitialStatus());
                    return ResponseEntity.ok((Object) response);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
