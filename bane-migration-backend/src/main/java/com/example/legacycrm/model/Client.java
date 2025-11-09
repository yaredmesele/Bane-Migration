package com.example.legacycrm.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Domain model representing a client within the migration context.
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private Long id;
    private String fullName;
    private String email;
    private boolean migrated;
    private LocalDateTime migratedAt;

    /**
     * Creates a defensive copy of the current client instance.
     *
     * @return cloned {@link Client}
     */
    public Client copy() {
        return this.toBuilder().build();
    }
}

