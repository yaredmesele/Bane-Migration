package com.example.legacycrm.api;

import java.time.Instant;
import lombok.Builder;
import lombok.Value;

/**
 * Canonical error representation returned by the REST API.
 */
@Value
@Builder
public class ApiError {
    Instant timestamp;
    int status;
    String error;
    String message;
    String path;
}

