# API Contract
**Project:** DEP Multi-Tenant
**API Style:** REST
**Auth:** OAuth 2.0 / SSO

## 2. API Overview

The HealthBridge DEP Multi-Tenant API follows RESTful principles, using nouns for resources and standard HTTP methods. The API base URL will be `/api/v1/`, adhering to the global versioning guidelines, and all endpoints except authentication will require OAuth 2.0 / SSO authentication. The API leverages Spring Boot for controllers, services for business logic, and Spring Data JPA for data access, mirroring the architecture overview. Error responses adhere to a consistent shape, and paginated list endpoints include metadata.

## 3. Authentication Endpoints

### 3.1 POST /api/v1/auth/login
This endpoint authenticates a Client Services Admin and returns an authentication token.
- Request
    - username: string, required
    - password: string, required
- Response
    - access_token: string
    - token_type: string (e.g., "Bearer")
- Errors: 400 (Bad Request), 401 (Unauthorized), 500 (Internal Server Error)

## 4. Core Resource Endpoints

### 4.1 Enrollment Programs

#### 4.1.1 POST /api/v1/enrollment-programs
Creates a new enrollment program.
- Request
    - client_name: string, required
    - program_name: string, required
    - program_description: string, optional
    - workflow_types: string[], required (e.g., ["patient", "provider"])
    - form_schema: JSON, required
    - logo_url: string, optional
    - primary_color: string, optional
    - secondary_color: string, optional
    - accent_color: string, optional
    - header_background_color: string, optional
    - text_color: string, optional
    - font_preference: string, optional
- Response
    - id: UUID
    - client_name: string
    - program_name: string
    - status: string ("Active" or "Inactive")
- Business Rules
    - The `workflow_types` array must contain at least one of "patient" or "provider".
    - The `form_schema` must be a valid JSON schema.
    - Only users with the "admin" role can access this endpoint.
- Errors: 400 (Bad Request), 401 (Unauthorized), 403 (Forbidden), 500 (Internal Server Error)

#### 4.1.2 GET /api/v1/enrollment-programs
Retrieves a list of enrollment programs.
- Request: None
- Response
    - data: array of EnrollmentProgram objects (id, client_name, program_name, status)
    - total: integer (total number of enrollment programs)
    - page: integer (current page number)
    - pageSize: integer (number of programs per page)
- Business Rules: Only users with the "admin" role can access this endpoint.
- Errors: 401 (Unauthorized), 403 (Forbidden), 500 (Internal Server Error)

#### 4.1.3 GET /api/v1/enrollment-programs/{id}
Retrieves a specific enrollment program by ID.
- Request: None
- Response
    - id: UUID
    - client_name: string
    - program_name: string
    - program_description: string
    - workflow_types: string[]
    - form_schema: JSON
    - logo_url: string
    - primary_color: string
    - secondary_color: string
    - accent_color: string
    - header_background_color: string
    - text_color: string
    - font_preference: string
    - status: string ("Active" or "Inactive")
- Business Rules: Only users with the "admin" role can access this endpoint.
- Errors: 401 (Unauthorized), 403 (Forbidden), 404 (Not Found), 500 (Internal Server Error)

#### 4.1.4 PUT /api/v1/enrollment-programs/{id}
Updates an existing enrollment program.
- Request
    - client_name: string, optional
    - program_name: string, optional
    - program_description: string, optional
    - workflow_types: string[], optional (e.g., ["patient", "provider"])
    - form_schema: JSON, optional
    - logo_url: string, optional
    - primary_color: string, optional
    - secondary_color: string, optional
    - accent_color: string, optional
    - header_background_color: string, optional
    - text_color: string, optional
    - font_preference: string, optional
    - status: string ("Active" or "Inactive")
- Response
    - id: UUID
    - client_name: string
    - program_name: string
    - status: string ("Active" or "Inactive")
- Business Rules
    - The `workflow_types` array must contain at least one of "patient" or "provider".
    - The `form_schema` must be a valid JSON schema.
    - Only users with the "admin" role can access this endpoint.
- Errors: 400 (Bad Request), 401 (Unauthorized), 403 (Forbidden), 404 (Not Found), 500 (Internal Server Error)

#### 4.1.5 DELETE /api/v1/enrollment-programs/{id}
Deletes an enrollment program (soft delete).
- Request: None
- Response: None (204 No Content on success)
- Business Rules: Only users with the "admin" role can access this endpoint.
- Errors: 401 (Unauthorized), 403 (Forbidden), 404 (Not Found), 500 (Internal Server Error)

#### 4.1.6 PATCH /api/v1/enrollment-programs/{id}/status
Toggles the active/inactive status of an enrollment program.
- Request
    - status: string ("Active" or "Inactive")
- Response
    - id: UUID
    - client_name: string
    - program_name: string
    - status: string ("Active" or "Inactive")
- Business Rules: Only users with the "admin" role can access this endpoint.
- Errors: 400 (Bad Request), 401 (Unauthorized), 403 (Forbidden), 404 (Not Found), 500 (Internal Server Error)

