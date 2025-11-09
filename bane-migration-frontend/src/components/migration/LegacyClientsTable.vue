<script setup lang="ts">
import type { Client } from "@/types/client";
import { formatDateTime } from "@/utils/formatters";

const props = defineProps<{
  items: Client[];
  totalItems: number;
  page: number;
  itemsPerPage: number;
  loading: boolean;
  migratingIds: Set<number>;
}>();

const emit = defineEmits<{
  (event: "update:page", value: number): void;
  (event: "update:items-per-page", value: number): void;
  (event: "migrate", clientId: number): void;
}>();

const headers = [
  { title: "Client Name", value: "fullName", sortable: false },
  { title: "Email", value: "email", sortable: false },
  { title: "Migration Status", value: "status", sortable: false },
  { title: "Migrated At", value: "migratedAt", sortable: false },
  { title: "Actions", value: "actions", sortable: false, width: 140 },
];

function handlePageChange(value: number) {
  emit("update:page", value);
}

function handleItemsPerPageChange(value: number) {
  emit("update:items-per-page", value);
}

function handleMigrateClick(client: Client) {
  if (client.migrated) {
    return;
  }
  emit("migrate", client.id);
}
</script>

<template>
  <v-data-table-server
    :headers="headers"
    :items="items"
    :items-length="totalItems"
    :loading="loading"
    :page="page"
    :items-per-page="itemsPerPage"
    item-value="id"
    class="rounded-lg elevation-0"
    hover
    @update:page="handlePageChange"
    @update:items-per-page="handleItemsPerPageChange"
  >
    <template #no-data>
      <v-alert
        border="start"
        color="primary"
        class="ma-4"
        title="No legacy clients"
        text="All legacy clients have been migrated. Great job!"
      />
    </template>

    <template #top>
      <v-toolbar flat color="transparent">
        <v-toolbar-title>Legacy Clients</v-toolbar-title>
        <v-spacer />
        <v-chip
          :ripple="false"
          variant="flat"
          color="primary"
          class="text-white"
        >
          {{ totalItems.toLocaleString() }} Total
        </v-chip>
      </v-toolbar>
    </template>

    <template #item.status="{ item }">
      <v-chip
        :color="item.migrated ? 'success' : 'warning'"
        size="small"
        label
        variant="flat"
      >
        {{ item.migrated ? "Migrated" : "Pending" }}
      </v-chip>
    </template>

    <template #item.migratedAt="{ value }">
      <span class="text-body-2">
        {{ formatDateTime(value, "Not migrated yet") }}
      </span>
    </template>

    <template #item.actions="{ item }">
      <v-btn
        size="small"
        color="primary"
        variant="flat"
        :disabled="item.migrated"
        :loading="migratingIds.has(item.id as number)"
        @click.stop="handleMigrateClick(item)"
      >
        <v-icon icon="mdi-rocket-launch" start />
        Migrate
      </v-btn>
    </template>
  </v-data-table-server>
</template>
