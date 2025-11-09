import { computed, reactive, ref, watch } from "vue";
import { extractErrorMessage } from "@/utils/error";
import type { PaginatedResponse } from "@/types/pagination";

interface Fetcher<T> {
  (page: number, size: number): Promise<PaginatedResponse<T>>;
}

export function usePaginatedResource<T>(fetcher: Fetcher<T>) {
  const items = ref<T[]>([]);
  const loading = ref(false);
  const error = ref<string | null>(null);

  const state = reactive({
    page: 1,
    itemsPerPage: 10,
    totalItems: 0,
    totalPages: 0,
    hasNext: false,
    hasPrevious: false,
  });

  async function load() {
    loading.value = true;
    error.value = null;

    try {
      const response = await fetcher(state.page - 1, state.itemsPerPage);
      items.value = response.content;
      state.totalItems = response.totalElements;
      state.totalPages = response.totalPages;
      state.hasNext = response.hasNext;
      state.hasPrevious = response.hasPrevious;
    } catch (err) {
      error.value = extractErrorMessage(err, "Unable to load data");
      items.value = [];
    } finally {
      loading.value = false;
    }
  }

  function setPage(page: number) {
    state.page = page;
  }

  function setItemsPerPage(size: number) {
    state.itemsPerPage = size;
  }

  function reset() {
    state.page = 1;
    state.itemsPerPage = 10;
  }

  const pageCount = computed(() =>
    Math.max(
      1,
      state.totalPages || Math.ceil(state.totalItems / state.itemsPerPage)
    )
  );

  watch(
    () => [state.page, state.itemsPerPage],
    () => {
      load();
    },
    { immediate: true }
  );

  return {
    items,
    loading,
    error,
    state,
    pageCount,
    load,
    refresh: load,
    setPage,
    setItemsPerPage,
    reset,
  };
}
