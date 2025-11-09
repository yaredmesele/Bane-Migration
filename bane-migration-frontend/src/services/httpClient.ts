import axios from "axios";

const apiBaseUrl =
  (import.meta.env.VITE_API_BASE_URL as string | undefined) ??
  "http://localhost:8080/api";
const baseURL = apiBaseUrl.endsWith("/") ? apiBaseUrl.slice(0, -1) : apiBaseUrl;

const httpClient = axios.create({
  baseURL,
  timeout: 15_000,
});

export default httpClient;
