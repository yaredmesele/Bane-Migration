<script setup lang="ts">
import { computed, onMounted, ref, watch } from "vue";
import DashboardSummaryCards from "@/components/dashboard/DashboardSummaryCards.vue";
import LegacyClientsPanel from "@/components/migration/LegacyClientsPanel.vue";
import MigratedClientsPanel from "@/components/migration/MigratedClientsPanel.vue";
import { useMigrationDashboard } from "@/composables/useMigrationDashboard";

const tabs = [
  { label: "Legacy Clients", value: "legacy" as const },
  { label: "Migrated Clients", value: "migrated" as const },
];

const activeTab = ref<"legacy" | "migrated">("legacy");

const {
  legacyResource,
  migratedResource,
  legacySearch,
  migratedSearch,
  metrics,
  metricsLoading,
  metricsError,
  migratingIds,
  migrateAllLoading,
  successRate,
  migrateClient,
  migrateAll,
  refreshAll,
} = useMigrationDashboard();

const totalLegacyClients = computed(() => metrics.value.totalLegacyClients);
const totalMigratedClients = computed(() => metrics.value.clientsMigrated);
const pendingMigrations = computed(() => metrics.value.pendingMigrations);

onMounted(async () => {
  await refreshAll();
});

function handleLegacyPageChange(page: number) {
  legacyResource.setPage(page);
}

function handleLegacyItemsPerPageChange(size: number) {
  legacyResource.setItemsPerPage(size);
}

function handleMigratedPageChange(page: number) {
  migratedResource.setPage(page);
}

function handleMigratedItemsPerPageChange(size: number) {
  migratedResource.setItemsPerPage(size);
}
</script>

<template>
  <div class="d-flex flex-column ga-6">
    <header class="d-flex flex-wrap align-center ga-4">
      <div>
        <h1 class="text-h4 font-weight-black mb-1">Migration Dashboard</h1>
        <p class="text-body-1 text-medium-emphasis mb-0">
          Monitor migration progress across systems and trigger client
          migrations.
        </p>
      </div>
      <v-spacer />
    </header>

    <DashboardSummaryCards
      :total-legacy="totalLegacyClients"
      :total-migrated="totalMigratedClients"
      :pending-migrations="pendingMigrations"
      :success-rate="successRate"
    />

    <v-alert v-if="metricsError" type="warning" variant="tonal" border="start">
      {{ metricsError }}
    </v-alert>

    <v-card flat class="rounded-xxl overflow-hidden">
      <v-tabs v-model="activeTab" color="primary" density="comfortable">
        <v-tab
          v-for="tab in tabs"
          :key="tab.value"
          :value="tab.value"
          class="text-capitalize"
        >
          {{ tab.label }}
        </v-tab>
      </v-tabs>

      <v-divider />

      <v-window v-model="activeTab">
        <v-window-item value="legacy">
          <LegacyClientsPanel
            :clients="legacyResource.items.value"
            :total-items="legacyResource.state.totalItems"
            :page="legacyResource.state.page"
            :items-per-page="legacyResource.state.itemsPerPage"
            :loading="legacyResource.loading.value || metricsLoading"
            :migrating-ids="migratingIds"
            :migrate-all-loading="migrateAllLoading"
            :error-message="legacyResource.error.value"
            :search-term="legacySearch"
            @update:page="handleLegacyPageChange"
            @update:items-per-page="handleLegacyItemsPerPageChange"
            @update:search="legacySearch = $event ?? ''"
            @migrate="migrateClient"
            @migrate-all="migrateAll"
          />
        </v-window-item>

        <v-window-item value="migrated">
          <MigratedClientsPanel
            :clients="migratedResource.items.value"
            :total-items="migratedResource.state.totalItems"
            :page="migratedResource.state.page"
            :items-per-page="migratedResource.state.itemsPerPage"
            :loading="migratedResource.loading.value || metricsLoading"
            :error-message="migratedResource.error.value"
            :search-term="migratedSearch"
            @update:page="handleMigratedPageChange"
            @update:items-per-page="handleMigratedItemsPerPageChange"
            @update:search="migratedSearch = $event ?? ''"
          />
        </v-window-item>
      </v-window>
    </v-card>
  </div>
</template>
