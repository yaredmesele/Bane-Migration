<script setup lang="ts">
import AppShell from '@/components/layout/AppShell.vue'
import AppNotifications from '@/components/feedback/AppNotifications.vue'
</script>

<template>
  <v-app>
    <AppShell title="Migration Dashboard">
      <router-view />
    </AppShell>
    <AppNotifications />
  </v-app>
</template>
