# Implementation Plan — DEP Multi-Tenant

## Project Summary

**Tech Stack:** Java / Spring Boot · Angular · PostgreSQL
**Target Consumer:** Both (AI Tool + Development Team)
**Delivery Strategy:** Phased delivery
**Generated:** 2024-09-20T10:00:00Z

## Effort Summary

| Phase | Tasks | S | M | L |
|-------|-------|---|---|---|
| Phase 1: Foundation & Authentication | 7 | 3 | 3 | 1 |
| Phase 2: Enrollment Portal Configuration | 7 | 2 | 3 | 2 |
| Phase 3: Public Form Rendering & Submission | 4 | 1 | 2 | 1 |
| Phase 4: Program Management Dashboard & Deployment | 4 | 2 | 2 | 0 |
| **Total** | **22** | **8** | **10** | **4** |

*S = under 1 hour · M = 1-3 hours · L = 3-8 hours*

---

## Phase 0: Prerequisites

> These steps require human action before starting implementation.

### Development Environment

- [ ] Install Java 21+ (Temurin/Adoptium or equivalent)
- [ ] Install Node.js 20+ LTS
- [ ] Install Docker and Docker Compose

### Database

- [ ] Provision a PostgreSQL 15+ instance (local Docker or cloud-hosted)
- [ ] Create a database for this project
- [ ] Note connection credentials: host, port, database name, username, password

### API Keys

- [ ] Obtain API keys for Claude, Gemini, Logo.dev, and ScreenshotOne
- [ ] Obtain Contentful API key and space ID

### Environment File

- [ ] Create .env file in project root
- [ ] Add DATABASE_URL, CONTENTFUL_API_KEY, CLAUDE_API_KEY, GEMINI_API_KEY, LOGO_DEV_API_KEY, SCREENSHOTONE_API_KEY, ADMIN_USERNAME, ADMIN_PASSWORD

### Verify Setup

- [ ] Verify that you can connect to the PostgreSQL database
- [ ] Verify that you can authenticate with Contentful API using the API key

---

## Phase 1: Foundation & Authentication

**Goal:** Set up project scaffold, database connectivity, and user authentication.
**Tasks:** 7 tasks (3S · 3M · 1L)

### Task 1.1: Project Scaffold [S]

**Effort:** Under 1 hour

Initialize the Spring Boot backend and Angular frontend with required dependencies. Set up the monorepo directory structure and verify compilation.

**Files to Create:**
- `backend/pom.xml`
- `backend/src/main/java/com/depmt/DepMtApplication.java`
- `backend/src/main/resources/application.yml`
- `frontend/package.json`
- `frontend/angular.json`
- `docker-compose.yml`
- `.gitignore`

**References:**
- Architecture Overview → Tech Stack
- Architecture Overview → Project Structure

**Acceptance Criteria:**
- [ ] ./mvnw clean package compiles without errors
- [ ] ng build completes without errors
- [ ] docker-compose up builds containers

**Dependencies:**
- Depends on: None (first task)
- Blocks: Task 1.2: Database Configuration
- Blocks: Task 1.3: Initial Database Schema

### Task 1.2: Database Configuration [S]

**Effort:** Under 1 hour

Configure Spring Boot to connect to the PostgreSQL database. Define the database URL in application.yml and verify the connection.

**Files to Modify:**
- `backend/src/main/resources/application.yml`

**References:**
- Data Model → PostgreSQL
- Architecture Overview → Database

**Acceptance Criteria:**
- [ ] Spring Boot connects to PostgreSQL on startup
- [ ] Database migrations run successfully

**Dependencies:**
- Depends on: Task 1.1: Project Scaffold
- Blocks: Task 1.4: Implement Session Auth

### Task 1.3: Initial Database Schema [M]

**Effort:** 1-3 hours

Define initial database schema for `enrollment_programs` and `form_submissions` tables. Use UUID primary keys and audit timestamps.

**Files to Create:**
- `backend/src/main/resources/db/migration/V001__create_enrollment_programs_table.sql`
- `backend/src/main/resources/db/migration/V002__create_form_submissions_table.sql`

**References:**
- Data Model → EnrollmentProgram
- Data Model → FormSubmission

**Acceptance Criteria:**
- [ ] Flyway migrations run successfully
- [ ] Tables are created with correct schema
- [ ] Tables include UUID primary keys

**Dependencies:**
- Depends on: Task 1.1: Project Scaffold
- Depends on: Task 1.2: Database Configuration
- Blocks: Task 1.4: Implement Session Auth

