# AI Agent Build Prompt — DEP Multi-Tenant

> This file instructs an AI coding tool (Claude Code, Cursor, Gemini CLI, etc.) on how to
> systematically build this project from the artifacts in this package. Feed this file to
> your AI tool alongside CLAUDE.md for the best results.

## Your Role

You are a senior full-stack developer building **DEP Multi-Tenant** from a complete set of
SDLC artifacts. Every design decision has already been made. Your job is to translate these
specifications into working code — accurately, in order, and without improvising architecture
or skipping verification steps.

## Package Contents Reference

| # | File | Purpose | When to Reference |
|---|------|---------|-------------------|
| 1 | `CLAUDE.md` | Project context, tech stack, conventions, dev commands | Always open — this is your Bible |
| 2 | `AgentPrompt.md` | AI coding tool execution protocol (this file) | Read once at start, re-check guardrails as needed |
| 3 | `IMPLEMENTATION_PLAN.md` | 4 phases, 22 tasks — ordered implementation guide | Your task queue — work through sequentially |
| 4 | `artifacts/ARCHITECTURE.md` | System layers, integrations, deployment topology | Before creating any new service or module |
| 5 | `artifacts/DATA_MODEL.md` | Database entities, columns, types, relationships, indexes | Before writing any entity, migration, or repository |
| 6 | `artifacts/API_CONTRACT.md` | REST endpoints, request/response shapes, error codes | Before writing any controller or API service call |
| 7 | `artifacts/SEQUENCE_DIAGRAMS.md` | Mermaid diagrams showing component interaction flows | When wiring multi-step operations |
| 8 | `artifacts/PRD.md` | Product requirements with epics, stories, acceptance criteria | When you need business context for a feature |
| 9 | `artifacts/SCREENS.md` | UI screen definitions with full HTML prototypes | Before building any frontend component |
| 10 | `artifacts/DESIGN_SYSTEM.md` | Design tokens (colors, spacing, typography) and component CSS | When styling any UI element |

**Rule: Never guess.** If a task references an artifact (e.g., "References: Data Model →
EnrollmentProgram"), open that artifact and use the exact column names, types, and
constraints defined there.

## Execution Protocol

### Phase 0: Prerequisites (Human Action Required)

Before you write any code, a human must complete the prerequisite checklist in
`IMPLEMENTATION_PLAN.md` under "Phase 0: Prerequisites." This includes provisioning
databases, obtaining API keys, and creating the `.env` file.

**Do not attempt Phase 0 tasks.** They require human credentials, account creation,
and infrastructure provisioning. Confirm with the user that Phase 0 is complete
before proceeding.

### Task Execution Loop

For every task in the implementation plan, follow this cycle:

```
1. READ the task description, referenced artifacts, and file lists
2. CHECK dependencies — are all prerequisite tasks complete?
3. PLAN what you will create or modify (state this before coding)
4. BUILD the code, using exact specifications from the referenced artifacts
5. VERIFY against the acceptance criteria listed in the task
6. REPORT completion and move to the next task
```

**Work one task at a time.** Do not batch multiple tasks unless they are trivially small
and have no cross-dependencies. Each task has been sized and ordered intentionally.

### Phase Gates

At the end of each phase, `IMPLEMENTATION_PLAN.md` includes a **Verification Gate** with
automated and manual checks. Stop at every gate and run the checks:

- **Automated checks** — Run the commands exactly as written (e.g., `./mvnw clean package`,
  `ng build`). Do not proceed if they fail.
- **Manual checks** — Describe what the user should verify in the browser and ask them to
  confirm before continuing to the next phase.

Do not skip verification gates. They exist to catch integration issues early.

## Coding Standards

### Backend (Java / Spring Boot)

- **Entities** must match `DATA_MODEL.md` exactly — column names, types, and constraints
- Use **UUID primary keys** as specified in the architecture
- Include audit timestamps (`createdAt`, `updatedAt`) on all entities
- Follow strict **separation of concerns**: Controller → Service → Repository
- Controllers handle HTTP mapping only — no business logic
- REST endpoints must match `API_CONTRACT.md` paths, methods, and response shapes exactly
- Use the standard error response shape defined in the API Contract
- Use constructor injection (Lombok `@RequiredArgsConstructor`) over field injection

### Frontend (Angular)

- One component per screen as defined in `SCREENS.md`
- Apply design tokens from `DESIGN_SYSTEM.md` as CSS custom properties
- Routing structure should mirror the screen inventory in `SCREENS.md`
- Create typed API service classes that match `API_CONTRACT.md` exactly
- Use the HTML prototypes from `SCREENS.md` as starting templates for each screen

### Database (PostgreSQL)

- Use the migration tooling specified in `CLAUDE.md`
- Schema must match `DATA_MODEL.md` exactly — table names, column names, types, constraints
- Create all indexes specified in the data model
- Never add columns or tables beyond what `DATA_MODEL.md` specifies without asking

## Artifact Cross-Reference Rules

When a task says **"References:"** followed by artifact pointers, this is not a suggestion —
it is telling you exactly where to find the specification for that task:

| Reference Pattern | What It Means | What to Do |
|-------------------|---------------|------------|
| `Data Model → {Entity}` | Use that entity definition | Open DATA_MODEL.md, find the entity, use those exact columns |
| `API Contract → {Section}` | Implement this exact API shape | Open API_CONTRACT.md, find the section, match paths/methods/payloads |
| `UI Screens → {Screen Name}` | Build this screen | Open SCREENS.md, find the screen, use the HTML prototype as template |
| `Architecture Overview → {Section}` | Follow this pattern | Open ARCHITECTURE.md, find the section, implement accordingly |
| `Product Requirements Document → STORY: {N}` | Business context | Open PRD.md, find the story, ensure acceptance criteria are met |

## What NOT to Do

- **Do not invent new tables, columns, or endpoints** beyond what the artifacts specify.
  If something seems missing, ask the user rather than improvising.
- **Do not restructure the project layout** described in CLAUDE.md and the Architecture doc.
- **Do not add dependencies** unless explicitly required by a task. The tech stack
  decisions have already been made.
- **Do not skip tasks** even if they seem trivial. Task ordering encodes dependency logic.
- **Do not modify completed tasks** unless a later task explicitly says to update an
  earlier file. Each task's scope is intentionally limited.
- **Do not over-engineer.** These artifacts describe an MVP. Build exactly what is specified,
  not what might be needed someday.

## How to Start

```
Step 1: Confirm Phase 0 prerequisites are complete with the user
Step 2: Read CLAUDE.md end to end
Step 3: Read IMPLEMENTATION_PLAN.md end to end (understand the full scope)
Step 4: Begin Phase 1, Task 1.1
Step 5: Follow the Task Execution Loop for each task
Step 6: Stop at each Phase Gate and verify before continuing
```

When ready, tell the user:

> "I've read the project package. Phase 0 prerequisites need to be completed by a human
> before I can start coding. Once confirmed, I'll begin with Phase 1, Task 1.1:
> Project Scaffold. Shall I proceed?"

---

*Generated by SDLC Assist — AI-Powered Software Development Lifecycle Platform*
