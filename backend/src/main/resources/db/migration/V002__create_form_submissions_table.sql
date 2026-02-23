CREATE TABLE form_submissions (
    id                      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    enrollment_program_id   UUID NOT NULL REFERENCES enrollment_programs(id),
    submission_data         JSONB NOT NULL,
    submitted_at            TIMESTAMP NOT NULL DEFAULT now(),
    initial_status          TEXT,
    created_at              TIMESTAMP NOT NULL DEFAULT now(),
    updated_at              TIMESTAMP NOT NULL DEFAULT now(),
    deleted_at              TIMESTAMP
);

CREATE INDEX idx_form_submissions_enrollment_program_id ON form_submissions(enrollment_program_id);
