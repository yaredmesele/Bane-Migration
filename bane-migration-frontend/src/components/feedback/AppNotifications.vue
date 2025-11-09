<script setup lang="ts">
import { useNotifier } from '@/composables/useNotifier'

const { notification, color, dismiss } = useNotifier()
</script>

<template>
  <v-snackbar
    v-model="notification.visible"
    :timeout="notification.timeout"
    :color="color"
    variant="flat"
    location="bottom right"
    class="text-white"
  >
    <div class="d-flex flex-column">
      <span class="text-subtitle-2 font-weight-bold">{{ notification.message }}</span>
      <span v-if="notification.details" class="text-body-2">{{ notification.details }}</span>
    </div>
    <template #actions>
      <v-btn variant="text" color="white" @click="dismiss">
        Close
      </v-btn>
    </template>
  </v-snackbar>
</template>

