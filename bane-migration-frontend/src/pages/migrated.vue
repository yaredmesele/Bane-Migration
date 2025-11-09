<script setup lang="ts">
import { onMounted } from "vue";
import MigratedClientsPanel from "@/components/migration/MigratedClientsPanel.vue";
import { useMigrationDashboard } from "@/composables/useMigrationDashboard";

const { migratedResource, migratedSearch, refreshAll } =
  useMigrationDashboard();

function handlePageChange(page: number) {
  migratedResource.setPage(page);
}

function handleItemsPerPageChange(size: number) {
  migratedResource.setItemsPerPage(size);
}

onMounted(async () => {
  await refreshAll();
});
</script>

<template>
  <div class="d-flex flex-column ga-6">
    <div>
      <h1 class="text-h4 font-weight-black mb-1">Migrated Clients</h1>
      <p class="text-body-1 text-medium-emphasis mb-0">
        Audit clients already migrated to NewConnect and verify their migration
        timestamps.
      </p>
    </div>

    <MigratedClientsPanel
      :clients="migratedResource.items.value"
      :total-items="migratedResource.state.totalItems"
      :page="migratedResource.state.page"
      :items-per-page="migratedResource.state.itemsPerPage"
      :loading="migratedResource.loading.value"
      :error-message="migratedResource.error.value"
      :search-term="migratedSearch"
      @update:page="handlePageChange"
      @update:items-per-page="handleItemsPerPageChange"
      @update:search="migratedSearch = $event ?? ''"
    />
  </div>
</template>
