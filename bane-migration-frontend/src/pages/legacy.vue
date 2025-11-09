<script setup lang="ts">
import { onMounted, watch } from "vue";
import LegacyClientsPanel from "@/components/migration/LegacyClientsPanel.vue";
import { useMigrationDashboard } from "@/composables/useMigrationDashboard";

const {
  legacyResource,
  legacySearch,
  migratingIds,
  migrateAllLoading,
  migrateClient,
  migrateAll,
  refreshAll,
} = useMigrationDashboard();

onMounted(async () => {
  await refreshAll();
});

function handleLegacyPageChange(page: number) {
  legacyResource.setPage(page);
}

function handleLegacyItemsPerPageChange(size: number) {
  legacyResource.setItemsPerPage(size);
}
</script>

<template>
  <div class="d-flex flex-column ga-6">
    <div>
      <h1 class="text-h4 font-weight-black mb-1">Legacy Clients</h1>
      <p class="text-body-1 text-medium-emphasis mb-0">
        Review clients still in LegacyCRM, initiate individual migrations, and
        monitor progress.
      </p>
    </div>

    <LegacyClientsPanel
      :clients="legacyResource.items.value"
      :total-items="legacyResource.state.totalItems"
      :page="legacyResource.state.page"
      :items-per-page="legacyResource.state.itemsPerPage"
      :loading="legacyResource.loading.value"
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
  </div>
</template>
