import type { AxiosError } from "axios";

interface ApiErrorBody {
  status?: number;
  message?: string;
  error?: string;
}

export function extractErrorMessage(
  error: unknown,
  fallback = "Something went wrong"
): string {
  if (typeof error === "string") {
    return error;
  }

  const axiosError = error as AxiosError<ApiErrorBody>;

  if (axiosError?.response?.data?.message) {
    return axiosError.response.data.message;
  }

  if (axiosError?.message) {
    return axiosError.message;
  }

  if (error instanceof Error && error.message) {
    return error.message;
  }

  return fallback;
}
