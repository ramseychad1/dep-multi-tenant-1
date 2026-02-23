package com.depmt.controller;

import com.depmt.dto.EnrollmentProgramDto;
import com.depmt.dto.StatusUpdateDto;
import com.depmt.entity.EnrollmentProgram;
import com.depmt.service.EnrollmentProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/enrollment-programs")
@RequiredArgsConstructor
public class EnrollmentProgramController {

    private final EnrollmentProgramService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody EnrollmentProgramDto dto) {
        if (dto.getClient_name() == null || dto.getProgram_name() == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "VALIDATION_ERROR",
                    "message", "client_name and program_name are required"
            ));
        }
        EnrollmentProgram program = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(toSummaryMap(program));
    }

    @GetMapping
    public ResponseEntity<?> list() {
        List<EnrollmentProgram> programs = service.findAll();
        List<Map<String, Object>> data = programs.stream().map(this::toSummaryMap).toList();
        return ResponseEntity.ok(Map.of(
                "data", data,
                "total", programs.size(),
                "page", 1,
                "pageSize", programs.size()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return service.findById(id)
                .map(program -> ResponseEntity.ok(toDetailMap(program)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/public")
    public ResponseEntity<?> getByIdPublic(@PathVariable UUID id) {
        return service.findById(id)
                .map(program -> ResponseEntity.ok(toDetailMap(program)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody EnrollmentProgramDto dto) {
        return service.update(id, dto)
                .map(program -> ResponseEntity.ok(toSummaryMap(program)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        if (service.softDelete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id, @RequestBody StatusUpdateDto dto) {
        if (dto.getStatus() == null || (!dto.getStatus().equals("Active") && !dto.getStatus().equals("Inactive"))) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "VALIDATION_ERROR",
                    "message", "status must be 'Active' or 'Inactive'"
            ));
        }
        return service.updateStatus(id, dto.getStatus())
                .map(program -> ResponseEntity.ok(toSummaryMap(program)))
                .orElse(ResponseEntity.notFound().build());
    }

    private Map<String, Object> toSummaryMap(EnrollmentProgram p) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", p.getId());
        map.put("client_name", p.getClientName());
        map.put("program_name", p.getProgramName());
        map.put("status", p.getStatus());
        return map;
    }

    private Map<String, Object> toDetailMap(EnrollmentProgram p) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", p.getId());
        map.put("client_name", p.getClientName());
        map.put("program_name", p.getProgramName());
        map.put("program_description", p.getProgramDescription());
        map.put("workflow_types", p.getWorkflowTypes());
        map.put("form_schema", p.getFormSchema());
        map.put("logo_url", p.getLogoUrl());
        map.put("primary_color", p.getPrimaryColor());
        map.put("secondary_color", p.getSecondaryColor());
        map.put("accent_color", p.getAccentColor());
        map.put("header_background_color", p.getHeaderBackgroundColor());
        map.put("text_color", p.getTextColor());
        map.put("font_preference", p.getFontPreference());
        map.put("status", p.getStatus());
        return map;
    }
}
