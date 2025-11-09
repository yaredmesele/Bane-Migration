# Bane Migration Backend (Spring Boot)

A modular Spring Boot backend that simulates an internal client migration tool for LegacyCRM → NewConnect. The service exposes REST endpoints backed by an in-memory repository, emphasising clean architecture so the persistence layer can be replaced with minimal effort.

## Stack

- Java 21, Spring Boot 3.3.5
- Spring Web, Validation, Lombok
- springdoc-openapi for Swagger UI
- Maven build (`mvnw.cmd` wrapper checked in)

## Quick Start

```bash
# run tests
.\mvnw.cmd -q test

# start API on :8080
.\mvnw.cmd spring-boot:run
```

Swagger UI is available at `http://localhost:8080/swagger-ui/index.html`. The OpenAPI document lives at `/v3/api-docs`.

## API Overview

| Method | Endpoint              | Description                                       |
| ------ | --------------------- | ------------------------------------------------- |
| GET    | `/api/legacy/clients` | Paginated list of legacy clients (`page`, `size`) |
| GET    | `/api/new/clients`    | Paginated list of migrated clients                |
| POST   | `/api/migrate/{id}`   | Migrates a single client                          |
| POST   | `/api/migrate/bulk`   | Migrates specific clients in a batch              |
| POST   | `/api/migrate/all`    | Migrates every remaining legacy client            |

### Sample Payloads

Bulk migrate request:

```json
{
  "clientIds": [2, 3]
}
```

Responses use the shared `Client` structure and paginated listings use the `PageResponse<T>` envelope.

## Winning Points / Key Features

- **Modular architecture** — `ClientRepository` abstraction decouples storage; service/controllers stay untouched when swapping persistence.
- **In-memory implementation** that is thread-safe and mirrors future DB semantics.
- **Bulk operations** — `/bulk` (selected clients) and `/all` (remaining clients) for efficient migrations.
- **Pagination layer** shared for legacy and migrated listings with configurable defaults.
- **Comprehensive validation & error handling** returning structured `ApiError` payloads (400/404/409/500).
- **OpenAPI documentation** with tagged operations and Swagger UI for quick exploration.
- **Automated tests** — controller integration tests cover happy paths and edge cases (conflict, not found, bad request), plus `/v3/api-docs` regression guard.
- **Seed data** — predictable startup dataset (John Doe, Jane Smith, Michael Brown).
- **Logging** — migration success logs for every client, including bulk operations.

## Project Structure

```
src/main/java/com/example/legacycrm
├── api/                # DTOs (PageResponse, BulkMigrationRequest, ApiError)
├── bootstrap/          # Startup data seeding
├── config/             # OpenAPI configuration
├── controller/         # REST controllers
├── exception/          # Domain exceptions + global handler
├── model/              # Domain model (Client)
├── repository/         # Repository abstraction + in-memory implementation
└── service/            # Business logic (ClientMigrationService)
```

Tests live under `src/test/java/com/example/legacycrm`, including end-to-end coverage for migration endpoints.

## Replacing In-Memory Storage with PostgreSQL

Thanks to the repository abstraction, swapping storage is straightforward:

1. **Add database & JPA tooling**

   - Include `spring-boot-starter-data-jpa` and the PostgreSQL driver in `pom.xml`.
   - Optionally add Flyway/Liquibase for schema migrations.

2. **Configure properties**

   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/legacycrm
       username: bane
       password: secret
     jpa:
       hibernate:
         ddl-auto: update # prefer validate/none with migrations later
       properties:
         hibernate:
           dialect: org.hibernate.dialect.PostgreSQLDialect
   ```

3. **Persistable model**

   - Either annotate the existing `Client` (`@Entity`, `@Table`, etc.).
   - Or create a dedicated `ClientEntity` and convert to/from the domain model to keep persistence concerns isolated.

4. **Database-backed repository**

   - Implement `ClientRepository` via a `PostgresClientRepository`.
   - Use Spring Data JPA (`extends JpaRepository<ClientEntity, Long>`) with an adapter, or a custom `@Repository` using `EntityManager`.
   - Implement the same methods (`findAllLegacy`, `findAllMigrated`, `migrateClient`, `migrateClients`, `migrateAll`) and rely on transactions for concurrency.

5. **Switch wiring**

   - Mark the new bean `@Primary` (or inject with `@Qualifier`) so `ClientMigrationService` picks it up.
   - Keep `InMemoryClientRepository` behind a profile (e.g., `@Profile("in-memory")`) for tests or demos.

6. **Startup data**

   - Seed via migration scripts or adjust `ClientDataInitializer` to use the new repository within a transactional context.

7. **Testing**
   - Add integration tests using Testcontainers or an embedded database (H2 in PostgreSQL mode) to verify the JPA implementation.
   - Run `mvn test` and `mvn spring-boot:run` to ensure `/v3/api-docs` and endpoints behave as before.

With this approach, controllers, services, and DTOs remain unchanged—you only replace the repository implementation and configuration.

## Validation, Errors & Logging

- All validation errors return a 400 with aggregated messages.
- Missing clients return 404; already migrated clients return 409.
- Unexpected errors fall back to a 500 with timestamped `ApiError`.
- Every successful migration logs `Migrated client {id} successfully` (bulk operations log each ID and a summary).

## Testing

```bash
.\mvnw.cmd -q test
```

Integration tests boot the application on random ports and exercise the REST endpoints end-to-end.

## Next Steps (Optional Enhancements)

- Implement the PostgreSQL adapter with Testcontainers coverage.
- Add filtering/sorting to the list endpoints.
- Build the Vue/Vuetify frontend described in the original brief and point it at this API.
- Automate quality checks (GitHub Actions, Sonar, etc.).

---

Built as part of the Bane Full-Stack timed assessment backend. Feel free to adapt or extend it for real migration workloads.
