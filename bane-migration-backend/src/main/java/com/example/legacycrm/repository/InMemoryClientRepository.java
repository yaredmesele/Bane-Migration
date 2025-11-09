package com.example.legacycrm.repository;

import com.example.legacycrm.exception.ClientAlreadyMigratedException;
import com.example.legacycrm.exception.ClientNotFoundException;
import com.example.legacycrm.model.Client;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.springframework.stereotype.Repository;

/**
 * In-memory implementation of {@link ClientRepository}. Designed for easy replacement with a
 * database-backed repository in the future.
 */
@Repository
public class InMemoryClientRepository implements ClientRepository {

    private final Map<Long, Client> legacyClients = new LinkedHashMap<>();
    private final Map<Long, Client> migratedClients = new LinkedHashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void replaceLegacyClients(List<Client> clients) {
        lock.writeLock().lock();
        try {
            legacyClients.clear();
            clients.forEach(client -> legacyClients.put(client.getId(), client.copy()));
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public List<Client> findAllLegacy() {
        lock.readLock().lock();
        try {
            return legacyClients.values().stream().map(Client::copy).toList();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Client> findAllMigrated() {
        lock.readLock().lock();
        try {
            return migratedClients.values().stream().map(Client::copy).toList();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Optional<Client> findLegacyById(Long id) {
        lock.readLock().lock();
        try {
            return Optional.ofNullable(legacyClients.get(id)).map(Client::copy);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Client migrateClient(Long id, LocalDateTime timestamp) {
        lock.writeLock().lock();
        try {
            return migrateClientInternal(id, timestamp);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public List<Client> migrateClients(List<Long> ids, LocalDateTime timestamp) {
        lock.writeLock().lock();
        try {
            List<Client> migrated = new ArrayList<>(ids.size());
            for (Long id : ids) {
                migrated.add(migrateClientInternal(id, timestamp));
            }
            return migrated;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public List<Client> migrateAll(LocalDateTime timestamp) {
        lock.writeLock().lock();
        try {
            List<Long> pending =
                    legacyClients.values().stream()
                            .filter(client -> !client.isMigrated())
                            .map(Client::getId)
                            .toList();
            List<Client> migrated = new ArrayList<>(pending.size());
            for (Long id : pending) {
                migrated.add(migrateClientInternal(id, timestamp));
            }
            return migrated;
        } finally {
            lock.writeLock().unlock();
        }
    }

    private Client migrateClientInternal(Long id, LocalDateTime timestamp) {
        Client existing = legacyClients.get(id);
        if (existing == null) {
            throw new ClientNotFoundException(id);
        }

        if (existing.isMigrated()) {
            throw new ClientAlreadyMigratedException(id);
        }

        Client migrated =
                existing.toBuilder().migrated(true).migratedAt(timestamp).build();
        legacyClients.put(id, migrated);
        migratedClients.put(id, migrated);
        return migrated.copy();
    }
}

