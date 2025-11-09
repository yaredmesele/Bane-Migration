<script setup lang="ts">
import LegacyClientsTable from "./LegacyClientsTable.vue";
import type { Client } from "@/types/client";

const props = defineProps<{
  clients: Client[];
  totalItems: number;
  page: number;
  itemsPerPage: number;
  loading: boolean;
  migratingIds: Set<number>;
  migrateAllLoading: boolean;
  errorMessage?: string | null;
  searchTerm?: string;
}>();

const emit = defineEmits<{
  (event: "update:page", value: number): void;
  (event: "update:items-per-page", value: number): void;
  (event: "migrate", id: number): void;
  (event: "migrate-all"): void;
  (event: "update:search", value: string): void;
}>();

function handleSearchInput(value: string) {
  emit("update:search", value);
}
</script>

<template>
  <v-card elevation="0" rounded="xl">
    <v-card-text class="pb-0">
      <div class="d-flex flex-wrap align-center ga-3 mb-4">
        <div>
          <h2 class="text-h6 font-weight-bold mb-1">Legacy Client Migration</h2>
          <p class="text-body-2 text-medium-emphasis mb-0">
            Manage legacy clients and migrate them to NewConnect individually.
          </p>
        </div>
        <v-spacer />
        <v-btn
          color="primary"
          variant="outlined"
          :loading="migrateAllLoading"
          @click="emit('migrate-all')"
        >
          <v-icon icon="mdi-rocket-launch-outline" start />
          Migrate All
        </v-btn>
      </div>

      <v-alert
        v-if="props.errorMessage"
        type="error"
        variant="tonal"
        border="start"
        class="mb-4"
      >
        {{ props.errorMessage }}
      </v-alert>

      <v-text-field
        :model-value="props.searchTerm"
        class="mb-4 search-input"
        density="comfortable"
        variant="outlined"
        prepend-inner-icon="mdi-magnify"
        placeholder="Search by name, email, or client ID"
        color="primary"
        rounded="pill"
        hide-details
        clearable
        @update:model-value="handleSearchInput"
      />

      <LegacyClientsTable
        :items="clients"
        :total-items="totalItems"
        :page="page"
        :items-per-page="itemsPerPage"
        :loading="loading"
        :migrating-ids="migratingIds"
        @migrate="emit('migrate', $event)"
        @update:items-per-page="emit('update:items-per-page', $event)"
        @update:page="emit('update:page', $event)"
      />
    </v-card-text>
  </v-card>
</template>

<style scoped>
.search-input :deep(.v-field) {
  transition: border-color 0.15s ease, box-shadow 0.15s ease, background-color 0.15s ease;
  background-color: #ffffff;
}

.search-input :deep(.v-field__outline) {
  border-radius: 999px;
  border-width: 1px;
}

.search-input :deep(.v-field.v-field--focused .v-field__outline) {
  border-color: rgb(19, 182, 236);
  box-shadow: 0 0 0 3px rgba(19, 182, 236, 0.12);
}

.search-input :deep(input) {
  box-shadow: none !important;
  background: transparent;
}
</style>