### Task 1.4: Implement Session Auth [L]

**Effort:** 3-8 hours

Implement session-based authentication using Spring Security with HTTP-only cookies to protect admin routes. Configure in-memory user store for MVP with credentials set through environment variables.

**Files to Create:**
- `backend/src/main/java/com/depmt/config/SecurityConfig.java`

**Files to Modify:**
- `backend/pom.xml`
- `backend/src/main/resources/application.yml`

**References:**
- Architecture Overview → Security Architecture
- API Contract → Authentication Endpoints
- Global Technical Guidelines → Security

**Acceptance Criteria:**
- [ ] Admin routes are protected and require authentication
- [ ] Unauthenticated users are redirected to the login page
- [ ] Authentication uses HTTP-only cookies

**Dependencies:**
- Depends on: Task 1.2: Database Configuration
- Depends on: Task 1.3: Initial Database Schema
- Blocks: Task 1.5: Create Admin Login Page

### Task 1.5: Create Admin Login Page [M]

**Effort:** 1-3 hours

Create a simple login page with username and password fields. Integrate with the Spring Security backend for authentication.

**Files to Create:**
- `frontend/src/app/components/login/login.component.ts`
- `frontend/src/app/components/login/login.component.html`
- `frontend/src/app/components/login/login.component.css`

**Files to Modify:**
- `frontend/src/app/app-routing.module.ts`

**References:**
- UI Screens → Admin Login
- Architecture Overview → Frontend
- API Contract → Authentication Endpoints

**Acceptance Criteria:**
- [ ] Login page is accessible at /login
- [ ] Login redirects to the dashboard upon successful authentication
- [ ] Invalid credentials display an error message

**Dependencies:**
- Depends on: Task 1.4: Implement Session Auth
- Blocks: Task 1.6: Implement Admin Route Protection

### Task 1.6: Implement Admin Route Protection [S]

**Effort:** Under 1 hour

Configure Angular route guards to protect admin routes, redirecting unauthenticated users to the login page.

**Files to Create:**
- `frontend/src/app/guards/auth.guard.ts`

**Files to Modify:**
- `frontend/src/app/app-routing.module.ts`

**References:**
- Architecture Overview → Frontend
- UI Screens → Admin Program Management Dashboard
- API Contract → Authentication Endpoints

**Acceptance Criteria:**
- [ ] Accessing /admin routes redirects to login when unauthenticated
- [ ] Authenticated users can access /admin routes
- [ ] Auth guard correctly checks authentication status

**Dependencies:**
- Depends on: Task 1.5: Create Admin Login Page

### Task 1.7: Implement Basic Auth API [M]

**Effort:** 1-3 hours

Create a basic REST API endpoint for authentication. This endpoint will receive the username and password, authenticate the user and return a JWT token.

**Files to Create:**
- `backend/src/main/java/com/depmt/controller/AuthController.java`
- `backend/src/main/java/com/depmt/dto/AuthRequest.java`
- `backend/src/main/java/com/depmt/dto/AuthResponse.java`

**Files to Modify:**
- `backend/src/main/java/com/depmt/config/SecurityConfig.java`

**References:**
- API Contract → Authentication Endpoints
- Architecture Overview → Backend

**Acceptance Criteria:**
- [ ] API endpoint /api/v1/auth/login exists
- [ ] API authenticates using hardcoded credentials
- [ ] API returns JWT token upon successful authentication

**Dependencies:**
- Depends on: Task 1.4: Implement Session Auth

### Phase 1 — Verification Gate

**What you should see:** Working app shell with a functional login page. The dashboard renders after successful authentication.

**Automated checks:**
- [ ] ./mvnw clean package passes all tests
- [ ] ng build produces zero errors
- [ ] docker-compose up starts all services cleanly

**Manual checks:**
- [ ] Navigate to http://localhost:4200 and see login page
- [ ] Log in with seeded admin credentials and see dashboard
- [ ] Reload page and confirm session is preserved
- [ ] Access protected route while logged out and confirm redirect to login

---

## Phase 2: Enrollment Portal Configuration

**Goal:** Implement the admin wizard for configuring enrollment portals.
**Tasks:** 7 tasks (2S · 3M · 2L)

### Task 2.1: PDF Upload Component [M]

**Effort:** 1-3 hours

Implement a file upload component in the admin wizard's first step, allowing admins to upload PDF enrollment forms. Display selected filename.

**Files to Create:**
- `frontend/src/app/components/upload/upload.component.ts`
- `frontend/src/app/components/upload/upload.component.html`
- `frontend/src/app/components/upload/upload.component.css`

