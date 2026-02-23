CREATE TABLE enrollment_programs (
    id                      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_name             TEXT NOT NULL,
    program_name            TEXT NOT NULL,
    program_description     TEXT,
    workflow_types          TEXT[],
    form_schema             JSONB,
    logo_url                TEXT,
    primary_color           TEXT,
    secondary_color         TEXT,
    accent_color            TEXT,
    header_background_color TEXT,
    text_color              TEXT,
    font_preference         TEXT,
    status                  TEXT NOT NULL DEFAULT 'Active',
    created_at              TIMESTAMP NOT NULL DEFAULT now(),
    updated_at              TIMESTAMP NOT NULL DEFAULT now(),
    deleted_at              TIMESTAMP
);
