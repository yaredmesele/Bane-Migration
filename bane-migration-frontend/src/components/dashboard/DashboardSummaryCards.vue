<script setup lang="ts">
import { computed } from "vue";

const props = defineProps<{
  totalLegacy: number;
  totalMigrated: number;
  pendingMigrations: number;
  successRate: number;
}>();

const pendingValue = computed(() => Math.max(props.pendingMigrations, 0));
</script>

<template>
  <v-row dense>
    <v-col cols="12" md="3">
      <v-card class="pa-4" elevation="0" rounded="lg" color="surface">
        <p class="text-caption text-medium-emphasis mb-1">
          Total Legacy Clients
        </p>
        <p class="text-h5 font-weight-bold">
          {{ totalLegacy.toLocaleString() }}
        </p>
      </v-card>
    </v-col>
    <v-col cols="12" md="3">
      <v-card class="pa-4" elevation="0" rounded="lg" color="surface">
        <p class="text-caption text-medium-emphasis mb-1">Clients Migrated</p>
        <p class="text-h5 font-weight-bold">
          {{ totalMigrated.toLocaleString() }}
        </p>
      </v-card>
    </v-col>
    <v-col cols="12" md="3">
      <v-card class="pa-4" elevation="0" rounded="lg" color="surface">
        <p class="text-caption text-medium-emphasis mb-1">
          Migration Success Rate
        </p>
        <p class="text-h5 font-weight-bold text-success">
          {{ successRate.toFixed(1) }}%
        </p>
      </v-card>
    </v-col>
    <v-col cols="12" md="3">
      <v-card class="pa-4" elevation="0" rounded="lg" color="surface">
        <p class="text-caption text-medium-emphasis mb-1">Pending Migrations</p>
        <p class="text-h5 font-weight-bold text-secondary">
          {{ pendingValue.toLocaleString() }}
        </p>
      </v-card>
    </v-col>
  </v-row>
</template>
