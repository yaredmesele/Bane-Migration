package com.example.legacycrm.exception;

/**
 * Thrown when attempting to migrate a client that has already been migrated.
 */
public class ClientAlreadyMigratedException extends RuntimeException {

    public ClientAlreadyMigratedException(Long id) {
        super("Client with id %d has already been migrated".formatted(id));
    }
}

