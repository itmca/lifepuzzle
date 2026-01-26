# Shared Packages

This directory is intended for shared configuration, libraries, or types that are common across multiple applications (e.g., between `apps/rn` and `apps/backend`).

Although React Native (TypeScript) and Backend (Java/Go) use different languages, they can share language-agnostic resources.

## Potential Use Cases

### 1. API Specifications (`packages/api-schema`)
*   **Content**: OpenAPI (Swagger) YAML/JSON files.
*   **Usage**:
    *   **Backend**: Use as the contract to validate implementation or generate interfaces.
    *   **Frontend**: Auto-generate TypeScript types and API clients (using tools like Orval or OpenAPI Generator).
    *   **Benefit**: Ensures Type safety across the network boundary.

### 2. Design Tokens (`packages/design-tokens`)
*   **Content**: JSON files defining colors, typography, spacing, etc.
*   **Usage**:
    *   **Frontend**: Convert to TypeScript theme objects.
    *   **Backend**: Use in HTML email templates or admin dashboards.
    *   **Benefit**: Maintains consistent branding across all touchpoints.

### 3. Shared Configuration (`packages/config`)
*   **Content**: ESLint rules, Prettier config, Git hooks.
*   **Usage**: Enforce consistent code style and commit conventions across the entire monorepo.
