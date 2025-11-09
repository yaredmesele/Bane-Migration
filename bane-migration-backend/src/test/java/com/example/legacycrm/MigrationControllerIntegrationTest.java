package com.example.legacycrm;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.legacycrm.model.Client;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MigrationControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void shouldReturnLegacyClientsWithDefaultPagination() throws Exception {
        ResponseEntity<String> response =
                restTemplate.getForEntity(url("/api/legacy/clients"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        JsonNode payload = objectMapper.readTree(response.getBody());
        assertThat(payload.get("totalElements").asInt()).isEqualTo(12);
        assertThat(payload.get("content").size()).isEqualTo(10);
    }

    @Test
    void shouldMigrateClientAndExposeInNewClientsList() throws Exception {
        ResponseEntity<Client> migrateResponse =
                restTemplate.postForEntity(url("/api/migrate/1"), null, Client.class);

        assertThat(migrateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(migrateResponse.getBody()).isNotNull();
        assertThat(migrateResponse.getBody().isMigrated()).isTrue();

        ResponseEntity<String> newClientsResponse =
                restTemplate.getForEntity(url("/api/new/clients"), String.class);

        JsonNode payload = objectMapper.readTree(newClientsResponse.getBody());
        assertThat(payload.get("totalElements").asInt()).isEqualTo(1);
        assertThat(payload.get("content").get(0).get("id").asLong()).isEqualTo(1L);
    }

    @Test
    void shouldReturnNotFoundWhenClientDoesNotExist() {
        ResponseEntity<String> response =
                restTemplate.postForEntity(url("/api/migrate/99"), null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldReturnConflictWhenClientAlreadyMigrated() {
        restTemplate.postForEntity(url("/api/migrate/1"), null, Client.class);

        ResponseEntity<String> secondAttempt =
                restTemplate.postForEntity(url("/api/migrate/1"), null, String.class);

        assertThat(secondAttempt.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void shouldBulkMigrateClients() throws Exception {
        ResponseEntity<Client[]> response =
                restTemplate.postForEntity(
                        url("/api/migrate/bulk"),
                        jsonEntity(Map.of("clientIds", List.of(2L, 3L))),
                        Client[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(2);

        ResponseEntity<String> newClientsResponse =
                restTemplate.getForEntity(url("/api/new/clients"), String.class);
        JsonNode payload = objectMapper.readTree(newClientsResponse.getBody());
        assertThat(payload.get("totalElements").asInt()).isEqualTo(2);
    }

    @Test
    void shouldRejectBulkMigrationWhenRequestEmpty() {
        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        url("/api/migrate/bulk"),
                        jsonEntity(Map.of("clientIds", List.of())),
                        String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldReturnConflictWhenBulkMigrationContainsAlreadyMigratedClient() {
        restTemplate.postForEntity(url("/api/migrate/2"), null, Client.class);

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        url("/api/migrate/bulk"),
                        jsonEntity(Map.of("clientIds", List.of(2L, 3L))),
                        String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void shouldMigrateAllRemainingClients() throws Exception {
        restTemplate.postForEntity(url("/api/migrate/1"), null, Client.class);

        ResponseEntity<Client[]> response =
                restTemplate.postForEntity(url("/api/migrate/all"), null, Client[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(11);

        ResponseEntity<String> legacyResponse =
                restTemplate.getForEntity(url("/api/legacy/clients?size=50"), String.class);
        JsonNode legacyPayload = objectMapper.readTree(legacyResponse.getBody());
        long migratedCount =
                StreamSupport.stream(legacyPayload.get("content").spliterator(), false)
                        .filter(node -> node.get("migrated").asBoolean())
                        .count();
        assertThat(migratedCount).isEqualTo(12);
    }

    @Test
    void shouldFilterLegacyClientsBySearchTerm() throws Exception {
        ResponseEntity<String> response =
                restTemplate.getForEntity(url("/api/legacy/clients?search=John"), String.class);

        JsonNode payload = objectMapper.readTree(response.getBody());
        assertThat(payload.get("content").size()).isEqualTo(2);
        assertThat(payload.get("content").get(0).get("fullName").asText()).contains("John");
    }

    @Test
    void shouldFilterMigratedClientsByIdSearch() throws Exception {
        restTemplate.postForEntity(url("/api/migrate/1"), null, Client.class);

        ResponseEntity<String> response =
                restTemplate.getForEntity(url("/api/new/clients?search=1"), String.class);

        JsonNode payload = objectMapper.readTree(response.getBody());
        assertThat(payload.get("content").size()).isEqualTo(1);
        assertThat(payload.get("content").get(0).get("id").asLong()).isEqualTo(1L);
    }

    @Test
    void shouldReturnMigrationMetricsSummary() throws Exception {
        restTemplate.postForEntity(
                url("/api/migrate/bulk"),
                jsonEntity(Map.of("clientIds", List.of(1L, 2L))),
                Client[].class);

        ResponseEntity<String> response =
                restTemplate.getForEntity(url("/api/metrics/summary"), String.class);

        JsonNode payload = objectMapper.readTree(response.getBody());
        assertThat(payload.get("totalLegacyClients").asLong()).isEqualTo(12);
        assertThat(payload.get("clientsMigrated").asLong()).isEqualTo(2);
        assertThat(payload.get("pendingMigrations").asLong()).isEqualTo(10);
        assertThat(payload.get("migrationSuccessRate").asDouble()).isGreaterThan(0.0);
    }

    private HttpEntity<Map<String, Object>> jsonEntity(Map<String, Object> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }
}

