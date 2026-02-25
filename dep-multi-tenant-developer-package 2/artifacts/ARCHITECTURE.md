# Architecture Overview
**Project:** DEP Multi-Tenant
**Stack:** Angular · Java / Spring Boot · PostgreSQL · Cloud — Google Cloud (GCP) · OAuth 2.0 / SSO · REST

### 2. System Context

The HealthBridge DEP Multi-Tenant platform is a web application designed to streamline patient and provider enrollment for healthcare clients. It provides an admin interface for Client Services Admins to configure enrollment portals, leveraging AI to automate form schema generation and branding extraction. The platform then renders these configured forms as public-facing enrollment experiences for Patients and Providers. The core technical purpose of the system is to digitize onboarding workflows, reducing manual effort and improving data accuracy.

### 3. Application Layers

| Layer                     | Technology            | Responsibility                                                                                                                                                                                                                                                           |
| ------------------------- | --------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| Frontend                  | Angular               | Presents the user interface, handles user interactions, calls the backend APIs, and renders the enrollment forms.                                                                                                                                                      |
| API Gateway / Controllers | Java / Spring Boot    | Receives HTTP requests, authenticates users, routes requests to the appropriate service, and handles response serialization. Enforces API versioning `/api/v1/` as per global guidelines.                                                                                        |
| Business Logic            | Java / Spring Boot    | Implements the core business logic of the application, including form schema generation, branding extraction, workflow management, and data validation.                                                                                                                   |
| Data Access               | Java / Spring Data JPA | Provides an abstraction layer for interacting with the database, mapping objects to database records. Implements soft deletes using `deleted_at` as per global guidelines.                                                                                               |
| Database                  | PostgreSQL            | Stores all persistent data, including user accounts, enrollment program configurations, form schemas, and enrollment submissions. All tables use UUID primary keys and audit timestamps `created_at` and `updated_at` as per global guidelines.                  |
| Cache                     |                     | Caching frequently accessed program configurations and branding assets to improve performance.                                                                                                                                                                              |
| File Storage              |                     | Stores uploaded PDF documents and extracted images.                                                                                                                                                                                                                          |
| Message Queue            |                     | Asynchronously processes PDF uploads and AI-powered schema generation to prevent blocking the main request thread.                                                                                                                                                          |

### 4. Key Integrations

- **Claude API** — Generates form schemas from PDF uploads using vision API (REST).
- **Gemini API** — Generates form schemas from PDF uploads using vision API (REST).
- **Logo.dev API** — Fetches company logos based on website URL (REST).
- **ScreenshotOne API** — Captures website screenshots (REST).
- **Contentful** — Stores enrollment program configurations, form schemas, and branding settings (SDK or REST).

### 5. Security Architecture

- **Authentication mechanism** — OAuth 2.0 / SSO is used to authenticate Client Services Admins.
- **Authorization model** — Role-based access control is implemented using Spring Security. Only users with the "admin" role can access and manage enrollment programs.
- **Data protection** — Sensitive data is encrypted at rest and in transit as per global technical guidelines. User inputs are validated server-side to prevent injection attacks. CORS is explicitly configured to prevent cross-site scripting attacks. Never returns passwords, secrets, or internal system IDs in API responses.
- **Audit requirements** — The system maintains audit logs for all administrative actions, including program creation, modification, and deletion.

### 6. Deployment Architecture

```
                                  +---------------------+
                                  |   GCP Load Balancer   |
                                  +---------+-----------+
                                            |
                                            |
                        +--------------------+---------------------+
                        |                                        |
          +---------------------------+  +---------------------------+
          |      Angular Frontend     |  |   Java/Spring Boot APIs   |
          |   (Google Cloud Storage)  |  |   (Google Cloud Run)      |
          +-----------+---------------+  +-----------+---------------+
                      |                           |
                      |                           |
                      +---------------------------+
                                     |
                                     |
                        +-------------+-------------+
                        |    Google Cloud SQL     |
                        |      (PostgreSQL)       |
                        +-------------------------+
```

### 7. Architecture Decisions

- **Choice of Angular for Frontend** — Angular provides a robust framework for building complex, interactive user interfaces, aligning with the admin wizard and dynamic form rendering requirements.
- **Choice of Java/Spring Boot for Backend** — Java/Spring Boot offers a mature and scalable platform for building enterprise-grade APIs and handling complex business logic, making it suitable for the multi-tenant nature of the application.
- **Choice of PostgreSQL for Database** — PostgreSQL provides a reliable and scalable relational database with support for JSON data types, which is beneficial for storing form submissions and program configurations. All tables use UUID primary keys and audit timestamps as per global guidelines.
- **Integration with Claude and Gemini** — Leverage AI providers (Claude and Gemini) to automate form schema generation and branding extraction, significantly reducing manual effort and improving onboarding efficiency.
- **API Versioning** — Implemented API versioning using `/api/v1/` prefix as per global API design guidelines for forward compatibility.