**Files to Modify:**
- `frontend/src/app/app.module.ts`

**References:**
- UI Screens → Enrollment Portal Configuration - Upload
- Product Requirements Document → STORY: 1.1
- Product Requirements Document → TASK: 1.1.1

**Acceptance Criteria:**
- [ ] Admin can upload a PDF document
- [ ] Uploaded filename is displayed
- [ ] Component integrates into wizard

**Dependencies:**
- Depends on: Task 1.6: Implement Admin Route Protection
- Blocks: Task 2.3: PDF Analysis Endpoint

### Task 2.2: AI Provider Selection [S]

**Effort:** Under 1 hour

Implement a dropdown menu allowing users to select Claude or Gemini as the AI provider for schema generation.

**Files to Modify:**
- `frontend/src/app/components/upload/upload.component.ts`
- `frontend/src/app/components/upload/upload.component.html`

**References:**
- UI Screens → Enrollment Portal Configuration - Upload
- Product Requirements Document → STORY: 1.1
- Product Requirements Document → TASK: 1.1.2

**Acceptance Criteria:**
- [ ] Dropdown includes Claude and Gemini options
- [ ] Selected AI provider is displayed
- [ ] Component integrates into wizard

**Dependencies:**
- Depends on: Task 1.6: Implement Admin Route Protection
- Blocks: Task 2.3: PDF Analysis Endpoint

### Task 2.3: PDF Analysis Endpoint [L]

**Effort:** 3-8 hours

Create a Spring Boot REST endpoint that receives the uploaded PDF, converts it to images, and calls the selected AI service (Claude or Gemini) for schema generation.

**Files to Create:**
- `backend/src/main/java/com/depmt/controller/PdfAnalysisController.java`
- `backend/src/main/java/com/depmt/service/PdfAnalysisService.java`

**Files to Modify:**
- `backend/pom.xml`

**References:**
- Architecture Overview → Backend
- API Contract → Integration and External Endpoints
- Product Requirements Document → STORY: 1.1
- Product Requirements Document → TASK: 1.1.3

**Acceptance Criteria:**
- [ ] API endpoint exists at /api/v1/pdf-analysis
- [ ] API receives PDF and AI provider
- [ ] API returns generated JSON schema

**Dependencies:**
- Depends on: Task 2.1: PDF Upload Component
- Depends on: Task 2.2: AI Provider Selection
- Blocks: Task 2.4: Integrate Claude & Gemini APIs

### Task 2.4: Integrate Claude & Gemini APIs [L]

**Effort:** 3-8 hours

Implement the logic to call the Claude and Gemini APIs, passing the PDF images and receiving the generated JSON schema. The schema format must be structured according to the agreed-upon standard.

**Files to Modify:**
- `backend/src/main/java/com/depmt/service/PdfAnalysisService.java`

**References:**
- Architecture Overview → Key Integrations
- Product Requirements Document → STORY: 1.1
- Product Requirements Document → TASK: 1.1.4

**Acceptance Criteria:**
- [ ] API calls Claude or Gemini based on selection
- [ ] API correctly passes images to AI service
- [ ] API handles API errors gracefully

**Dependencies:**
- Depends on: Task 2.3: PDF Analysis Endpoint

### Task 2.5: Convert PDF to Images [M]

**Effort:** 1-3 hours

Implement PDF to image conversion using PDFBox to convert each page of the uploaded PDF into a PNG image encoded as base64.

**Files to Modify:**
- `backend/src/main/java/com/depmt/service/PdfAnalysisService.java`
- `backend/pom.xml`

**References:**
- Product Requirements Document → STORY: 1.1
- Product Requirements Document → TASK: 1.1.5

**Acceptance Criteria:**
- [ ] PDF is correctly converted to images
- [ ] Images are encoded as base64
- [ ] Images are passed to AI service

**Dependencies:**
- Depends on: Task 2.3: PDF Analysis Endpoint

### Task 2.6: Visual Schema Editor UI [M]

**Effort:** 1-3 hours

Develop a UI component that visually represents the form schema and allows the admin to modify field labels, types and required properties.

**Files to Create:**
- `frontend/src/app/components/schema-editor/schema-editor.component.ts`
- `frontend/src/app/components/schema-editor/schema-editor.component.html`
- `frontend/src/app/components/schema-editor/schema-editor.component.css`

**Files to Modify:**
- `frontend/src/app/app.module.ts`

**References:**
- UI Screens → Enrollment Portal Configuration - Review Schema
- Product Requirements Document → STORY: 1.2
- Product Requirements Document → TASK: 1.2.1

