```markdown
## Executive Summary

HealthBridge is a web-based, multi-tenant enrollment platform that allows healthcare clients to digitize patient and provider onboarding workflows. The platform uses AI to automate the conversion of PDF forms into JSON schemas and to extract branding elements, significantly reducing client onboarding time. The MVP focuses on a streamlined admin wizard for creating and managing enrollment programs, and public-facing enrollment forms without authentication.

---

## EPIC: 1. Admin Enrollment Portal Configuration

**Summary:** Enable admins to configure client enrollment portals via an intuitive wizard.
**As a** Client Services Admin, **I want to** configure enrollment portals for healthcare clients, **so that** they can digitally onboard patients and providers more efficiently.
**Priority:** Critical
**Labels:** mvp, admin, configuration, ai, contentful

### STORY: 1.1. PDF Upload and AI-Powered Schema Generation

**Summary:** Upload PDF form, analyze via AI, and generate form schema.
**Priority:** Critical
**Labels:** mvp, admin, ai, schema

**Scenario:** Admin uploads a PDF enrollment form, and the AI generates a corresponding form schema.
**Given:** The admin user is in the "Upload" step of the enrollment portal configuration wizard.
**When:** The admin uploads a PDF form and selects an AI provider (Claude or Gemini).
**Then:** The system uses the selected AI provider to analyze the PDF and generates a JSON schema representing the form's structure, fields, and layout.

**Acceptance Criteria:**
- The admin can upload a PDF document.
- The admin can select either Claude or Google Gemini as the AI provider.
- The system converts the PDF into a series of images and sends them to the selected AI provider.
- The system receives a JSON schema from the AI provider.
- The system visually represents the generated form schema to the admin.

#### TASK: 1.1.1. Implement PDF Upload Functionality

**Summary:** Implement PDF upload feature in the admin wizard.
**Component:** Frontend
**Description:** Implement file upload component in the admin wizard's first step, allowing admins to upload PDF enrollment forms.

#### TASK: 1.1.2. Implement AI Provider Selection

**Summary:** Implement AI provider selection dropdown.
**Component:** Frontend
**Description:** Implement a dropdown menu in the admin wizard's first step allowing users to select Claude or Gemini as the AI provider for form analysis.

#### TASK: 1.1.3. Backend Endpoint for PDF Analysis

**Summary:** Create Spring Boot API endpoint to process PDF and call AI service.
**Component:** Backend
**Description:** Create a Spring Boot REST endpoint that receives the uploaded PDF, converts it to images, and calls the selected AI service (Claude or Gemini) for schema generation.

#### TASK: 1.1.4. Integrate with Claude and Gemini APIs

**Summary:** Integrate backend with Claude and Gemini vision APIs.
**Component:** Backend
**Description:** Implement the logic to call the Claude and Gemini vision APIs, passing the PDF images and receiving the generated JSON schema. The schema format must be structured according to the agreed-upon standard.

#### TASK: 1.1.5. Convert PDF to Images

**Summary:** Implement PDF to image conversion using PDFBox.
**Component:** Backend
**Description:** Implement a function using PDFBox to convert each page of the uploaded PDF into a PNG image encoded as base64.

### STORY: 1.2. Review and Edit AI-Generated Form Schema

**Summary:** Allow admins to review and edit the AI-generated form schema.
**Priority:** Critical
**Labels:** mvp, admin, schema, editor

**Scenario:** Admin reviews and edits the AI-generated form schema.
**Given:** The system has generated a form schema based on the uploaded PDF.
**When:** The admin navigates to the "Review Schema" step in the wizard.
**Then:** The admin can visually inspect the form schema, modify field properties (label, type, required), add new fields, remove existing fields, and reorder fields and sections.

**Acceptance Criteria:**
- The system presents the form schema in a visually understandable format {confirm with PM}.
- The admin can modify field labels, types, and required/optional properties.
- The admin can add new fields to the schema.
- The admin can remove existing fields from the schema.
- The admin can reorder fields within the form.

#### TASK: 1.2.1. Implement Visual Schema Editor

**Summary:** Build UI component for visual schema editing.
**Component:** Frontend
**Description:** Develop a UI component that visually represents the form schema and allows the admin to modify, add, remove, and reorder fields. Prioritize a property editor approach over drag-and-drop for MVP.

#### TASK: 1.2.2. Implement Schema Update Logic

**Summary:** Implement backend logic to update schema in Contentful.
**Component:** Backend
**Description:** Implement the backend logic to receive schema updates from the frontend editor and update the corresponding entry in Contentful.

### STORY: 1.3. Configure Branding for Enrollment Portal

**Summary:** Enable admins to configure branding for the enrollment portal.
**Priority:** High
**Labels:** mvp, admin, branding, ai

**Scenario:** Admin configures the branding for the enrollment portal.
**Given:** The admin is in the "Branding" step of the enrollment portal configuration wizard.
**When:** The admin enters a client's website URL or uploads the PDF form.
**Then:** The system automatically fetches the client's logo, extracts brand colors from the website or PDF, and pre-populates the branding configuration. The admin can override these settings and preview the changes in real-time.

**Acceptance Criteria:**
- The admin can enter a client's website URL.
- The system automatically fetches the client's logo from the website.
- The system extracts dominant colors from the website or PDF.
- The admin can manually select colors using a color picker.
- The admin can preview the enrollment form with the selected branding in real-time.
- The branding configuration includes primary color, secondary color, accent color, header background color, text color, font preference, and logo URL.

#### TASK: 1.3.1. Implement Website Logo Fetching

**Summary:** Implement website logo fetching using Logo.dev API.
**Component:** Backend
**Description:** Integrate with the Logo.dev API to fetch a company's logo based on their website URL.

#### TASK: 1.3.2. Implement Website Screenshot Capture

**Summary:** Integrate ScreenshotOne API to capture website screenshot.
**Component:** Backend
**Description:** Integrate with ScreenshotOne or similar service to capture a screenshot of the client's website homepage.

#### TASK: 1.3.3. Implement AI-Powered Color Extraction

**Summary:** Implement AI to extract branding colors from website screenshot or PDF.
**Component:** Backend
**Description:** Use the selected AI provider (Claude or Gemini) to analyze the website screenshot or PDF images and extract the dominant brand colors.

#### TASK: 1.3.4. Implement Real-Time Branding Preview

**Summary:** Implement real-time preview of branding settings in form builder.
**Component:** Frontend
**Description:** Develop a live preview panel in the admin wizard that displays the enrollment form with the currently configured branding settings in real-time.

### STORY: 1.4. Set Program Details and Workflow Types

**Summary:** Configure program details and workflow types for enrollment portal.
**Priority:** High
**Labels:** mvp, admin, configuration, workflow

**Scenario:** Admin sets the program details and selects the supported workflow types.
**Given:** The admin is in the "Program Details" step of the enrollment portal configuration wizard.
**When:** The admin enters the program name, client name, and description.
**Then:** The admin can select which workflow types (patient enrollment, provider enrollment, or both) are supported by the program.

**Acceptance Criteria:**
- The admin can enter the program name, client name, and description.
- The admin can select one or more workflow types (patient, provider, or both).
- The selected workflow types are stored in the program's Contentful entry.
- The frontend renders the appropriate workflow options based on the selected workflow types.

#### TASK: 1.4.1. Implement Program Details Form

**Summary:** Implement form for entering program details.
**Component:** Frontend
**Description:** Create a form in the admin wizard's "Program Details" step to capture the program name, client name, and description.

#### TASK: 1.4.2. Implement Workflow Type Selection

**Summary:** Implement workflow type selection (patient, provider, or both).
**Component:** Frontend
**Description:** Implement a UI element (e.g., checkboxes or a multi-select dropdown) that allows the admin to select the workflow types supported by the program.

#### TASK: 1.4.3. Store Workflow Types in Contentful

**Summary:** Update backend to store workflow types in Contentful.
**Component:** Backend
**Description:** Modify the backend logic to store the selected workflow types (patient, provider, or both) in the Contentful entry associated with the program.

### STORY: 1.5. Publish Enrollment Portal

**Summary:** Allow admins to publish the completed enrollment portal configuration.
**Priority:** Critical
**Labels:** mvp, admin, publish

**Scenario:** Admin publishes the enrollment portal after completing the configuration wizard.
**Given:** The admin has completed all steps in the enrollment portal configuration wizard.
**When:** The admin clicks the "Publish" button.
**Then:** The system saves all configuration data to Contentful, sets the program status to "Active", and generates a unique URL for the enrollment portal.

**Acceptance Criteria:**
- The system saves all configuration data to Contentful.
- The program status is set to "Active".
- A unique URL is generated for the enrollment portal in the format `/enroll/program-id`.
- The enrollment portal is accessible via the generated URL.

#### TASK: 1.5.1. Implement Publish Logic

**Summary:** Implement publish function to save configuration and generate URL.
**Component:** Backend
**Description:** Implement the backend logic that saves all configuration data to Contentful, sets the program status to "Active", and generates a unique URL for the enrollment portal.

#### TASK: 1.5.2. Generate Enrollment Portal URL

**Summary:** Generate unique URL for each enrollment program.
**Component:** Backend
**Description:** Implement the logic to generate a unique URL for each enrollment portal, following the `/enroll/program-id` format.

---

## EPIC: 2. Public Enrollment Form Rendering and Submission

**Summary:** Render enrollment forms based on Contentful configuration and capture submissions.
**As a** Patient or Provider, **I want to** complete an enrollment form, **so that** I can enroll in a program or credential myself.
**Priority:** Critical
**Labels:** mvp, enrollment, frontend, submission

### STORY: 2.1. Render Enrollment Form from Contentful Schema

**Summary:** Render dynamic enrollment form based on Contentful schema.
**Priority:** Critical
**Labels:** mvp, enrollment, frontend, contentful

**Scenario:** User accesses an enrollment portal, and the frontend renders the form based on the schema stored in Contentful.
**Given:** A user navigates to the enrollment portal URL `/enroll/program-id`.
**When:** The frontend fetches the form schema from Contentful based on the `program-id`.
**Then:** The frontend dynamically renders the enrollment form based on the retrieved schema, including all fields, sections, and branding elements.

**Acceptance Criteria:**
- The frontend fetches the form schema from Contentful based on the `program-id`.
- The frontend renders all fields, sections, and branding elements defined in the schema.
- The rendered form matches the preview shown in the admin wizard.
- The form adapts to different screen sizes (responsive design).

#### TASK: 2.1.1. Implement Contentful Schema Fetching

**Summary:** Implement Contentful schema fetching by program ID.
**Component:** Frontend
**Description:** Implement the logic to fetch the form schema from Contentful based on the `program-id` passed in the URL.

#### TASK: 2.1.2. Implement Dynamic Form Rendering

**Summary:** Implement dynamic form rendering based on JSON schema.
**Component:** Frontend
**Description:** Develop a component that dynamically renders the enrollment form based on the JSON schema fetched from Contentful. This component should handle various field types (text input, dropdown, checkbox, date picker, radio buttons) and layout configurations.

### STORY: 2.2. Capture and Store Form Submissions

**Summary:** Capture and store enrollment form submissions.
**Priority:** Critical
**Labels:** mvp, enrollment, backend, submission, database

**Scenario:** User submits the enrollment form, and the system captures and stores the submission data.
**Given:** A user has filled out the enrollment form.
**When:** The user clicks the "Submit" button.
**Then:** The frontend sends the form data to the backend, which stores the data in the PostgreSQL database hosted on Supabase.

**Acceptance Criteria:**
- The frontend sends the form data to the backend upon submission.
- The backend stores the form data in the PostgreSQL database on Supabase, including the program ID, form ID, submission data as JSON payload, timestamp, and initial status.
- The submission data is stored in a structured, integration-friendly JSON format.

#### TASK: 2.2.1. Implement Form Submission Endpoint

**Summary:** Implement Spring Boot API endpoint for form submission.
**Component:** Backend
**Description:** Create a Spring Boot REST endpoint that receives the form data from the frontend and stores it in the PostgreSQL database.

#### TASK: 2.2.2. Integrate with Supabase PostgreSQL

**Summary:** Integrate backend with Supabase PostgreSQL database.
**Component:** Backend
**Description:** Configure the backend to connect to the Supabase-hosted PostgreSQL database and store the form submission data.

#### TASK: 2.2.3. Define Submission Data Model

**Summary:** Define the structure for storing form submission data in the database.
**Component:** Database
**Description:** Define the database schema for storing form submission data, including fields for program ID, form ID, submission data as a JSON payload, timestamp, and status.

---

## EPIC: 3. Program Management Dashboard

**Summary:** Allow internal users to manage existing programs through a management dashboard.
**As a** Client Services Admin, **I want to** manage existing enrollment programs, **so that** I can update program details, preview forms, and monitor submissions.
**Priority:** High
**Labels:** admin, management, dashboard

### STORY: 3.1. List Existing Enrollment Programs

**Summary:** Display a list of existing enrollment programs in the admin dashboard.
**Priority:** High
**Labels:** admin, dashboard, management

**Scenario:** Admin views a list of all enrollment programs.
**Given:** The admin user is logged into the admin dashboard.
**When:** The admin navigates to the program management section.
**Then:** The system displays a list of all existing enrollment programs, each represented as a card showing the client name, logo, program type, and status.

**Acceptance Criteria:**
- The system displays a list of all enrollment programs.
- Each program is displayed as a card.
- Each card displays the client name, logo, program type, and status.

#### TASK: 3.1.1. Implement Program List View

**Summary:** Build UI component for listing enrollment programs.
**Component:** Frontend
**Description:** Develop a UI component to display a list of enrollment programs, with each program represented as a card.

#### TASK: 3.1.2. Fetch Program Data for Dashboard

**Summary:** Implement backend endpoint to fetch program data for the dashboard.
**Component:** Backend
**Description:** Implement a Spring Boot REST endpoint to fetch the data required for displaying the enrollment programs in the dashboard, including client name, logo, program type, and status.

### STORY: 3.2. Edit Existing Enrollment Program

**Summary:** Allow admins to edit the configuration of an existing enrollment program.
**Priority:** High
**Labels:** admin, management, edit

**Scenario:** Admin edits the configuration of an existing program.
**Given:** The admin is viewing the program management dashboard.
**When:** The admin clicks the "Edit" button on a program card.
**Then:** The admin is redirected to the enrollment portal configuration wizard, pre-populated with the existing program's configuration data.

**Acceptance Criteria:**
- The "Edit" button redirects the admin to the configuration wizard.
- The configuration wizard is pre-populated with the existing program's data.
- The admin can modify the program configuration and save the changes.

#### TASK: 3.2.1. Implement Edit Program Navigation

**Summary:** Implement navigation from program card to edit wizard.
**Component:** Frontend
**Description:** Implement the "Edit" button on each program card in the dashboard, which navigates the admin to the enrollment portal configuration wizard with the existing program's ID.

#### TASK: 3.2.2. Load Existing Program Data into Wizard

**Summary:** Implement loading existing program data into edit wizard.
**Component:** Frontend
**Description:** Modify the enrollment portal configuration wizard to load and display the configuration data for an existing program based on its ID.

### STORY: 3.3. Preview Enrollment Form

**Summary:** Allow admins to preview the enrollment form as a user would see it.
**Priority:** Critical
**Labels:** admin, management, preview

**Scenario:** Admin previews the enrollment form.
**Given:** The admin is viewing the program management dashboard.
**When:** The admin clicks the "View" button on a program card.
**Then:** The system opens the enrollment form in a new tab or window, displaying it exactly as a patient or provider would see it.

**Acceptance Criteria:**
- The "View" button opens the enrollment form in a new tab or window.
- The enrollment form is displayed with all branding and fields as configured in the admin wizard.

#### TASK: 3.3.1. Implement "View" Button Navigation

**Summary:** Implement "View" button on each program card in dashboard.
**Component:** Frontend
**Description:** Implement a "View" button on each program card in the admin dashboard. Clicking this button opens the enrollment form in a new tab or window using the program's unique URL.

### STORY: 3.4. Toggle Program Active/Inactive Status

**Summary:** Allow admins to toggle the active/inactive status of an enrollment program.
**Priority:** High
**Labels:** admin, management, status

**Scenario:** Admin activates or deactivates an enrollment program.
**Given:** The admin is viewing the program management dashboard.
**When:** The admin toggles the "Active" status switch on a program card.
**Then:** The system updates the program's status in Contentful. If the program is active, it is visible to users via its enrollment URL. If the program is inactive, it is hidden from the public and its URL displays an appropriate message.

**Acceptance Criteria:**
- The program card includes an "Active" status toggle.
- Toggling the switch updates the program's status in Contentful.
- Inactive programs do not appear in public program lists.
- Inactive program URLs display an appropriate message.

#### TASK: 3.4.1. Implement Active/Inactive Toggle

**Summary:** Implement active/inactive toggle on program card.
**Component:** Frontend
**Description:** Add an "Active" status toggle (e.g., a switch or checkbox) to each program card in the admin dashboard.

#### TASK: 3.4.2. Update Program Status in Contentful

**Summary:** Implement backend logic to update program status in Contentful.
**Component:** Backend
**Description:** Implement the backend logic to update the program's "Active" status in its Contentful entry when the toggle is switched in the admin dashboard.

---

## EPIC: 4. Admin Authentication and Authorization

**Summary:** Implement authentication and authorization for the admin section of the application.
**As a** Client Services Admin, **I want to** securely access the admin section of the application, **so that** I can manage enrollment programs.
**Priority:** Critical
**Labels:** mvp, auth, admin

### STORY: 4.1. Secure Admin Routes with Authentication

**Summary:** Secure admin routes requiring user login.
**Priority:** Critical
**Labels:** mvp, auth, admin

**Scenario:** Admin attempts to access a protected admin route.
**Given:** The user is not authenticated.
**When:** The user attempts to access a URL under the `/admin` path.
**Then:** The user is redirected to the login page.

**Acceptance Criteria:**
- All routes under the `/admin` path require authentication.
- Unauthenticated users are redirected to the login page when attempting to access admin routes.

#### TASK: 4.1.1. Implement Session-Based Authentication

**Summary:** Implement session-based authentication using Spring Security.
**Component:** Backend
**Description:** Implement session-based authentication using Spring Security with HTTP-only cookies to protect admin routes.

#### TASK: 4.1.2. Create Login Page

**Summary:** Design and implement basic login page.
**Component:** Frontend
**Description:** Create a simple login page with fields for username and password.

### STORY: 4.2. Implement Admin Role-Based Access Control

**Summary:** Implement admin role-based access control.
**Priority:** Medium
**Labels:** auth, admin, roles

**Scenario:** User with insufficient privileges attempts to access a protected admin function.
**Given:** The user is authenticated but does not have the required role (e.g., "admin").
**When:** The user attempts to perform an action that requires the "admin" role.
**Then:** The system prevents the user from performing the action and displays an error message.

**Acceptance Criteria:**
- Only users with the "admin" role can create and manage enrollment programs.
- Users without the "admin" role are prevented from accessing sensitive admin functions.

#### TASK: 4.2.1. Implement Role-Based Access Control

**Summary:** Implement role-based access control in Spring Security.
**Component:** Backend
**Description:** Configure Spring Security to enforce role-based access control for admin functions, ensuring that only users with the "admin" role can perform sensitive actions.

#### TASK: 4.2.2. Implement Simple In-Memory User Store

**Summary:** Implement simple in-memory user store with hardcoded credentials.
**Component:** Backend
**Description:** For MVP, use a simple in-memory user store with hardcoded credentials set through environment variables. Avoid building a full user management system.

---
```