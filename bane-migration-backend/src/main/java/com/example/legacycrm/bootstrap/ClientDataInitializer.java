package com.example.legacycrm.bootstrap;

import com.example.legacycrm.model.Client;
import com.example.legacycrm.repository.ClientRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Seeds the in-memory store with predictable data every time the application boots.
 */
@Component
@RequiredArgsConstructor
public class ClientDataInitializer {

    private final ClientRepository clientRepository;

    @PostConstruct
    void seedClients() {
        List<Client> initialClients =
                List.of(
                        Client.builder()
                                .id(1L)
                                .fullName("John Doe")
                                .email("john.doe@example.com")
                                .migrated(false)
                                .build(),
                        Client.builder()
                                .id(2L)
                                .fullName("Jane Smith")
                                .email("jane.smith@example.com")
                                .migrated(false)
                                .build(),
                        Client.builder()
                                .id(3L)
                                .fullName("Michael Brown")
                                .email("michael.brown@example.com")
                                .migrated(false)
                                .build(),
                        Client.builder()
                                .id(4L)
                                .fullName("Emily Johnson")
                                .email("emily.johnson@example.com")
                                .migrated(false)
                                .build(),
                        Client.builder()
                                .id(5L)
                                .fullName("David Williams")
                                .email("david.williams@example.com")
                                .migrated(false)
                                .build(),
                        Client.builder()
                                .id(6L)
                                .fullName("Sophia Martinez")
                                .email("sophia.martinez@example.com")
                                .migrated(false)
                                .build(),
                        Client.builder()
                                .id(7L)
                                .fullName("Liam Anderson")
                                .email("liam.anderson@example.com")
                                .migrated(false)
                                .build(),
                        Client.builder()
                                .id(8L)
                                .fullName("Olivia Thomas")
                                .email("olivia.thomas@example.com")
                                .migrated(false)
                                .build(),
                        Client.builder()
                                .id(9L)
                                .fullName("Noah Hernandez")
                                .email("noah.hernandez@example.com")
                                .migrated(false)
                                .build(),
                        Client.builder()
                                .id(10L)
                                .fullName("Ava Lee")
                                .email("ava.lee@example.com")
                                .migrated(false)
                                .build(),
                        Client.builder()
                                .id(11L)
                                .fullName("Ethan Clark")
                                .email("ethan.clark@example.com")
                                .migrated(false)
                                .build(),
                        Client.builder()
                                .id(12L)
                                .fullName("Mia Lewis")
                                .email("mia.lewis@example.com")
                                .migrated(false)
                                .build());

        clientRepository.replaceLegacyClients(initialClients);
    }
}

