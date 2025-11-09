package com.example.legacycrm.exception;

/**
 * Thrown when a client cannot be located within the legacy data store.
 */
public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(Long id) {
        super("Client with id %d was not found".formatted(id));
    }
}

