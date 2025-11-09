<script setup lang="ts">
import MigratedClientsTable from "./MigratedClientsTable.vue";
import type { Client } from "@/types/client";

defineProps<{
  clients: Client[];
  totalItems: number;
  page: number;
  itemsPerPage: number;
  loading: boolean;
  errorMessage?: string | null;
  searchTerm?: string;
}>();

const emit = defineEmits<{
  (event: "update:page", value: number): void;
  (event: "update:items-per-page", value: number): void;
  (event: "update:search", value: string): void;
}>();

function handleSearchInput(value: string) {
  emit("update:search", value);
}
</script>

<template>
  <v-card elevation="0" rounded="xl">
    <v-card-text>
      <div class="d-flex flex-wrap align-center ga-3 mb-4">
        <div>
          <h2 class="text-h6 font-weight-bold mb-1">
            Successfully Migrated Clients
          </h2>
          <p class="text-body-2 text-medium-emphasis mb-0">
            Review clients that have been migrated to the new platform and
            confirm migration details.
          </p>
        </div>
      </div>

      <v-alert
        v-if="errorMessage"
        type="error"
        variant="tonal"
        border="start"
        class="mb-4"
      >
        {{ errorMessage }}
      </v-alert>

      <v-text-field
        :model-value="searchTerm"
        class="mb-4"
        density="comfortable"
        variant="outlined"
        prepend-inner-icon="mdi-magnify"
        placeholder="Search migrated clients"
        clearable
        @update:model-value="handleSearchInput"
      />

      <MigratedClientsTable
        :items="clients"
        :total-items="totalItems"
        :page="page"
        :items-per-page="itemsPerPage"
        :loading="loading"
        @update:items-per-page="emit('update:items-per-page', $event)"
        @update:page="emit('update:page', $event)"
      />
    </v-card-text>
  </v-card>
</template>
