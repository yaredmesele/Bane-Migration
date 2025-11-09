# Bane Migration Tool – Frontend

A Vue 3 + Vuetify application that provides the administrative UI for migrating
clients from LegacyCRM into NewConnect. The project consumes a Spring Boot
backend that exposes the migration APIs.

---

## Getting Started

### Prerequisites

- **Node.js** 18 or later (20 LTS recommended)
- **pnpm**, **npm**, or **yarn** – examples below use `npm`

### Install dependencies

```bash
npm install
```

### Environment configuration

Create a `.env` file at the project root (or adjust the existing one) with the
API base URL exposed by the backend:

```env
VITE_API_BASE_URL=http://localhost:8080/api
```

### Run the development server

```bash
npm run dev
```

You can now visit the UI at [http://localhost:5173](http://localhost:5173) (or
whichever port Vite reports).

### Build for production

```bash
npm run build
```

Static assets are emitted to `dist/`.

### Preview the production build locally

```bash
npm run preview
```

---

## Technical Choices

### Vue 3 + Vite

- **Setup**: Vite offers fast HMR and an opinionated yet minimal configuration.
- **SFCs**: Single File Components keep template, script and styles colocated.
- **Composition API**: Provides modular composables (`useMigrationDashboard`,
  `usePaginatedResource`, `useNotifier`) for shared behavior.

### Vuetify 3

- **Material-inspired design** with quick access to tables, dialogs, buttons,
  and form elements.
- **Theming**: Custom light/dark palettes align with the product’s cyan/orange
  branding.
- **Layout primitives** (`v-app`, `v-app-bar`, `v-container`) simplify building
  responsive dashboards.

### Axios HTTP client

- Centralised client (`src/services/httpClient.ts`) configured via
  `VITE_API_BASE_URL`.
- Graceful timeout defaults and consistent error handling through
  `extractErrorMessage`.

### Modular services & utilities

- Typed service functions (`fetchLegacyClients`, `migrateClient`, etc.) isolate
  API specifics from components.
- Shared utilities in `src/utils` for formatting timestamps and surfacing user
  friendly error messages.

### State & feedback

- `usePaginatedResource` abstracts server-driven pagination, keeping components
  lightweight.
- `useNotifier` exposes a single snackbar instance for uniform success/error
  messaging.

### UI architecture

- **DashboardSummaryCards** highlight key metrics from `/api/metrics/summary`.
- **Legacy/Migrated panels** share styling but fetch separate data sources.
- Bulk migration was intentionally removed per requirement changes; only
  individual migrations and “Migrate All” remain, simplifying user flows.

### Tooling & linting

- ESLint with Vuetify and Vue 3 presets maintains code consistency.
- TypeScript enforces strict types for API contracts and component props.

---

## Project Structure Highlights

```
├── src/
│   ├── components/
│   │   ├── dashboard/         # Summary cards and dashboard pieces
│   │   └── migration/         # Legacy & migrated client tables/panels
│   ├── composables/           # Reusable logic hooks (pagination, notifier, dashboard)
│   ├── pages/                 # Route views (dashboard, legacy, migrated)
│   ├── services/              # Axios client + API wrappers
│   ├── utils/                 # Formatters and error helpers
│   └── types/                 # TypeScript interfaces for shared models
├── public/                    # Static assets
└── README.md
```

---

## Next Steps / Ideas

- Reintroduce bulk migration once backend/business rules are clarified.
- Add integration tests (Cypress or Playwright) to exercise key flows.
- Enhance filters (status/date/search) for large client datasets.
- Localize strings and add accessible notifications where needed.

---

Feel free to open issues or PRs for improvements. Happy migrating!

# Vuetify (Default)

This is the official scaffolding tool for Vuetify, designed to give you a head start in building your new Vuetify application. It sets up a base template with all the necessary configurations and standard directory structure, enabling you to begin development without the hassle of setting up the project from scratch.

## ❗️ Important Links

