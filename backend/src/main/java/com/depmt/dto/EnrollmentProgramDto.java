package com.depmt.dto;

import lombok.Data;

@Data
public class EnrollmentProgramDto {
    private String client_name;
    private String program_name;
    private String program_description;
    private String[] workflow_types;
    private Object form_schema;
    private String logo_url;
    private String primary_color;
    private String secondary_color;
    private String accent_color;
    private String header_background_color;
    private String text_color;
    private String font_preference;
    private String status;
}
