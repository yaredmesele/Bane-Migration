/**
 * plugins/vuetify.ts
 *
 * Framework documentation: https://vuetifyjs.com`
 */

// Styles
import "@mdi/font/css/materialdesignicons.css";
import "vuetify/styles";

// Composables
import { createVuetify, type ThemeDefinition } from "vuetify";

const baneLightTheme: ThemeDefinition = {
  dark: false,
  colors: {
    background: "#f6f8f8",
    surface: "#ffffff",
    primary: "#13b6ec",
    "primary-darken-1": "#0fa1d0",
    secondary: "#ff9800",
    "secondary-darken-1": "#e07f00",
    error: "#dc2626",
    info: "#13b6ec",
    success: "#16a34a",
    warning: "#f59e0b",
  },
};

const baneDarkTheme: ThemeDefinition = {
  dark: true,
  colors: {
    background: "#101d22",
    surface: "#152329",
    primary: "#13b6ec",
    secondary: "#ff9800",
    error: "#f87171",
    info: "#38bdf8",
    success: "#34d399",
    warning: "#fbbf24",
  },
};

// https://vuetifyjs.com/en/introduction/why-vuetify/#feature-guides
export default createVuetify({
  theme: {
    defaultTheme: "baneLight",
    themes: {
      baneLight: baneLightTheme,
      baneDark: baneDarkTheme,
    },
  },
});
