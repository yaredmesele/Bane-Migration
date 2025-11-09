package com.example.legacycrm.controller;

import com.example.legacycrm.api.BulkMigrationRequest;
import com.example.legacycrm.model.Client;
import com.example.legacycrm.service.ClientMigrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/migrate")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Migration")
public class MigrationController {

    private final ClientMigrationService migrationService;

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Migrate a client",
            description = "Moves a client from the legacy list to the migrated list.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Client migrated successfully"),
                @ApiResponse(responseCode = "404", description = "Client not found"),
                @ApiResponse(responseCode = "409", description = "Client already migrated")
            })
    public Client migrateClient(@PathVariable Long id) {
        return migrationService.migrateClient(id);
    }

    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Bulk migrate clients",
            description = "Migrates multiple clients in one request.",
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            required = true,
                            content =
                                    @Content(
                                            schema =
                                                    @Schema(
                                                            implementation =
                                                                    BulkMigrationRequest.class))),
            responses = {
                @ApiResponse(responseCode = "200", description = "Clients migrated successfully"),
                @ApiResponse(responseCode = "404", description = "At least one client not found"),
                @ApiResponse(responseCode = "409", description = "At least one client already migrated")
            })
    public List<Client> migrateClients(@Valid @RequestBody BulkMigrationRequest request) {
        return migrationService.migrateClients(request.getClientIds());
    }

    @PostMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Migrate all clients",
            description = "Migrates every client that has not yet been migrated.")
    public List<Client> migrateAllClients() {
        return migrationService.migrateAllClients();
    }
}