- 📄 [Docs](https://vuetifyjs.com/)
- 🚨 [Issues](https://issues.vuetifyjs.com/)
- 🏬 [Store](https://store.vuetifyjs.com/)
- 🎮 [Playground](https://play.vuetifyjs.com/)
- 💬 [Discord](https://community.vuetifyjs.com)

## 💿 Install

Set up your project using your preferred package manager. Use the corresponding command to install the dependencies:

| Package Manager                                           | Command        |
| --------------------------------------------------------- | -------------- |
| [yarn](https://yarnpkg.com/getting-started)               | `yarn install` |
| [npm](https://docs.npmjs.com/cli/v7/commands/npm-install) | `npm install`  |
| [pnpm](https://pnpm.io/installation)                      | `pnpm install` |
| [bun](https://bun.sh/#getting-started)                    | `bun install`  |

After completing the installation, your environment is ready for Vuetify development.

## ✨ Features

- 🖼️ **Optimized Front-End Stack**: Leverage the latest Vue 3 and Vuetify 3 for a modern, reactive UI development experience. [Vue 3](https://v3.vuejs.org/) | [Vuetify 3](https://vuetifyjs.com/en/)
- 🗃️ **State Management**: Integrated with [Pinia](https://pinia.vuejs.org/), the intuitive, modular state management solution for Vue.
- 🚦 **Routing and Layouts**: Utilizes Vue Router for SPA navigation and vite-plugin-vue-layouts-next for organizing Vue file layouts. [Vue Router](https://router.vuejs.org/) | [vite-plugin-vue-layouts-next](https://github.com/loicduong/vite-plugin-vue-layouts-next)
- 💻 **Enhanced Development Experience**: Benefit from TypeScript's static type checking and the ESLint plugin suite for Vue, ensuring code quality and consistency. [TypeScript](https://www.typescriptlang.org/) | [ESLint Plugin Vue](https://eslint.vuejs.org/)
- ⚡ **Next-Gen Tooling**: Powered by Vite, experience fast cold starts and instant HMR (Hot Module Replacement). [Vite](https://vitejs.dev/)
- 🧩 **Automated Component Importing**: Streamline your workflow with unplugin-vue-components, automatically importing components as you use them. [unplugin-vue-components](https://github.com/antfu/unplugin-vue-components)
- 🛠️ **Strongly-Typed Vue**: Use vue-tsc for type-checking your Vue components, and enjoy a robust development experience. [vue-tsc](https://github.com/johnsoncodehk/volar/tree/master/packages/vue-tsc)

These features are curated to provide a seamless development experience from setup to deployment, ensuring that your Vuetify application is both powerful and maintainable.

## 💡 Usage

This section covers how to start the development server and build your project for production.

### Starting the Development Server

To start the development server with hot-reload, run the following command. The server will be accessible at [http://localhost:3000](http://localhost:3000):

```bash
yarn dev
```

(Repeat for npm, pnpm, and bun with respective commands.)

> Add NODE_OPTIONS='--no-warnings' to suppress the JSON import warnings that happen as part of the Vuetify import mapping. If you are on Node [v21.3.0](https://nodejs.org/en/blog/release/v21.3.0) or higher, you can change this to NODE_OPTIONS='--disable-warning=5401'. If you don't mind the warning, you can remove this from your package.json dev script.

### Building for Production

To build your project for production, use:

```bash
yarn build
```

(Repeat for npm, pnpm, and bun with respective commands.)

Once the build process is completed, your application will be ready for deployment in a production environment.

## 💪 Support Vuetify Development

This project is built with [Vuetify](https://vuetifyjs.com/en/), a UI Library with a comprehensive collection of Vue components. Vuetify is an MIT licensed Open Source project that has been made possible due to the generous contributions by our [sponsors and backers](https://vuetifyjs.com/introduction/sponsors-and-backers/). If you are interested in supporting this project, please consider:

- [Requesting Enterprise Support](https://support.vuetifyjs.com/)
- [Sponsoring John on Github](https://github.com/users/johnleider/sponsorship)
- [Sponsoring Kael on Github](https://github.com/users/kaelwd/sponsorship)
- [Supporting the team on Open Collective](https://opencollective.com/vuetify)
- [Becoming a sponsor on Patreon](https://www.patreon.com/vuetify)
- [Becoming a subscriber on Tidelift](https://tidelift.com/subscription/npm/vuetify)
- [Making a one-time donation with Paypal](https://paypal.me/vuetify)

## 📑 License

[MIT](http://opensource.org/licenses/MIT)

Copyright (c) 2016-present Vuetify, LLC