**Acceptance Criteria:**
- [ ] Schema is visually represented
- [ ] Admin can modify field properties
- [ ] Component integrates into wizard

**Dependencies:**
- Depends on: Task 2.4: Integrate Claude & Gemini APIs
- Blocks: Task 2.7: Schema Update Logic

### Task 2.7: Schema Update Logic [S]

**Effort:** Under 1 hour

Implement backend logic to receive schema updates from the frontend editor and update the corresponding entry in Contentful.

**Files to Modify:**
- `backend/src/main/java/com/depmt/service/ContentfulService.java`

**References:**
- Architecture Overview → Key Integrations
- Product Requirements Document → STORY: 1.2
- Product Requirements Document → TASK: 1.2.2

**Acceptance Criteria:**
- [ ] Backend receives schema updates
- [ ] Updates are saved to Contentful
- [ ] API handles Contentful errors

**Dependencies:**
- Depends on: Task 2.6: Visual Schema Editor UI

### Phase 2 — Verification Gate

**What you should see:** Admin can upload a PDF, select an AI provider, view and edit the generated schema.

**Automated checks:**
- [ ] ./mvnw clean package passes all tests
- [ ] ng build produces zero errors

**Manual checks:**
- [ ] Navigate to the enrollment portal configuration wizard
- [ ] Upload a sample PDF enrollment form
- [ ] Select Claude as the AI provider
- [ ] Review the generated form schema and modify field properties

---

## Phase 3: Public Form Rendering & Submission

**Goal:** Implement the public-facing enrollment form and submission processing.
**Tasks:** 4 tasks (1S · 2M · 1L)

### Task 3.1: Contentful Schema Fetching [M]

**Effort:** 1-3 hours

Implement the logic to fetch the form schema from Contentful based on the `program-id` passed in the URL.

**Files to Modify:**
- `frontend/src/app/services/contentful.service.ts`

**References:**
- UI Screens → Public Enrollment Form
- Product Requirements Document → STORY: 2.1
- Product Requirements Document → TASK: 2.1.1

**Acceptance Criteria:**
- [ ] Frontend fetches schema by program ID
- [ ] Correct Contentful API calls are made
- [ ] Error handling for missing schema

**Dependencies:**
- Depends on: Task 2.7: Schema Update Logic
- Blocks: Task 3.2: Dynamic Form Rendering

### Task 3.2: Dynamic Form Rendering [L]

**Effort:** 3-8 hours

Develop a component that dynamically renders the enrollment form based on the JSON schema fetched from Contentful. Handle various field types and layout configurations.

**Files to Create:**
- `frontend/src/app/components/dynamic-form/dynamic-form.component.ts`
- `frontend/src/app/components/dynamic-form/dynamic-form.component.html`
- `frontend/src/app/components/dynamic-form/dynamic-form.component.css`

**Files to Modify:**
- `frontend/src/app/app.module.ts`

**References:**
- UI Screens → Public Enrollment Form
- Product Requirements Document → STORY: 2.1
- Product Requirements Document → TASK: 2.1.2

**Acceptance Criteria:**
- [ ] Form renders correctly from JSON schema
- [ ] All field types are supported
- [ ] Responsive design is implemented

**Dependencies:**
- Depends on: Task 3.1: Contentful Schema Fetching
- Blocks: Task 3.3: Form Submission Endpoint

### Task 3.3: Form Submission Endpoint [M]

**Effort:** 1-3 hours

Create a Spring Boot REST endpoint that receives the form data from the frontend and stores it in the PostgreSQL database.

**Files to Create:**
- `backend/src/main/java/com/depmt/controller/FormSubmissionController.java`
- `backend/src/main/java/com/depmt/dto/FormSubmissionDto.java`

**References:**
- API Contract → Form Submissions
- Product Requirements Document → STORY: 2.2
- Product Requirements Document → TASK: 2.2.1

**Acceptance Criteria:**
- [ ] API endpoint exists at /api/v1/form-submissions
- [ ] API receives form data as JSON
- [ ] API validates the enrollment_program_id

**Dependencies:**
- Depends on: Task 3.2: Dynamic Form Rendering
- Blocks: Task 3.4: Integrate with PostgreSQL

### Task 3.4: Integrate with PostgreSQL [S]

**Effort:** Under 1 hour

Configure the backend to connect to the PostgreSQL database and store the form submission data.

**Files to Modify:**
- `backend/src/main/java/com/depmt/repository/FormSubmissionRepository.java`
- `backend/src/main/java/com/depmt/entity/FormSubmission.java`

