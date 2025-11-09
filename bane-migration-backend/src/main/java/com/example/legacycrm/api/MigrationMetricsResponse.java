package com.example.legacycrm.api;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MigrationMetricsResponse {
    long totalLegacyClients;
    long clientsMigrated;
    long pendingMigrations;
    double migrationSuccessRate;
}

