<script setup lang="ts">
import type { Client } from '@/types/client'
import { formatDateTime } from '@/utils/formatters'

defineProps<{
  items: Client[]
  totalItems: number
  page: number
  itemsPerPage: number
  loading: boolean
}>()

const emit = defineEmits<{
  (event: 'update:page', value: number): void
  (event: 'update:items-per-page', value: number): void
  (event: 'view-details', value: Client): void
}>()

const headers = [
  { title: 'Client Name', value: 'fullName', sortable: false },
  { title: 'Email', value: 'email', sortable: false },
  { title: 'Migrated At', value: 'migratedAt', sortable: false },
  { title: 'Status', value: 'status', sortable: false },
  { title: 'Actions', value: 'actions', sortable: false, width: 120 },
]

function handlePageChange(value: number) {
  emit('update:page', value)
}

function handleItemsPerPageChange(value: number) {
  emit('update:items-per-page', value)
}

function handleDetailsClick(client: Client) {
  emit('view-details', client)
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
        title="No migrated clients yet"
        text="Once clients are migrated they will appear here."
      />
    </template>

    <template #top>
      <v-toolbar flat color="transparent">
        <v-toolbar-title>Migrated Clients</v-toolbar-title>
        <v-spacer />
        <v-chip :ripple="false" variant="flat" color="primary" class="text-white">
          {{ totalItems.toLocaleString() }} Total
        </v-chip>
      </v-toolbar>
    </template>

    <template #item.migratedAt="{ value }">
      {{ formatDateTime(value, 'Unavailable') }}
    </template>

    <template #item.status="{ item }">
      <v-chip color="success" size="small" variant="flat" label>
        Migrated
      </v-chip>
    </template>

    <template #item.actions="{ item }">
      <v-btn color="primary" variant="text" size="small" @click.stop="handleDetailsClick(item)">
        <v-icon icon="mdi-eye" start />
        Details
      </v-btn>
    </template>
  </v-data-table-server>
</template>

