import { computed, ref, watch } from "vue";
import type { Client } from "@/types/client";
import type { MigrationMetrics } from "@/types/metrics";
import {
  fetchLegacyClients,
  fetchMigratedClients,
  fetchMigrationMetrics,
  migrateAll,
  migrateClient,
  type PaginationQuery,
} from "@/services/clientService";
import { extractErrorMessage } from "@/utils/error";
import { useNotifier } from "./useNotifier";
import { usePaginatedResource } from "./usePaginatedResource";

const DEFAULT_PAGE_SIZE = 10;

export function useMigrationDashboard() {
  const notifier = useNotifier();

  const legacySearch = ref("");
  const migratedSearch = ref("");
  const metrics = ref<MigrationMetrics | null>(null);
  const metricsLoading = ref(false);
  const metricsError = ref<string | null>(null);

  const legacyResource = usePaginatedResource<Client>((page, size) =>
    fetchLegacyClients(
      normalizePagination({ page, size, search: legacySearch.value })
    )
  );
  const migratedResource = usePaginatedResource<Client>((page, size) =>
    fetchMigratedClients(
      normalizePagination({ page, size, search: migratedSearch.value })
    )
  );

  const migratingIds = ref<Set<number>>(new Set());
  const migrateAllLoading = ref(false);

  const totalClients = computed(
    () => legacyResource.state.totalItems + migratedResource.state.totalItems
  );
  const successRateFromData = computed(() => {
    const total = totalClients.value;
    if (total === 0) {
      return 0;
    }

    return (migratedResource.state.totalItems / total) * 100;
  });

  const metricsSummary = computed<MigrationMetrics>(() => ({
    totalLegacyClients:
      metrics.value?.totalLegacyClients ?? legacyResource.state.totalItems,
    clientsMigrated:
      metrics.value?.clientsMigrated ?? migratedResource.state.totalItems,
    pendingMigrations:
      metrics.value?.pendingMigrations ??
      Math.max(
        legacyResource.state.totalItems - migratedResource.state.totalItems,
        0
      ),
    migrationSuccessRate:
      metrics.value?.migrationSuccessRate ?? successRateFromData.value,
  }));

  async function loadMetrics() {
    metricsLoading.value = true;
    metricsError.value = null;
    try {
      metrics.value = await fetchMigrationMetrics();
    } catch (error) {
      metricsError.value = extractErrorMessage(error, "Unable to load metrics");
    } finally {
      metricsLoading.value = false;
    }
  }

  async function refreshAll() {
    await Promise.all([
      legacyResource.refresh(),
      migratedResource.refresh(),
      loadMetrics(),
    ]);
  }

  async function handleSingleMigration(id: number) {
    if (migratingIds.value.has(id)) {
      return;
    }

    migratingIds.value.add(id);

    try {
      const client = await migrateClient(id);
      notifier.success(`Client "${client.fullName}" migrated successfully`);
      await refreshAll();
    } catch (error) {
      notifier.error(extractErrorMessage(error));
    } finally {
      migratingIds.value.delete(id);
    }
  }

  async function handleMigrateAll() {
    migrateAllLoading.value = true;

    try {
      const migrated = await migrateAll();
      if (migrated.length === 0) {
        notifier.info("All legacy clients have already been migrated");
      } else {
        notifier.success(`Migrated ${migrated.length} client(s) successfully`);
      }
      await refreshAll();
    } catch (error) {
      notifier.error(extractErrorMessage(error));
    } finally {
      migrateAllLoading.value = false;
    }
  }

  watch(legacySearch, () => {
    legacyResource.setPage(1);
    legacyResource.refresh();
  });

  watch(migratedSearch, () => {
    migratedResource.setPage(1);
    migratedResource.refresh();
  });

  function normalizePagination({
    page,
    size,
    search,
  }: PaginationQuery): PaginationQuery {
    return {
      page,
      search,
      size: Math.min(size ?? DEFAULT_PAGE_SIZE, 50),
    };
  }

  const successRate = computed(() => metricsSummary.value.migrationSuccessRate);

  return {
    legacyResource,
    migratedResource,
    legacySearch,
    migratedSearch,
    metrics: metricsSummary,
    metricsLoading,
    metricsError,
    migratingIds,
    migrateAllLoading,
    successRate,
    migrateClient: handleSingleMigration,
    migrateAll: handleMigrateAll,
    loadMetrics,
    refreshAll,
  };
}