**References:**
- Data Model → FormSubmission
- Product Requirements Document → STORY: 2.2
- Product Requirements Document → TASK: 2.2.2

**Acceptance Criteria:**
- [ ] Data is stored in PostgreSQL
- [ ] Data matches the form schema
- [ ] Enrollment program ID is correctly referenced

**Dependencies:**
- Depends on: Task 3.3: Form Submission Endpoint

### Phase 3 — Verification Gate

**What you should see:** Public enrollment form renders correctly and submissions are saved to the database.

**Automated checks:**
- [ ] ./mvnw clean package passes all tests
- [ ] ng build produces zero errors

**Manual checks:**
- [ ] Navigate to a public enrollment form URL
- [ ] Fill out the form and submit it
- [ ] Verify that the data is stored in the PostgreSQL database

---

## Phase 4: Program Management Dashboard & Deployment

**Goal:** Implement the admin dashboard for managing enrollment programs and prepare for deployment.
**Tasks:** 5 tasks (2S · 2M · 1L)

### Task 4.1: Program List View [M]

**Effort:** 1-3 hours

Build UI component for displaying a list of enrollment programs in the admin dashboard, with each program represented as a card.

**Files to Create:**
- `frontend/src/app/components/program-list/program-list.component.ts`
- `frontend/src/app/components/program-list/program-list.component.html`
- `frontend/src/app/components/program-list/program-list.component.css`

**Files to Modify:**
- `frontend/src/app/app.module.ts`

**References:**
- UI Screens → Admin Program Management Dashboard
- Product Requirements Document → STORY: 3.1
- Product Requirements Document → TASK: 3.1.1

**Acceptance Criteria:**
- [ ] Program list is displayed in dashboard
- [ ] Each program is displayed as a card
- [ ] Card displays client name, logo, program type and status

**Dependencies:**
- Depends on: Task 1.6: Implement Admin Route Protection
- Blocks: Task 4.2: Fetch Program Data for Dashboard

### Task 4.2: Fetch Program Data for Dashboard [M]

**Effort:** 1-3 hours

Implement a Spring Boot REST endpoint to fetch the data required for displaying the enrollment programs in the dashboard.

**Files to Create:**
- `backend/src/main/java/com/depmt/controller/EnrollmentProgramController.java`

**References:**
- API Contract → Enrollment Programs
- Product Requirements Document → STORY: 3.1
- Product Requirements Document → TASK: 3.1.2

**Acceptance Criteria:**
- [ ] API endpoint exists at /api/v1/enrollment-programs
- [ ] API returns program data
- [ ] Data includes client name, logo, program type and status

**Dependencies:**
- Depends on: Task 1.7: Implement Basic Auth API

### Task 4.3: Program Status Toggle [S]

**Effort:** Under 1 hour

Implement active/inactive toggle on program card in dashboard. Toggle updates program status in Contentful.

**Files to Modify:**
- `frontend/src/app/components/program-list/program-list.component.ts`
- `frontend/src/app/components/program-list/program-list.component.html`
- `backend/src/main/java/com/depmt/controller/EnrollmentProgramController.java`
- `backend/src/main/java/com/depmt/service/ContentfulService.java`

**References:**
- UI Screens → Admin Program Management Dashboard
- Product Requirements Document → STORY: 3.4
- Product Requirements Document → TASK: 3.4.1
- Product Requirements Document → TASK: 3.4.2

**Acceptance Criteria:**
- [ ] Program card has active/inactive toggle
- [ ] Toggling updates program status in Contentful
- [ ] Frontend reflects updated status

**Dependencies:**
- Depends on: Task 4.1: Program List View
- Depends on: Task 4.2: Fetch Program Data for Dashboard

### Task 4.4: Edit Program Navigation [S]

**Effort:** Under 1 hour

Implement navigation from program card to edit wizard. Modify the wizard to load existing program data based on program ID.

**Files to Modify:**
- `frontend/src/app/components/program-list/program-list.component.ts`
- `frontend/src/app/app-routing.module.ts`

**References:**
- UI Screens → Admin Program Management Dashboard
- Product Requirements Document → STORY: 3.2
- Product Requirements Document → TASK: 3.2.1
- Product Requirements Document → TASK: 3.2.2

**Acceptance Criteria:**
- [ ] Clicking 'Edit' navigates to wizard
- [ ] Wizard loads existing program data
- [ ] Program ID is passed to wizard

**Dependencies:**
- Depends on: phase-4-task-