### 4.2 Form Submissions

#### 4.2.1 POST /api/v1/form-submissions
Creates a new form submission.
- Request
    - enrollment_program_id: UUID, required
    - submission_data: JSON, required
- Response
    - id: UUID
    - enrollment_program_id: UUID
    - submitted_at: timestamp
    - initial_status: string
- Business Rules
    - The `enrollment_program_id` must reference a valid, active enrollment program.
    - The `submission_data` must conform to the schema defined in the corresponding EnrollmentProgram.
- Errors: 400 (Bad Request), 404 (Not Found), 500 (Internal Server Error)

#### 4.2.2 GET /api/v1/enrollment-programs/{enrollment_program_id}/form-submissions
Retrieves all form submissions for a specific enrollment program.
- Request: None
- Response
    - data: array of FormSubmission objects (id, submitted_at, initial_status)
    - total: integer (total number of submissions)
    - page: integer (current page number)
    - pageSize: integer (number of submissions per page)
- Business Rules: Only users with the "admin" role can access this endpoint.
- Errors: 401 (Unauthorized), 403 (Forbidden), 404 (Not Found), 500 (Internal Server Error)

#### 4.2.3 GET /api/v1/form-submissions/{id}
Retrieves a specific form submission by ID.
- Request: None
- Response
    - id: UUID
    - enrollment_program_id: UUID
    - submission_data: JSON
    - submitted_at: timestamp
    - initial_status: string
- Business Rules: Only users with the "admin" role can access this endpoint.
- Errors: 401 (Unauthorized), 403 (Forbidden), 404 (Not Found), 500 (Internal Server Error)

## 5. Integration and External Endpoints

### 5.1 PDF Analysis via AI Provider

Connects to either the Claude or Gemini API to generate a form schema from a PDF.
- Receives an uploaded PDF and the selected AI provider.
- Converts the PDF to images and sends them to the selected AI service.
- Returns the generated JSON schema representing the form's structure, fields, and layout.
- Timeout: 60 seconds. Retry: 3 attempts with exponential backoff. Fallback: Log the error and notify the admin.

### 5.2 Logo Fetching

Connects to the Logo.dev API to fetch a company's logo based on their website URL.
- Receives a website URL.
- Returns the logo URL.
- Timeout: 10 seconds. Retry: 1 attempt. Fallback: Return a default logo URL or an empty string.

### 5.3 Website Screenshot Capture

Connects to the ScreenshotOne API to capture a screenshot of a website.
- Receives a website URL.
- Returns the URL of the screenshot.
- Timeout: 20 seconds. Retry: 1 attempt. Fallback: Return null and use default branding colors.

### 5.4 AI-Powered Color Extraction

Connects to either the Claude or Gemini API to extract dominant brand colors from a website screenshot or PDF.
- Receives a website screenshot or PDF image.
- Returns the dominant brand colors.
- Timeout: 30 seconds. Retry: 2 attempts. Fallback: Return default branding colors.

## 6. Async and Event Endpoints

_This project does not require asynchronous or event-driven endpoints._

## 7. Error Handling Standards

- Standard error response shape: `{ "error": "ERROR_CODE", "message": "Error description" }`. The `error` field contains a code that categorizes the error, and the `message` field provides a human-readable description.
- HTTP status code conventions:
    - 200 OK: Success
    - 201 Created: Resource created successfully
    - 204 No Content: Operation completed successfully with no response body
    - 400 Bad Request: Invalid request data
    - 401 Unauthorized: Authentication required
    - 403 Forbidden: User does not have permission
    - 404 Not Found: Resource not found
    - 409 Conflict: Request conflicts with existing data
    - 500 Internal Server Error: Unexpected server error
- Validation error format: If the request body fails validation, the error response will include a `details` field containing a list of validation errors: `{ "error": "VALIDATION_ERROR", "message": "Validation failed", "details": [ { "field": "fieldName", "message": "Error message" } ] }`
- Domain-specific error categories:
    - `INVALID_SCHEMA`: The provided form schema is invalid.
    - `AI_SERVICE_ERROR`: An error occurred while communicating with the AI service.
    - `LOGO_FETCH_FAILED`: Failed to fetch the logo from the website.
    - `PROGRAM_INACTIVE`: The enrollment program is inactive.

## 8. Key Design Decisions

- **REST API Style** - The project utilizes a REST API style to enable scalability and interoperability.
- **OAuth 2.0 Authentication** - OAuth 2.0 / SSO was chosen to securely authenticate admins.
- **/api/v1/ Versioning** - Implemented API versioning using the `/api/v1/` prefix to ensure forward compatibility.
- **JSON Data Format** - The API uses JSON for request and response bodies to facilitate data exchange between the frontend and backend.
- **UUID Primary Keys** - All database tables use UUID primary keys for uniqueness and security.
- **Soft Deletes** - Soft deletes are implemented using the `deleted_at` column for user-facing entities to preserve data integrity.
