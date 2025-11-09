package com.example.legacycrm.controller;

import com.example.legacycrm.api.MigrationMetricsResponse;
import com.example.legacycrm.service.ClientMigrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metrics")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Metrics")
public class MetricsController {

    private final ClientMigrationService migrationService;

    @GetMapping("/summary")
    @Operation(summary = "Fetch migration summary metrics")
    public MigrationMetricsResponse getMetrics() {
        return migrationService.getMigrationMetrics();
    }
}

