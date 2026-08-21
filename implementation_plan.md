# UI Revamp Implementation Plan — MicroWatch

Revamp the UI design system of both the **React Web Dashboard** and **Android Mobile Application** to strictly follow [`style.guide.md`](file:///d:/sudeep-projects/microsercvice-monitor/style.guide.md).

> **Design Objective**: Transform MicroWatch into a **minimalist, calm, precise, light-first observability SaaS product** (resembling Vercel / Datadog / Better Stack) rather than a dark/neon panel.

---

## User Review Required

> [!IMPORTANT]
> - **Light Theme First**: Background `#F7F8FA`, Content surfaces `#FFFFFF`, Secondary bg `#F1F3F5`, Borders `#E4E7EC`.
> - **Typography**: Primary `Inter`, Monospace `JetBrains Mono` reserved for URLs, endpoints, response times (`42 ms`), and HTTP codes.
> - **Accent Color**: Indigo `#4F46E5` for primary buttons, active navigation, links, and focus rings.
> - **Restrained Status Colors**: Small status dot `● UP` (`#16A34A` / `#ECFDF3`), `● DOWN` (`#DC2626` / `#FEF2F2`), `● UNKNOWN` (`#D97706` / `#FFFBEB`). No large colored card backgrounds.
> - **Web & Mobile Sync**: Both React Web Dashboard and native Android App share the exact same color palette, typography hierarchy, and status representation.

---

## Proposed Changes

### 1. Web Dashboard (`web/dashboard`)

#### [MODIFY] [`src/index.css`](file:///d:/sudeep-projects/microsercvice-monitor/web/dashboard/src/index.css)
- Replace dark theme CSS tokens with light SaaS palette (`#F7F8FA` bg, `#FFFFFF` cards, `#E4E7EC` borders).
- Set `Inter` as primary font, `JetBrains Mono` for `.font-mono`.
- Configure clean SaaS inputs (42px height, 8px radius, `#D0D5DD` border, indigo focus ring).
- Configure minimal card styling (12px radius, subtle border, shadowless/minimal shadow).

#### [MODIFY] [`src/components/StatusBadge.jsx`](file:///d:/sudeep-projects/microsercvice-monitor/web/dashboard/src/components/StatusBadge.jsx)
- Update badge to 8px dot + label (`● UP` / `● DOWN` / `● UNKNOWN`) with light background pills (`#ECFDF3`, `#FEF2F2`, `#FFFBEB`).

#### [MODIFY] [`src/components/Sidebar.jsx`](file:///d:/sudeep-projects/microsercvice-monitor/web/dashboard/src/components/Sidebar.jsx) & [`src/components/Navbar.jsx`](file:///d:/sudeep-projects/microsercvice-monitor/web/dashboard/src/components/Navbar.jsx)
- Sidebar width 220px, `#FFFFFF` background, `#E4E7EC` right border. Active item `#EEF2FF` with `#4338CA` text.
- Navbar clean light header with subtle socket status badge.

#### [MODIFY] [`src/components/MetricCard.jsx`](file:///d:/sudeep-projects/microsercvice-monitor/web/dashboard/src/components/MetricCard.jsx) & [`src/components/ServiceCard.jsx`](file:///d:/sudeep-projects/microsercvice-monitor/web/dashboard/src/components/ServiceCard.jsx)
- Metric Card: 28-32px number, 12px radius, clean white surface.
- Service Card & Health Table: Clean SaaS presentation with monospace response times (`42 ms`) and URLs.

#### [MODIFY] [`src/components/ResponseChart.jsx`](file:///d:/sudeep-projects/microsercvice-monitor/web/dashboard/src/components/ResponseChart.jsx)
- Update Recharts styling for light background, subtle `#EEF0F2` gridlines, and clean indigo/cyan line.

#### [MODIFY] [`src/pages/Dashboard.jsx`](file:///d:/sudeep-projects/microsercvice-monitor/web/dashboard/src/pages/Dashboard.jsx) & Other Pages
- Update layout to Information-First hierarchy (Summary Cards -> Service Health Table -> Response Time Chart -> Recent Events).
- Update Simulator Panel to subtle light card style.

---

### 2. Android Mobile Application (`android/MicroWatch`)

#### [MODIFY] [`res/values/colors.xml`](file:///d:/sudeep-projects/microsercvice-monitor/android/MicroWatch/app/src/main/res/values/colors.xml)
- Update color tokens to light SaaS palette:
  - `bg_dark`: `#F7F8FA`
  - `bg_card`: `#FFFFFF`
  - `primary`: `#4F46E5`
  - `primary_dark`: `#4338CA`
  - `text_primary`: `#111827`
  - `text_secondary`: `#667085`
  - `border_color`: `#E4E7EC`
  - `accent_emerald`: `#16A34A`, `status_up_bg`: `#ECFDF3`
  - `accent_rose`: `#DC2626`, `status_down_bg`: `#FEF2F2`

#### [MODIFY] [`res/values/themes.xml`](file:///d:/sudeep-projects/microsercvice-monitor/android/MicroWatch/app/src/main/res/values/themes.xml)
- Set light status bar & light window background (`#F7F8FA`).

#### [MODIFY] Layouts & Adapters
- Update `activity_dashboard.xml`, `item_service.xml`, `item_notification.xml`, `activity_service_details.xml`.
- Update `ServiceAdapter.java` status display to `● UP`, `● DOWN`, `● UNKNOWN` with restrained color badges.

---

## Verification Plan

### Automated Verification
1. Rebuild React Web Dashboard (`npm run build` in `web/dashboard`) to verify zero syntax/CSS errors.

### Manual Verification
1. Open React Web Dashboard and verify light theme, typography, status badges, metric cards, forms, and charts match `style.guide.md`.
2. Inspect Android XML resources and Java adapters to verify light theme parity.
