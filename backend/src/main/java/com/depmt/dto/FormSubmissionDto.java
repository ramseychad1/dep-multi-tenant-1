package com.depmt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class FormSubmissionDto {
    @NotNull
    @JsonProperty("enrollment_program_id")
    private UUID enrollmentProgramId;

    @NotNull
    @JsonProperty("submission_data")
    private Object submissionData;
}
