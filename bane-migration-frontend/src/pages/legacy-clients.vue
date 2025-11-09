<template>
  <div class="relative flex min-h-screen w-full flex-col bg-background-light text-[#111618] dark:bg-background-dark dark:text-gray-200">
    <header class="sticky top-0 z-10 w-full border-b border-gray-200 bg-background-light/80 backdrop-blur-sm dark:border-gray-700 dark:bg-background-dark/80">
      <div class="mx-auto flex h-16 max-w-7xl items-center justify-between px-4 sm:px-6 lg:px-8">
        <div class="flex items-center gap-4">
          <div class="size-6 text-primary">
            <svg fill="none" viewBox="0 0 48 48" xmlns="http://www.w3.org/2000/svg">
              <path
                clip-rule="evenodd"
                d="M24 4H42V17.3333V30.6667H24V44H6V30.6667V17.3333H24V4Z"
                fill="currentColor"
                fill-rule="evenodd"
              />
            </svg>
          </div>
          <h2 class="text-xl font-bold leading-tight tracking-tight">Bane Migration Tool</h2>
        </div>
        <RouterLink
          class="flex h-10 min-w-[84px] items-center justify-center gap-2 rounded-lg bg-primary px-4 text-sm font-bold text-white shadow-sm transition hover:bg-primary/90 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-primary"
          to="/migrated-clients"
        >
          <span class="material-symbols-outlined text-base">rocket_launch</span>
          <span class="truncate">Migrate Selected</span>
        </RouterLink>
      </div>
    </header>

    <main class="mx-auto flex w-full max-w-7xl flex-grow flex-col gap-8 px-4 py-8 sm:px-6 lg:px-8">
      <div class="flex flex-wrap items-center justify-between gap-4">
        <div class="flex flex-col gap-1">
          <p class="text-3xl font-black leading-tight tracking-[-0.033em]">
            Client Migration Dashboard
          </p>
          <p class="text-base font-normal leading-normal text-gray-500 dark:text-gray-400">
            Manage and initiate migrations for legacy clients.
          </p>
        </div>
        <RouterLink
          class="hidden items-center gap-2 rounded-lg bg-secondary px-4 py-2 text-sm font-semibold text-white shadow-sm transition hover:bg-secondary/90 sm:flex"
          to="/"
        >
          <span class="material-symbols-outlined text-base">dashboard</span>
          Dashboard Overview
        </RouterLink>
      </div>

      <section class="flex flex-col gap-4">
        <div class="overflow-hidden rounded-lg border border-gray-200 bg-white shadow-sm dark:border-gray-700 dark:bg-background-dark">
          <div class="overflow-x-auto">
            <table class="w-full text-left">
              <thead class="border-b border-gray-200 bg-gray-50 dark:border-gray-700 dark:bg-white/5">
                <tr>
                  <th class="w-12 p-4 text-center">
                    <input
                      class="h-5 w-5 rounded border-gray-300 text-primary focus:ring-primary dark:border-gray-600 dark:bg-transparent dark:ring-offset-background-dark"
                      type="checkbox"
                    >
                  </th>
                  <th class="px-4 py-3 text-sm font-medium">Client Name</th>
                  <th class="px-4 py-3 text-sm font-medium">Last Activity</th>
                  <th class="px-4 py-3 text-sm font-medium">Migration Status</th>
                  <th class="w-40 px-4 py-3 text-center text-sm font-medium">Actions</th>
                </tr>
              </thead>
              <tbody>
                <tr class="border-t border-gray-200 dark:border-gray-700">
                  <td class="p-4 text-center">
                    <input
                      class="h-5 w-5 rounded border-gray-300 text-primary focus:ring-primary dark:border-gray-600 dark:bg-transparent dark:ring-offset-background-dark"
                      type="checkbox"
                    >
                  </td>
                  <td class="px-4 py-3 text-sm font-medium">Legacy Corp</td>
                  <td class="px-4 py-3 text-sm text-gray-500 dark:text-gray-400">2023-10-26</td>
                  <td class="px-4 py-3 text-sm">
                    <span class="inline-flex items-center rounded-full bg-gray-100 px-2.5 py-0.5 text-xs font-medium text-gray-600 dark:bg-gray-700 dark:text-gray-300">
                      Pending
                    </span>
                  </td>
                  <td class="px-4 py-3 text-center">
                    <button
                      class="flex h-8 w-full min-w-[84px] items-center justify-center gap-1 rounded-md bg-gray-100 px-3 text-sm font-medium hover:bg-gray-200 dark:bg-white/10 dark:hover:bg-white/20"
                    >
                      <span class="truncate">Migrate</span>
                    </button>
                  </td>
                </tr>
                <tr class="border-t border-gray-200 dark:border-gray-700">
                  <td class="p-4 text-center">
                    <input
                      class="h-5 w-5 rounded border-gray-300 text-primary focus:ring-primary dark:border-gray-600 dark:bg-transparent dark:ring-offset-background-dark"
                      type="checkbox"
                    >
                  </td>
                  <td class="px-4 py-3 text-sm font-medium">Old Systems Inc.</td>
                  <td class="px-4 py-3 text-sm text-gray-500 dark:text-gray-400">2023-10-25</td>
                  <td class="px-4 py-3 text-sm">
                    <span class="inline-flex items-center rounded-full bg-blue-100 px-2.5 py-0.5 text-xs font-medium text-blue-700 dark:bg-blue-900/50 dark:text-blue-300">
                      In Progress
                    </span>
                  </td>
                  <td class="px-4 py-3 text-center">
                    <button
                      class="flex h-8 w-full min-w-[84px] items-center justify-center gap-1 rounded-md bg-gray-100 px-3 text-sm font-medium dark:bg-white/10"
                      disabled
                    >
                      <span class="material-symbols-outlined text-sm animate-spin">progress_activity</span>
                      <span class="truncate">Migrating</span>
                    </button>
                  </td>
                </tr>
                <tr class="border-t border-gray-200 dark:border-gray-700">
                  <td class="p-4 text-center">
                    <input
                      class="h-5 w-5 rounded border-gray-300 text-primary focus:ring-primary dark:border-gray-600 dark:bg-transparent dark:ring-offset-background-dark"
                      type="checkbox"
                    >
                  </td>
                  <td class="px-4 py-3 text-sm font-medium">Vintage Tech</td>
                  <td class="px-4 py-3 text-sm text-gray-500 dark:text-gray-400">2023-10-22</td>
                  <td class="px-4 py-3 text-sm">
                    <span class="inline-flex items-center gap-1.5 rounded-full bg-green-500/10 px-2.5 py-0.5 text-xs font-medium text-success dark:bg-success/20 dark:text-green-300">
                      <span class="material-symbols-outlined text-xs">check_circle</span>
                      Migrated
                    </span>
                  </td>
                  <td class="px-4 py-3 text-center">
                    <button
                      class="flex h-8 w-full min-w-[84px] items-center justify-center gap-1 rounded-md bg-gray-100 px-3 text-sm font-medium dark:bg-white/10"
                      disabled
                    >
                      <span class="truncate">Migrate</span>
                    </button>
                  </td>
                </tr>
                <tr class="border-t border-gray-200 dark:border-gray-700">
                  <td class="p-4 text-center">
                    <input
                      class="h-5 w-5 rounded border-gray-300 text-primary focus:ring-primary dark:border-gray-600 dark:bg-transparent dark:ring-offset-background-dark"
                      type="checkbox"
                    >
                  </td>
                  <td class="px-4 py-3 text-sm font-medium">Data Dynamos</td>
                  <td class="px-4 py-3 text-sm text-gray-500 dark:text-gray-400">2023-10-21</td>
                  <td class="px-4 py-3 text-sm">
                    <span class="inline-flex items-center gap-1.5 rounded-full bg-secondary/10 px-2.5 py-0.5 text-xs font-medium text-secondary dark:bg-secondary/20 dark:text-orange-300">
                      <span class="material-symbols-outlined text-xs">warning</span>
                      Failed
                    </span>
                  </td>
                  <td class="px-4 py-3 text-center">
                    <button
                      class="flex h-8 w-full min-w-[84px] items-center justify-center gap-1 rounded-md bg-secondary/20 px-3 text-sm font-medium text-secondary transition hover:bg-secondary/30 dark:bg-secondary/20 dark:hover:bg-secondary/30"
                    >
                      <span class="material-symbols-outlined text-base">refresh</span>
                      <span class="truncate">Retry</span>
                    </button>
                  </td>
                </tr>
                <tr class="border-t border-gray-200 dark:border-gray-700">
                  <td class="p-4 text-center">
                    <input
                      class="h-5 w-5 rounded border-gray-300 text-primary focus:ring-primary dark:border-gray-600 dark:bg-transparent dark:ring-offset-background-dark"
                      type="checkbox"
                    >
                  </td>
                  <td class="px-4 py-3 text-sm font-medium">Archive Solutions</td>
                  <td class="px-4 py-3 text-sm text-gray-500 dark:text-gray-400">2023-10-20</td>
                  <td class="px-4 py-3 text-sm">
                    <span class="inline-flex items-center rounded-full bg-gray-100 px-2.5 py-0.5 text-xs font-medium text-gray-600 dark:bg-gray-700 dark:text-gray-300">
                      Pending
                    </span>
                  </td>
                  <td class="px-4 py-3 text-center">
                    <button
                      class="flex h-8 w-full min-w-[84px] items-center justify-center gap-1 rounded-md bg-gray-100 px-3 text-sm font-medium hover:bg-gray-200 dark:bg-white/10 dark:hover:bg-white/20"
                    >
                      <span class="truncate">Migrate</span>
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>

          <div class="flex items-center justify-between border-t border-gray-200 p-4 text-sm text-gray-500 dark:border-gray-700 dark:text-gray-400">
            <span>Showing 1 to 5 of 57 results</span>
            <div class="flex items-center gap-2">
              <a class="flex size-8 items-center justify-center rounded-md border border-gray-300 hover:bg-gray-100 dark:border-gray-600 dark:hover:bg-white/10" href="#">
                <span class="material-symbols-outlined text-lg">chevron_left</span>
              </a>
              <a class="flex size-8 items-center justify-center rounded-md bg-primary text-sm font-bold text-white" href="#">1</a>
              <a class="flex size-8 items-center justify-center rounded-md text-sm hover:bg-gray-100 dark:hover:bg-white/10" href="#">2</a>
              <a class="flex size-8 items-center justify-center rounded-md text-sm hover:bg-gray-100 dark:hover:bg-white/10" href="#">3</a>
              <span class="flex size-8 items-center justify-center text-sm">...</span>
              <a class="flex size-8 items-center justify-center rounded-md text-sm hover:bg-gray-100 dark:hover:bg-white/10" href="#">12</a>
              <a class="flex size-8 items-center justify-center rounded-md border border-gray-300 hover:bg-gray-100 dark:border-gray-600 dark:hover:bg-white/10" href="#">
                <span class="material-symbols-outlined text-lg">chevron_right</span>
              </a>
            </div>
          </div>
        </div>
      </section>
    </main>

    <div class="fixed bottom-8 right-8 flex flex-col gap-4">
      <div class="flex w-auto max-w-md items-center gap-4 rounded-lg bg-success p-4 text-white shadow-lg">
        <span class="material-symbols-outlined">task_alt</span>
        <div class="flex-grow">
          <p class="font-bold">Migration Success</p>
          <p class="text-sm">Migration for 'Vintage Tech' initiated successfully.</p>
        </div>
        <button class="rounded-full p-1 hover:bg-white/20">
          <span class="material-symbols-outlined text-lg">close</span>
        </button>
      </div>
      <div class="flex w-auto max-w-md items-center gap-4 rounded-lg bg-danger p-4 text-white shadow-lg">
        <span class="material-symbols-outlined">error</span>
        <div class="flex-grow">
          <p class="font-bold">Migration Failed</p>
          <p class="text-sm">Failed to initiate migration for 'Data Dynamos'. Please try again.</p>
        </div>
        <button class="rounded-full p-1 hover:bg-white/20">
          <span class="material-symbols-outlined text-lg">close</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { RouterLink } from 'vue-router'
</script>

