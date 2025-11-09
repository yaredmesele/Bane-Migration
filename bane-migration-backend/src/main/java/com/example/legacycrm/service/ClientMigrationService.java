package com.example.legacycrm.service;

import com.example.legacycrm.api.MigrationMetricsResponse;
import com.example.legacycrm.api.PageResponse;
import com.example.legacycrm.model.Client;
import com.example.legacycrm.repository.ClientRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Orchestrates read and migration operations for clients.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ClientMigrationService {

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 50;

    private final ClientRepository clientRepository;

    public PageResponse<Client> getLegacyClients(int page, int size, String search) {
        return paginate(applySearch(clientRepository.findAllLegacy(), search), page, size);
    }

    public PageResponse<Client> getMigratedClients(int page, int size, String search) {
        return paginate(applySearch(clientRepository.findAllMigrated(), search), page, size);
    }

    public Client migrateClient(Long id) {
        Client migrated = clientRepository.migrateClient(id, LocalDateTime.now());
        log.info("Migrated client {} successfully", id);
        return migrated;
    }

    public List<Client> migrateClients(List<Long> ids) {
        Objects.requireNonNull(ids, "ids must not be null");
        if (ids.isEmpty()) {
            return List.of();
        }

        List<Client> migrated =
                clientRepository.migrateClients(ids, LocalDateTime.now());
        migrated.forEach(client -> log.info("Migrated client {} successfully", client.getId()));
        return migrated;
    }

    public List<Client> migrateAllClients() {
        List<Client> migrated = clientRepository.migrateAll(LocalDateTime.now());
        migrated.forEach(client -> log.info("Migrated client {} successfully", client.getId()));
        log.info("Bulk migration completed for {} clients", migrated.size());
        return migrated;
    }

    public MigrationMetricsResponse getMigrationMetrics() {
        List<Client> legacy = clientRepository.findAllLegacy();
        List<Client> migrated = clientRepository.findAllMigrated();

        long totalLegacy = legacy.size();
        long migratedCount = migrated.size();
        long pending = legacy.stream().filter(client -> !client.isMigrated()).count();
        double successRate = totalLegacy == 0 ? 0.0 : (double) migratedCount / totalLegacy * 100.0;

        return MigrationMetricsResponse.builder()
                .totalLegacyClients(totalLegacy)
                .clientsMigrated(migratedCount)
                .pendingMigrations(pending)
                .migrationSuccessRate(successRate)
                .build();
    }

    private <T> PageResponse<T> paginate(List<T> items, int requestedPage, int requestedSize) {
        int safePage = Math.max(0, requestedPage);
        int safeSize = normalizePageSize(requestedSize);

        int fromIndex = Math.min(safePage * safeSize, items.size());
        int toIndex = Math.min(fromIndex + safeSize, items.size());

        List<T> pagedContent = items.subList(fromIndex, toIndex).stream().toList();
        long totalElements = items.size();
        int totalPages = (int) Math.ceil(totalElements / (double) safeSize);

        return PageResponse.<T>builder()
                .content(pagedContent)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .page(safePage)
                .size(safeSize)
                .hasNext(safePage + 1 < totalPages)
                .hasPrevious(safePage > 0)
                .build();
    }

    private int normalizePageSize(int requestedSize) {
        if (requestedSize <= 0) {
            return DEFAULT_PAGE_SIZE;
        }
        return Math.min(requestedSize, MAX_PAGE_SIZE);
    }

    private List<Client> applySearch(List<Client> clients, String search) {
        if (search == null || search.isBlank()) {
            return clients;
        }

        String trimmed = search.trim();
        String lowered = trimmed.toLowerCase(Locale.ROOT);

        Stream<Client> stream = clients.stream();
        stream =
                stream.filter(
                        client -> {
                            boolean matchesName =
                                    client.getFullName() != null
                                            && client.getFullName()
                                                    .toLowerCase(Locale.ROOT)
                                                    .contains(lowered);
                            boolean matchesEmail =
                                    client.getEmail() != null
                                            && client.getEmail()
                                                    .toLowerCase(Locale.ROOT)
                                                    .contains(lowered);

                            boolean matchesId = false;
                            if (client.getId() != null) {
                                matchesId = client.getId().toString().equals(trimmed);
                            }

                            return matchesName || matchesEmail || matchesId;
                        });

        return stream.toList();
    }

}

