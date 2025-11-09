package com.example.legacycrm.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

/**
 * Centralizes OpenAPI metadata for the Swagger UI documentation.
 */
@Configuration
@OpenAPIDefinition(
        info =
                @Info(
                        title = "Bane Migration API",
                        version = "1.0.0",
                        description =
                                "Documentation for the simplified migration backend used in the Bane "
                                        + "technical assessment.",
                        contact =
                                @Contact(
                                        name = "LegacyCRM Support",
                                        email = "support@example.com")),
        servers = {@Server(url = "http://localhost:8080", description = "Local environment")},
        tags = {
            @Tag(name = "Legacy Clients", description = "Operations on legacy system clients"),
            @Tag(name = "New Clients", description = "Operations on migrated clients"),
            @Tag(name = "Migration", description = "Triggers the migration workflow"),
            @Tag(name = "Metrics", description = "Aggregated migration KPIs")
        })
public class OpenApiConfig {}

