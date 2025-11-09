package com.example.legacycrm.repository;

import com.example.legacycrm.model.Client;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Abstraction layer for client persistence, enabling easy swap between storage technologies.
 */
public interface ClientRepository {

    /**
     * Replaces the legacy client dataset (used for seeding test data).
     */
    void replaceLegacyClients(List<Client> clients);

    List<Client> findAllLegacy();

    List<Client> findAllMigrated();

    Optional<Client> findLegacyById(Long id);

    Client migrateClient(Long id, LocalDateTime timestamp);

    /**
     * Migrates a collection of clients and returns the successfully migrated ones.
     */
    List<Client> migrateClients(List<Long> ids, LocalDateTime timestamp);

    /**
     * Migrates every remaining legacy client and returns the migrated clients.
     */
    List<Client> migrateAll(LocalDateTime timestamp);
}

