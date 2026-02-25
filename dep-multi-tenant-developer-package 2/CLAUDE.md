# CLAUDE.md

## Project Overview
HealthBridge DEP Multi-Tenant is a web application for healthcare clients to digitize patient and provider enrollment using AI. It provides an admin wizard for configuring enrollment programs and public-facing enrollment forms.

## Tech Stack
- Backend: Java / Spring Boot
- Frontend: Angular
- Database: PostgreSQL
- Deployment: Google Cloud (GCP)
- Authentication: OAuth 2.0 / SSO
- API Style: REST

## Project Structure
```
backend/
  src/main/java/com/depmt/
frontend/
  src/app/
docker-compose.yml
.gitignore
```

## Development Commands
```
# Backend
./mvnw spring-boot:run
# Frontend
npm start
# Database
docker-compose up -d postgres
```

## Coding Conventions
- Follow RESTful API principles.
- Implement soft deletes with `deleted_at`.
- Use UUID primary keys and audit timestamps.
- Separate concerns: controllers, services, repositories.

## References
- See IMPLEMENTATION_PLAN.md for implementation phases and tasks.
- See artifacts directory for API contracts and data models.

## Prerequisites
See Phase 0 in IMPLEMENTATION_PLAN.md.
Required environment variables must exist in .env before starting.
```
DATABASE_URL
CONTENTFUL_API_KEY
CLAUDE_API_KEY
GEMINI_API_KEY
LOGO_DEV_API_KEY
SCREENSHOTONE_API_KEY
ADMIN_USERNAME
ADMIN_PASSWORD
```