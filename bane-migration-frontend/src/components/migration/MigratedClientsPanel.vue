<script setup lang="ts">
import { ref, watch } from "vue";
import MigratedClientsTable from "./MigratedClientsTable.vue";
import type { Client } from "@/types/client";
import { formatDateTime } from "@/utils/formatters";

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

const detailsDialogOpen = ref(false);
const selectedClient = ref<Client | null>(null);

function handleSearchInput(value: string) {
  emit("update:search", value);
}

function handleViewDetails(client: Client) {
  selectedClient.value = client;
  detailsDialogOpen.value = true;
}

function closeDetailsDialog() {
  detailsDialogOpen.value = false;
}

watch(detailsDialogOpen, (isOpen) => {
  if (!isOpen) {
    selectedClient.value = null;
  }
});
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
        class="mb-4 search-input"
        density="comfortable"
        variant="outlined"
        prepend-inner-icon="mdi-magnify"
        placeholder="Search migrated clients"
        color="primary"
        rounded="pill"
        hide-details
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
        @view-details="handleViewDetails"
      />
    </v-card-text>

    <v-dialog v-model="detailsDialogOpen" max-width="460">
      <v-card>
        <v-card-title class="d-flex align-center justify-space-between">
          <span class="text-h6 font-weight-bold">Client Details</span>
          <v-btn icon variant="text" color="default" @click="closeDetailsDialog">
            <v-icon icon="mdi-close" />
          </v-btn>
        </v-card-title>

        <v-divider />

        <v-card-text v-if="selectedClient" class="pa-6">
          <v-list density="compact" lines="two">
            <v-list-item>
              <v-list-item-subtitle>Full Name</v-list-item-subtitle>
              <v-list-item-title class="text-body-1">
                {{ selectedClient.fullName }}
              </v-list-item-title>
            </v-list-item>

            <v-divider class="my-2" />

            <v-list-item>
              <v-list-item-subtitle>Email</v-list-item-subtitle>
              <v-list-item-title class="text-body-1">
                {{ selectedClient.email }}
              </v-list-item-title>
            </v-list-item>

            <v-divider class="my-2" />

            <v-list-item>
              <v-list-item-subtitle>Client ID</v-list-item-subtitle>
              <v-list-item-title class="text-body-1">
                {{ selectedClient.id }}
              </v-list-item-title>
            </v-list-item>

            <v-divider class="my-2" />

            <v-list-item>
              <v-list-item-subtitle>Migrated At</v-list-item-subtitle>
              <v-list-item-title class="text-body-1">
                {{ formatDateTime(selectedClient.migratedAt, "Unavailable") }}
              </v-list-item-title>
            </v-list-item>
          </v-list>
        </v-card-text>

        <v-card-actions class="justify-end px-6 pb-4">
          <v-btn color="primary" variant="tonal" @click="closeDetailsDialog">
            Close
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
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
