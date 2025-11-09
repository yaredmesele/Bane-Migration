# API Reference

All endpoints are prefixed with `/api` and return JSON. Pagination parameters (`page`, `size`) are optional and default to `0` and `10` respectively.

## Legacy Clients

### GET `/api/legacy/clients`

Retrieve legacy clients (including migration status) in a paginated response.

#### Query Parameters

| Name     | Type    | Default | Description                                                    |
| -------- | ------- | ------- | -------------------------------------------------------------- |
| `page`   | integer | `0`     | Zero-based page index.                                         |
| `size`   | integer | `10`    | Page size (1–50).                                              |
| `search` | string  | —       | Optional filter (matches ID, `fullName`, or `email` contains). |

#### Response `200 OK`

```json
{
  "content": [
    {
      "id": 1,
      "fullName": "John Doe",
      "email": "john.doe@example.com",
      "migrated": false,
      "migratedAt": null
    }
  ],
  "totalElements": 3,
  "totalPages": 1,
  "page": 0,
  "size": 10,
  "hasNext": false,
  "hasPrevious": false
}
```

## Migrated Clients

### GET `/api/new/clients`

Retrieve clients already migrated to the new system in a paginated response.

#### Query Parameters

Same as `/api/legacy/clients`.

#### Response `200 OK`

```json
{
  "content": [
    {
      "id": 2,
      "fullName": "Jane Smith",
      "email": "jane.smith@example.com",
      "migrated": true,
      "migratedAt": "2025-11-09T07:30:12.123456Z"
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "page": 0,
  "size": 10,
  "hasNext": false,
  "hasPrevious": false
}
```

## Migration Operations

### POST `/api/migrate/{id}`

Migrates a single client by identifier.

#### Path Parameters

| Name | Type | Description               |
| ---- | ---- | ------------------------- |
| `id` | long | Legacy client identifier. |

#### Request Body

None.

#### Response `200 OK`

```json
{
  "id": 1,
  "fullName": "John Doe",
  "email": "john.doe@example.com",
  "migrated": true,
  "migratedAt": "2025-11-09T07:30:12.123456Z"
}
```

#### Errors

| Status          | Description               | Example                                                                                                                              |
| --------------- | ------------------------- | ------------------------------------------------------------------------------------------------------------------------------------ |
| `404 Not Found` | Client ID does not exist. | `{"status":404,"message":"Client with id 99 was not found","error":"Not Found","path":"/api/migrate/99","timestamp":"..."}`          |
| `409 Conflict`  | Client already migrated.  | `{"status":409,"message":"Client with id 1 has already been migrated","error":"Conflict","path":"/api/migrate/1","timestamp":"..."}` |

### POST `/api/migrate/bulk`

Migrates multiple clients in a single call.

#### Request Body

```json
{
  "clientIds": [2, 3]
}
```

`clientIds` must be a non-empty array of unique client IDs.

#### Response `200 OK`

```json
[
  {
    "id": 2,
    "fullName": "Jane Smith",
    "email": "jane.smith@example.com",
    "migrated": true,
    "migratedAt": "2025-11-09T07:45:55.987654Z"
  },
  {
    "id": 3,
    "fullName": "Michael Brown",
    "email": "michael.brown@example.com",
    "migrated": true,
    "migratedAt": "2025-11-09T07:45:55.987654Z"
  }
]
```

#### Errors

| Status            | Description                                | Example                                                                                                                                   |
| ----------------- | ------------------------------------------ | ----------------------------------------------------------------------------------------------------------------------------------------- |
| `400 Bad Request` | Request body missing or `clientIds` empty. | `{"status":400,"message":"clientIds must contain at least one entry","error":"Bad Request","path":"/api/migrate/bulk","timestamp":"..."}` |
| `404 Not Found`   | Any client ID not found.                   | `{"status":404,"message":"Client with id 99 was not found","error":"Not Found","path":"/api/migrate/bulk","timestamp":"..."}`             |
| `409 Conflict`    | Any client already migrated.               | `{"status":409,"message":"Client with id 2 has already been migrated","error":"Conflict","path":"/api/migrate/bulk","timestamp":"..."}`   |

### POST `/api/migrate/all`

Migrates every legacy client that has not yet been migrated.

#### Request Body

None.

#### Response `200 OK`

```json
[
  {
    "id": 2,
    "fullName": "Jane Smith",
    "email": "jane.smith@example.com",
    "migrated": true,
    "migratedAt": "2025-11-09T07:55:23.654321Z"
  },
  {
    "id": 3,
    "fullName": "Michael Brown",
    "email": "michael.brown@example.com",
    "migrated": true,
    "migratedAt": "2025-11-09T07:55:23.654321Z"
  }
]
```

If all clients are already migrated, the response is an empty array.

## Metrics

### GET `/api/metrics/summary`

Returns aggregate migration metrics for dashboard cards.

#### Response `200 OK`

```json
{
  "totalLegacyClients": 1482,
  "clientsMigrated": 891,
  "pendingMigrations": 25,
  "migrationSuccessRate": 98.5
}
```

`migrationSuccessRate` is a percentage (0–100).

## Error Schema

All error responses conform to:

```json
{
  "timestamp": "2025-11-09T07:55:23.654321Z",
  "status": 404,
  "error": "Not Found",
  "message": "Client with id 99 was not found",
  "path": "/api/migrate/99"
}
```

## Notes

- Timestamps use ISO-8601 UTC (Z) format.
- Bulk operations are transactional in memory; if any client fails validation, the entire call returns an error and no clients are migrated.
- Pagination respects `size` up to 50. Requests greater than 50 are capped server-side.
