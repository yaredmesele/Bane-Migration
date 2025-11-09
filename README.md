# Bane Migration Suite

A full-stack application demonstrating a client migration workflow from a legacy CRM into a new platform. The repository contains a Spring Boot backend and a Vue + Vuetify frontend that consume the same migration APIs.

## Repository Layout

```
.
├── bane-migration-backend/   # Spring Boot REST API (Java 21, Maven)
└── bane-migration-frontend/  # Vue 3 + Vuetify admin UI (Vite toolchain)
```

## Prerequisites

- Java 21+
- Node.js 18+ (20 LTS recommended)
- Maven Wrapper (`./mvnw.cmd`) and npm (or preferred package manager)

## Quick Start

### 1. Back-End API

```powershell
cd bane-migration-backend
.\mvnw.cmd -q test
.\mvnw.cmd spring-boot:run
```

- REST API served on `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI spec: `http://localhost:8080/v3/api-docs`

See `bane-migration-backend/README.md` for architecture, endpoints, and migration strategy notes.

### 2. Front-End UI

```powershell
cd bane-migration-frontend
npm install
npm run dev
```

- Configure `VITE_API_BASE_URL` in `.env` (defaults to `http://localhost:8080/api`)
- Vite dev server available at `http://localhost:3000`

Refer to `bane-migration-frontend/README.md` for tooling details, project structure, and additional scripts.

## Development Tips

- Backend tests: `.\mvnw.cmd -q test`
- Frontend type checks & linting follow the Vuetify starter defaults (see package scripts).
- Consider running both services concurrently for end-to-end verification.

## Next Steps

- Swap the in-memory backend repository with PostgreSQL (guidance in backend README).
- Reintroduce or extend bulk migration flows once requirements stabilize.
- Add automated integration tests across the stack (Testcontainers, Playwright/Cypress).
