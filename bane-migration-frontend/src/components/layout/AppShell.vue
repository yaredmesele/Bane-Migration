<script setup lang="ts">
import { RouterLink } from "vue-router";

defineProps<{
  title?: string;
}>();
</script>

<template>
  <v-layout class="font-display">
    <v-app-bar flat color="surface" elevation="1" density="comfortable">
      <v-container class="d-flex align-center p-4" max-width="1400">
        <div class="d-flex align-center gap-3">
          <v-avatar size="36" color="primary" class="text-white">
            <v-icon icon="mdi-database-sync" />
          </v-avatar>
          <div class="d-flex flex-column">
            <span class="text-subtitle-1 font-weight-bold text-primary"
              >Bane Migration Tool</span
            >
            <span class="text-caption text-medium-emphasis">{{
              title ?? "LegacyCRM to NewConnect"
            }}</span>
          </div>
        </div>
        <v-spacer />
        <div class="d-none d-md-flex align-center ga-4">
          <RouterLink class="text-button font-weight-bold text-primary" to="/"
            >Dashboard</RouterLink
          >
          <RouterLink class="text-button text-medium-emphasis" to="/legacy"
            >Legacy Clients</RouterLink
          >
          <RouterLink class="text-button text-medium-emphasis" to="/migrated"
            >Migrated Clients</RouterLink
          >
        </div>
      </v-container>
    </v-app-bar>

    <v-main class="bg-background">
      <v-container class="py-6 px-4 px-md-8" max-width="1400">
        <slot />
      </v-container>
    </v-main>
  </v-layout>
</template>
