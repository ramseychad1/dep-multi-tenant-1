# Data Model
**Project:** DEP Multi-Tenant
**Database:** PostgreSQL

### 2. Entity Overview

| Entity             | Description                                                                                                   | Key Relationships                                   |
| ------------------ | ------------------------------------------------------------------------------------------------------------- | --------------------------------------------------- |
| EnrollmentProgram  | Represents an enrollment program configured by admins, containing details, branding, and the form schema.    | One-to-many with FormSubmission                   |
| FormSubmission     | Represents a single submission of an enrollment form by a patient or provider.                               | One-to-one with EnrollmentProgram                 |

### 3. Entity Definitions

#### EnrollmentProgram

| Column             | Type      | Constraints | Description                                                                                                |
| ------------------ | --------- | ----------- | ---------------------------------------------------------------------------------------------------------- |
| id                 | UUID      | PRIMARY KEY | Unique identifier for the enrollment program.                                                              |
| client_name        | TEXT      | NOT NULL    | The name of the healthcare client associated with this program.                                           |
| program_name       | TEXT      | NOT NULL    | The name of the enrollment program.                                                                        |
| program_description| TEXT      |             | A description of the enrollment program.                                                                   |
| workflow_types     | TEXT[]    |             | An array of workflow types supported by the program (e.g., 'patient', 'provider').                         |
| form_schema        | JSONB     |             | The JSON schema representing the structure and fields of the enrollment form.                               |
| logo_url           | TEXT      |             | URL of the client's logo.                                                                                 |
| primary_color      | TEXT      |             | The primary branding color for the enrollment portal.                                                      |
| secondary_color    | TEXT      |             | The secondary branding color for the enrollment portal.                                                    |
| accent_color       | TEXT      |             | The accent color for the enrollment portal.                                                                |
| header_background_color | TEXT | | The background color for the header of the enrollment portal |
| text_color | TEXT | | The text color for the enrollment portal |
| font_preference | TEXT | | Font preference for the enrollment portal |
| status             | TEXT      | NOT NULL    | The status of the enrollment program ('Active', 'Inactive').                                               |
| created_at         | TIMESTAMP | NOT NULL    | Timestamp indicating when the enrollment program was created.                                              |
| updated_at         | TIMESTAMP | NOT NULL    | Timestamp indicating when the enrollment program was last updated.                                         |
| deleted_at         | TIMESTAMP |             | Timestamp indicating when the enrollment program was soft deleted (NULL if not deleted).                    |

The `EnrollmentProgram` entity stores the configuration details for an enrollment portal.
It captures program details, branding information, the generated form schema, and status.
The `workflow_types` field specifies which types of enrollment (patient, provider, or both) are supported.

#### FormSubmission

| Column         | Type      | Constraints | Description                                                                                                 |
| -------------- | --------- | ----------- | ----------------------------------------------------------------------------------------------------------- |
| id             | UUID      | PRIMARY KEY | Unique identifier for the form submission.                                                                |
| enrollment_program_id | UUID      | NOT NULL    | Foreign key referencing the `EnrollmentProgram` to which this submission belongs.                       |
| submission_data| JSONB     | NOT NULL    | The data submitted through the enrollment form, stored as a JSON payload.                                   |
| submitted_at   | TIMESTAMP | NOT NULL    | Timestamp indicating when the form was submitted.                                                            |
| initial_status | TEXT      |             | The initial status of the form submission.                                                                  |
| created_at     | TIMESTAMP | NOT NULL    | Timestamp indicating when the form submission record was created.                                             |
| updated_at     | TIMESTAMP | NOT NULL    | Timestamp indicating when the form submission record was last updated.                                        |
| deleted_at     | TIMESTAMP |             | Timestamp indicating when the form submission record was soft deleted (NULL if not deleted).                 |

The `FormSubmission` entity stores the data submitted through a public-facing enrollment form.
It captures the program ID, the submitted data as a JSON payload, and a timestamp of when the submission occurred.
The `enrollment_program_id` links the submission to its corresponding `EnrollmentProgram`.

### 4. Relationships

- **EnrollmentProgram → FormSubmission** — one-to-many — One enrollment program can have multiple form submissions.
  This relationship represents the fact that many users (patients or providers) can submit forms for a single enrollment program.

### 5. Indexes

- **form_submission(enrollment_program_id)** — Used for querying submissions associated with a specific enrollment program, which is needed for reporting and program management features.

### 6. Data Security & Compliance

- **PII Fields:** The `submission_data` column in the `FormSubmission` table may contain PII, depending on the fields defined in the enrollment form schema.
- **Sensitive Data Handling:** The `submission_data` column should be encrypted at rest and in transit due to the potential for PII. Access to this data should be restricted to authorized personnel only.
- **Compliance Requirements:** HIPAA compliance may be required depending on the specific data collected in the enrollment forms. Ensure appropriate safeguards are in place to protect patient data.
- **Audit Trail:** The `EnrollmentProgram` entity requires audit logging for creation, modification, and deletion events, as these actions are performed by administrators and impact the configuration of the enrollment portals.
