package com.example.legacycrm.controller;

import com.example.legacycrm.api.PageResponse;
import com.example.legacycrm.model.Client;
import com.example.legacycrm.service.ClientMigrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/new/clients")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "New Clients")
public class NewClientController {

    private final ClientMigrationService migrationService;

    @GetMapping
    @Operation(
            summary = "List migrated clients",
            description = "Returns paginated clients that already migrated to the new system.")
    public PageResponse<Client> getMigratedClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search) {
        return migrationService.getMigratedClients(page, size, search);
    }
}

