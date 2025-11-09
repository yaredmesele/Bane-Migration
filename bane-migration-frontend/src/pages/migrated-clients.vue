<template>
  <div class="relative flex min-h-screen w-full flex-col bg-background-light font-display dark:bg-background-dark">
    <div class="flex flex-1">
      <aside class="flex w-64 flex-shrink-0 flex-col justify-between border-r border-gray-200 bg-white p-4 dark:border-gray-700 dark:bg-[#152329]">
        <div class="flex flex-col gap-4">
          <div class="flex items-center gap-3 p-2">
            <div class="rounded-lg bg-primary/20 p-2 text-primary">
              <span class="material-symbols-outlined">all_inclusive</span>
            </div>
            <h2 class="text-lg font-bold text-[#111618] dark:text-white">Bane Migrator</h2>
          </div>
          <nav class="mt-4 flex flex-col gap-2">
            <RouterLink
              class="flex items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium text-[#111618] hover:bg-gray-100 dark:text-gray-300 dark:hover:bg-background-dark"
              to="/"
            >
              <span class="material-symbols-outlined text-xl">grid_view</span>
              Dashboard
            </RouterLink>
            <RouterLink
              class="flex items-center gap-3 rounded-lg bg-primary/20 px-3 py-2 text-sm font-medium text-primary dark:bg-primary/30"
              to="/migrated-clients"
            >
              <span class="material-symbols-outlined text-xl">sync_alt</span>
              Migrations
            </RouterLink>
            <RouterLink
              class="flex items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium text-[#111618] hover:bg-gray-100 dark:text-gray-300 dark:hover:bg-background-dark"
              to="/legacy-clients"
            >
              <span class="material-symbols-outlined text-xl">group</span>
              Clients
            </RouterLink>
            <a
              class="flex items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium text-[#111618] hover:bg-gray-100 dark:text-gray-300 dark:hover:bg-background-dark"
              href="#"
            >
              <span class="material-symbols-outlined text-xl">settings</span>
              Settings
            </a>
          </nav>
        </div>

        <div class="flex flex-col gap-1">
          <a
            class="flex items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium text-[#111618] hover:bg-gray-100 dark:text-gray-300 dark:hover:bg-background-dark"
            href="#"
          >
            <span class="material-symbols-outlined text-xl">help</span>
            Support
          </a>
          <div class="mt-4 flex items-center gap-3 border-t border-gray-200 px-3 pt-4 dark:border-gray-700">
            <div
              class="size-10 rounded-full bg-cover bg-center bg-no-repeat"
              data-alt="User avatar image"
              style="background-image: url('https://lh3.googleusercontent.com/aida-public/AB6AXuCQVq_jdF8WGuyP4-LdgGhQPDp6CLOHa0r1uG5IsS6FyB7EmPd3AwEWTmVUf6eVd5tPnDvt6utAS4jLtAv_NDz7PYBHDv8L-C6ay04lWvg7Xpzcr8TrVZzuTy-bXTc0Pa-z5pvZ1lWOw8pYrZEcicwU9FrsDIy0fuLTep2m8z2u1dHrKQsywcWLmilzctjiJyT3XBGBw6NI0-hoTzUnezy1d-alKMzCrJF_tnO9uWp-qHsS9ZqWFWdMLcSTu6e0GqOLL0zL2vPRdygY');"
            />
            <div class="flex flex-col">
              <h1 class="text-sm font-semibold text-[#111618] dark:text-white">
                Admin User
              </h1>
              <p class="text-xs font-normal text-gray-500 dark:text-gray-400">
                admin@bane.com
              </p>
            </div>
            <a class="ml-auto text-[#111618] hover:text-primary dark:text-gray-300 dark:hover:text-primary" href="#">
              <span class="material-symbols-outlined text-xl">logout</span>
            </a>
          </div>
        </div>
      </aside>

      <main class="flex-1 p-8">
        <div class="mx-auto flex max-w-7xl flex-col">
          <div class="mb-6 flex flex-wrap items-center justify-between gap-4">
            <h1 class="text-3xl font-bold leading-tight tracking-tight text-[#111618] dark:text-white">
              Successfully Migrated Clients
            </h1>
            <RouterLink
              class="flex items-center gap-2 rounded-lg bg-secondary px-4 py-2 text-sm font-semibold text-white transition hover:bg-opacity-90"
              to="/legacy-clients"
            >
              <span class="material-symbols-outlined text-lg">add</span>
              New Migration
            </RouterLink>
          </div>

          <section class="overflow-hidden rounded-xl border border-gray-200 bg-white dark:border-gray-700 dark:bg-[#152329]">
            <div class="flex overflow-x-auto">
              <table class="w-full text-left">
                <thead>
                  <tr class="bg-gray-50 text-xs font-semibold uppercase tracking-wider text-gray-600 dark:bg-background-dark dark:text-gray-400">
                    <th class="px-6 py-4">
                      <div class="flex items-center gap-1 cursor-pointer hover:text-primary">
                        Client Name
                        <span class="material-symbols-outlined text-sm">unfold_more</span>
                      </div>
                    </th>
                    <th class="px-6 py-4">
                      <div class="flex items-center gap-1">
                        Migration ID
                      </div>
                    </th>
                    <th class="px-6 py-4">
                      <div class="flex items-center gap-1 cursor-pointer hover:text-primary">
                        Migrated At
                        <span class="material-symbols-outlined text-sm">unfold_more</span>
                      </div>
                    </th>
                    <th class="px-6 py-4 text-right">
                      Actions
                    </th>
                  </tr>
                </thead>
                <tbody class="divide-y divide-gray-200 dark:divide-gray-700">
                  <tr class="hover:bg-gray-50 dark:hover:bg-background-dark">
                    <td class="whitespace-nowrap px-6 py-4 text-sm font-medium text-[#111618] dark:text-white">
                      Innovate Inc.
                    </td>
                    <td class="whitespace-nowrap px-6 py-4 font-mono text-sm text-gray-500 dark:text-gray-400">
                      mig-a4b1c2d3
                    </td>
                    <td class="whitespace-nowrap px-6 py-4 text-sm text-gray-500 dark:text-gray-400">
                      2023-10-27 14:30:15
                    </td>
                    <td class="whitespace-nowrap px-6 py-4 text-right text-sm font-medium">
                      <a class="text-primary hover:underline" href="#">View Details</a>
                    </td>
                  </tr>
                  <tr class="hover:bg-gray-50 dark:hover:bg-background-dark">
                    <td class="whitespace-nowrap px-6 py-4 text-sm font-medium text-[#111618] dark:text-white">
                      Quantum Solutions
                    </td>
                    <td class="whitespace-nowrap px-6 py-4 font-mono text-sm text-gray-500 dark:text-gray-400">
                      mig-e5f6g7h8
                    </td>
                    <td class="whitespace-nowrap px-6 py-4 text-sm text-gray-500 dark:text-gray-400">
                      2023-10-27 11:05:45
                    </td>
                    <td class="whitespace-nowrap px-6 py-4 text-right text-sm font-medium">
                      <a class="text-primary hover:underline" href="#">View Details</a>
                    </td>
                  </tr>
                  <tr class="hover:bg-gray-50 dark:hover:bg-background-dark">
                    <td class="whitespace-nowrap px-6 py-4 text-sm font-medium text-[#111618] dark:text-white">
                      Apex Enterprises
                    </td>
                    <td class="whitespace-nowrap px-6 py-4 font-mono text-sm text-gray-500 dark:text-gray-400">
                      mig-i9j0k1l2
                    </td>
                    <td class="whitespace-nowrap px-6 py-4 text-sm text-gray-500 dark:text-gray-400">
                      2023-10-26 18:20:00
                    </td>
                    <td class="whitespace-nowrap px-6 py-4 text-right text-sm font-medium">
                      <a class="text-primary hover:underline" href="#">View Details</a>
                    </td>
                  </tr>
                  <tr class="hover:bg-gray-50 dark:hover:bg-background-dark">
                    <td class="whitespace-nowrap px-6 py-4 text-sm font-medium text-[#111618] dark:text-white">
                      Stellar Corp
                    </td>
                    <td class="whitespace-nowrap px-6 py-4 font-mono text-sm text-gray-500 dark:text-gray-400">
                      mig-m3n4o5p6
                    </td>
                    <td class="whitespace-nowrap px-6 py-4 text-sm text-gray-500 dark:text-gray-400">
                      2023-10-26 09:55:30
                    </td>
                    <td class="whitespace-nowrap px-6 py-4 text-right text-sm font-medium">
                      <a class="text-primary hover:underline" href="#">View Details</a>
                    </td>
                  </tr>
                  <tr class="hover:bg-gray-50 dark:hover:bg-background-dark">
                    <td class="whitespace-nowrap px-6 py-4 text-sm font-medium text-[#111618] dark:text-white">
                      Momentum LLC
                    </td>
                    <td class="whitespace-nowrap px-6 py-4 font-mono text-sm text-gray-500 dark:text-gray-400">
                      mig-q7r8s9t0
                    </td>
                    <td class="whitespace-nowrap px-6 py-4 text-sm text-gray-500 dark:text-gray-400">
                      2023-10-25 21:10:12
                    </td>
                    <td class="whitespace-nowrap px-6 py-4 text-right text-sm font-medium">
                      <a class="text-primary hover:underline" href="#">View Details</a>
                    </td>
                  </tr>
                  <tr class="hover:bg-gray-50 dark:hover:bg-background-dark">
                    <td class="whitespace-nowrap px-6 py-4 text-sm font-medium text-[#111618] dark:text-white">
                      Zenith Dynamics
                    </td>
                    <td class="whitespace-nowrap px-6 py-4 font-mono text-sm text-gray-500 dark:text-gray-400">
                      mig-u1v2w3x4
                    </td>
                    <td class="whitespace-nowrap px-6 py-4 text-sm text-gray-500 dark:text-gray-400">
                      2023-10-25 15:45:50
                    </td>
                    <td class="whitespace-nowrap px-6 py-4 text-right text-sm font-medium">
                      <a class="text-primary hover:underline" href="#">View Details</a>
                    </td>
                  </tr>
                  <tr class="hover:bg-gray-50 dark:hover:bg-background-dark">
                    <td class="whitespace-nowrap px-6 py-4 text-sm font-medium text-[#111618] dark:text-white">
                      Nexus Innovations
                    </td>
                    <td class="whitespace-nowrap px-6 py-4 font-mono text-sm text-gray-500 dark:text-gray-400">
                      mig-y5z6a7b8
                    </td>
                    <td class="whitespace-nowrap px-6 py-4 text-sm text-gray-500 dark:text-gray-400">
                      2023-10-24 12:00:00
                    </td>
                    <td class="whitespace-nowrap px-6 py-4 text-right text-sm font-medium">
                      <a class="text-primary hover:underline" href="#">View Details</a>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>

            <div class="flex items-center justify-between border-t border-gray-200 p-4 text-sm text-gray-600 dark:border-gray-700 dark:text-gray-400">
              <div class="flex items-center gap-4">
                <span>Rows per page:</span>
                <select class="rounded-md border border-gray-300 bg-white py-1.5 px-2 text-sm focus:border-primary focus:ring-primary dark:border-gray-600 dark:bg-[#152329]">
                  <option>10</option>
                  <option>25</option>
                  <option selected>50</option>
                </select>
                <span>1-50 of 234</span>
              </div>
              <div class="flex items-center justify-center">
                <a class="flex size-10 items-center justify-center rounded-lg text-[#111618] hover:bg-gray-100 dark:text-gray-300 dark:hover:bg-background-dark" href="#">
                  <span class="material-symbols-outlined text-lg">chevron_left</span>
                </a>
                <a class="flex size-10 items-center justify-center rounded-lg bg-primary text-sm font-bold text-white" href="#">1</a>
                <a class="flex size-10 items-center justify-center rounded-lg text-[#111618] hover:bg-gray-100 dark:text-gray-300 dark:hover:bg-background-dark" href="#">2</a>
                <a class="flex size-10 items-center justify-center rounded-lg text-[#111618] hover:bg-gray-100 dark:text-gray-300 dark:hover:bg-background-dark" href="#">3</a>
                <span class="flex size-10 items-center justify-center rounded-lg text-[#111618] dark:text-gray-300">...</span>
                <a class="flex size-10 items-center justify-center rounded-lg text-[#111618] hover:bg-gray-100 dark:text-gray-300 dark:hover:bg-background-dark" href="#">5</a>
                <a class="flex size-10 items-center justify-center rounded-lg text-[#111618] hover:bg-gray-100 dark:text-gray-300 dark:hover:bg-background-dark" href="#">
                  <span class="material-symbols-outlined text-lg">chevron_right</span>
                </a>
              </div>
            </div>
          </section>
        </div>
      </main>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { RouterLink } from 'vue-router'
</script>

