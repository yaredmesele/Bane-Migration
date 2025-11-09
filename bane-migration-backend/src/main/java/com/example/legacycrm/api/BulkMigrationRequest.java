package com.example.legacycrm.api;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Request payload used for migrating multiple clients in a single call.
 */
@Value
@Builder
@Jacksonized
public class BulkMigrationRequest {
    @NotEmpty(message = "clientIds must contain at least one entry")
    List<Long> clientIds;
}

