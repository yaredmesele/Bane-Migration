import { computed, reactive, readonly } from "vue";

type NotificationVariant = "success" | "info" | "warning" | "error";

interface NotificationPayload {
  message: string;
  details?: string;
  variant?: NotificationVariant;
}

const state = reactive({
  visible: false,
  message: "",
  details: "",
  variant: "info" as NotificationVariant,
  timeout: 4000,
});

export function useNotifier() {
  function show({
    message,
    details = "",
    variant = "info",
  }: NotificationPayload) {
    state.visible = true;
    state.message = message;
    state.details = details;
    state.variant = variant;
  }

  function success(message: string, details?: string) {
    show({ message, details, variant: "success" });
  }

  function error(message: string, details?: string) {
    show({ message, details, variant: "error" });
  }

  function info(message: string, details?: string) {
    show({ message, details, variant: "info" });
  }

  function warning(message: string, details?: string) {
    show({ message, details, variant: "warning" });
  }

  function dismiss() {
    state.visible = false;
  }

  const notification = readonly(state);
  const color = computed(() => {
    switch (state.variant) {
      case "success":
        return "success";
      case "error":
        return "error";
      case "warning":
        return "warning";
      default:
        return "primary";
    }
  });

  return {
    notification,
    color,
    show,
    success,
    error,
    info,
    warning,
    dismiss,
  };
}
