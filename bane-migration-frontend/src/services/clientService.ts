import type { Client } from "@/types/client";
import type { PaginatedResponse } from "@/types/pagination";
import type { MigrationMetrics } from "@/types/metrics";
import httpClient from "./httpClient";

const LEGACY_ENDPOINT = "/legacy/clients";
const NEW_ENDPOINT = "/new/clients";
const MIGRATE_ENDPOINT = "/migrate";
const METRICS_ENDPOINT = "/metrics/summary";

export interface PaginationQuery {
  page?: number;
  size?: number;
  search?: string;
}

export interface BulkMigrationPayload {
  clientIds: number[];
}

function toQueryParams({
  page = 0,
  size = 10,
  search,
}: PaginationQuery): string {
  const params = new URLSearchParams();
  params.set("page", page.toString());
  params.set("size", size.toString());

  if (search && search.trim().length > 0) {
    params.set("search", search.trim());
  }

  return params.toString();
}

export async function fetchLegacyClients(
  query: PaginationQuery
): Promise<PaginatedResponse<Client>> {
  const response = await httpClient.get<PaginatedResponse<Client>>(
    `${LEGACY_ENDPOINT}?${toQueryParams(query)}`
  );
  return response.data;
}

export async function fetchMigratedClients(
  query: PaginationQuery
): Promise<PaginatedResponse<Client>> {
  const response = await httpClient.get<PaginatedResponse<Client>>(
    `${NEW_ENDPOINT}?${toQueryParams(query)}`
  );
  return response.data;
}

export async function fetchMigrationMetrics(): Promise<MigrationMetrics> {
  const response = await httpClient.get<MigrationMetrics>(METRICS_ENDPOINT);
  return response.data;
}

export async function migrateClient(clientId: number): Promise<Client> {
  const response = await httpClient.post<Client>(
    `${MIGRATE_ENDPOINT}/${clientId}`
  );
  return response.data;
}

export async function migrateBulk(
  payload: BulkMigrationPayload
): Promise<Client[]> {
  const response = await httpClient.post<Client[]>(
    `${MIGRATE_ENDPOINT}/bulk`,
    payload
  );
  return response.data;
}

export async function migrateAll(): Promise<Client[]> {
  const response = await httpClient.post<Client[]>(`${MIGRATE_ENDPOINT}/all`);
  return response.data;
}
