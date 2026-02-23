package com.depmt.service;

import com.depmt.dto.EnrollmentProgramDto;
import com.depmt.entity.EnrollmentProgram;
import com.depmt.repository.EnrollmentProgramRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentProgramService {

    private final EnrollmentProgramRepository repository;
    private final ObjectMapper objectMapper;

    public List<EnrollmentProgram> findAll() {
        return repository.findByDeletedAtIsNull();
    }

    public Optional<EnrollmentProgram> findById(UUID id) {
        return repository.findByIdAndDeletedAtIsNull(id);
    }

    public EnrollmentProgram create(EnrollmentProgramDto dto) {
        EnrollmentProgram program = new EnrollmentProgram();
        program.setClientName(dto.getClient_name());
        program.setProgramName(dto.getProgram_name());
        program.setProgramDescription(dto.getProgram_description());
        program.setWorkflowTypes(dto.getWorkflow_types());
        program.setFormSchema(toJsonString(dto.getForm_schema()));
        program.setLogoUrl(dto.getLogo_url());
        program.setPrimaryColor(dto.getPrimary_color());
        program.setSecondaryColor(dto.getSecondary_color());
        program.setAccentColor(dto.getAccent_color());
        program.setHeaderBackgroundColor(dto.getHeader_background_color());
        program.setTextColor(dto.getText_color());
        program.setFontPreference(dto.getFont_preference());
        program.setStatus("Active");
        return repository.save(program);
    }

    public Optional<EnrollmentProgram> update(UUID id, EnrollmentProgramDto dto) {
        return repository.findByIdAndDeletedAtIsNull(id).map(program -> {
            if (dto.getClient_name() != null) program.setClientName(dto.getClient_name());
            if (dto.getProgram_name() != null) program.setProgramName(dto.getProgram_name());
            if (dto.getProgram_description() != null) program.setProgramDescription(dto.getProgram_description());
            if (dto.getWorkflow_types() != null) program.setWorkflowTypes(dto.getWorkflow_types());
            if (dto.getForm_schema() != null) program.setFormSchema(toJsonString(dto.getForm_schema()));
            if (dto.getLogo_url() != null) program.setLogoUrl(dto.getLogo_url());
            if (dto.getPrimary_color() != null) program.setPrimaryColor(dto.getPrimary_color());
            if (dto.getSecondary_color() != null) program.setSecondaryColor(dto.getSecondary_color());
            if (dto.getAccent_color() != null) program.setAccentColor(dto.getAccent_color());
            if (dto.getHeader_background_color() != null) program.setHeaderBackgroundColor(dto.getHeader_background_color());
            if (dto.getText_color() != null) program.setTextColor(dto.getText_color());
            if (dto.getFont_preference() != null) program.setFontPreference(dto.getFont_preference());
            if (dto.getStatus() != null) program.setStatus(dto.getStatus());
            return repository.save(program);
        });
    }

    public Optional<EnrollmentProgram> updateStatus(UUID id, String status) {
        return repository.findByIdAndDeletedAtIsNull(id).map(program -> {
            program.setStatus(status);
            return repository.save(program);
        });
    }

    public boolean softDelete(UUID id) {
        return repository.findByIdAndDeletedAtIsNull(id).map(program -> {
            program.setDeletedAt(LocalDateTime.now());
            repository.save(program);
            return true;
        }).orElse(false);
    }

    private String toJsonString(Object obj) {
        if (obj == null) return null;
        if (obj instanceof String) return (String) obj;
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("Failed to serialize form schema: {}", e.getMessage());
            return obj.toString();
        }
    }
}
