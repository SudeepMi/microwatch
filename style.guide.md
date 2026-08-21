# MicroWatch — UI Style & Typography Guidelines

Use the following as an **additional design prompt/specification for Antigravity AI**. The visual identity should communicate **reliability, observability, clarity, and technical precision** rather than looking like a generic admin dashboard.

---

# 1. Design Direction

**MicroWatch should feel like a premium, modern observability product — minimal, calm, precise, and trustworthy.**

The interface should communicate:

* **Reliability** → clear service health indicators
* **Visibility** → information is easy to scan
* **Speed** → response-time and status information are immediately understandable
* **Control** → service management feels deliberate and simple
* **Technical sophistication** → modern cloud/microservice aesthetic
* **Trust** → restrained colors, excellent typography, predictable interactions

### Design keywords

> **Minimal · Elegant · Clean · Technical · Calm · Precise · Modern · Reliable · Lightweight**

Avoid making it look like:

* A generic Bootstrap admin panel
* A cryptocurrency dashboard
* A neon "developer" dashboard
* A crowded enterprise monitoring system
* A dark cyber-security interface
* A template with excessive cards and gradients

---

# 2. Theme

## Primary Theme: Light

MicroWatch must use a **light-first interface**.

The overall visual appearance should be:

```text
Bright background
      ↓
White content surfaces
      ↓
Subtle borders
      ↓
Dark typography
      ↓
Small, meaningful status colors
```

Do not use large colored backgrounds.

Do not use gradients as a primary visual element.

Do not use excessive shadows.

---

# 3. Color System

Use a restrained neutral palette.

### Background

```text
Application Background
#F7F8FA

Secondary Background
#F1F3F5

Primary Surface
#FFFFFF
```

### Typography

```text
Primary Text
#111827

Secondary Text
#667085

Muted Text
#98A2B3
```

### Borders

```text
Primary Border
#E4E7EC

Subtle Border
#EEF0F2
```

---

# 4. Status Colors

Status colors are extremely important because **service health is the core objective of MicroWatch**.

Use colors only where they communicate actual system state.

### UP

```text
Primary:
#16A34A

Light Background:
#ECFDF3
```

Display:

```text
● UP
```

or

```text
● Healthy
```

---

### DOWN

```text
Primary:
#DC2626

Light Background:
#FEF2F2
```

Display:

```text
● DOWN
```

---

### UNKNOWN / WARNING

```text
Primary:
#D97706

Light Background:
#FFFBEB
```

Display:

```text
● UNKNOWN
```

---

### Informational

Use a restrained blue:

```text
#2563EB
```

For:

* Links
* Active navigation
* Information
* Selected states
* System activity

---

# 5. Important Color Rule

**Do not color the entire UI according to service status.**

For example, do NOT create:

```text
████████████████████
GREEN Dashboard
████████████████████
```

Instead:

```text
Payment Service

● UP

Response Time
42 ms
```

Only the status indicator should use the status color.

This keeps the interface elegant.

---

# 6. Brand Accent

Use a sophisticated **indigo/blue accent** as the primary interaction color.

Recommended:

```text
Primary Accent
#4F46E5
```

Use it for:

* Primary buttons
* Active sidebar item
* Focus states
* Links
* Selected tabs
* Interactive controls

Avoid using the accent color everywhere.

---

# 7. Typography

Use **Inter** as the primary typeface.

### Font

```text
Inter
```

Fallback:

```text
Inter, system-ui, -apple-system, BlinkMacSystemFont,
"Segoe UI", sans-serif
```

Inter works particularly well because MicroWatch contains:

* Numbers
* Response times
* Status labels
* Tables
* Technical information
* Dashboard metrics

---

# 8. Typography Hierarchy

## Application Title

```text
Font:
Inter

Weight:
600

Size:
18–20px

Letter spacing:
-0.02em
```

Example:

```text
MicroWatch
```

---

## Page Title

```text
Font size:
28px

Weight:
600

Line height:
1.2

Letter spacing:
-0.02em
```

Example:

> Monitoring Overview

Avoid huge 48–64px dashboard headings.

---

## Section Heading

```text
Size:
18px

Weight:
600

Line height:
1.4
```

Example:

> Service Health

---

## Card Heading

```text
Size:
14–15px

Weight:
500–600
```

---

## Body Text

```text
Size:
14px

Weight:
400

Line height:
1.5
```

---

## Small Metadata

```text
Size:
12–13px

Weight:
400–500

Color:
#667085
```

Examples:

```text
Last checked 12 seconds ago

Updated 21 Aug 2026
```

---

# 9. Dashboard Numbers

Large numbers are important because monitoring depends on quick visual scanning.

Example:

```text
Healthy Services

4
```

Use:

```text
Font size:
28–32px

Weight:
600

Line height:
1.1
```

Avoid oversized numbers such as:

```text
84px
```

The dashboard should feel sophisticated rather than flashy.

---

# 10. Monospace Typography

Use a monospace font **only for technical values**.

Recommended:

```text
JetBrains Mono
```

Use it for:

* Response times
* HTTP status codes
* Ports
* URLs
* Service endpoints
* API paths
* IP addresses
* Technical logs

Example:

```text
payment-service
localhost:9003
GET /health
200 OK
42 ms
```

Do not use monospace for normal UI text.

---

# 11. Logo / Brand Concept

Create a simple wordmark:

```text
MicroWatch
```

Possible visual symbol:

```text
M + pulse/monitoring line
```

The logo should subtly communicate:

> **Microservices + continuous observation**

Avoid:

* Complex server illustrations
* Cloud clipart
* Shields
* Eye icons
* Excessive gradients

A simple geometric mark is preferred.

---

# 12. Layout

Use a spacious desktop layout.

```text
┌─────────────────────────────────────────────────────┐
│ MicroWatch                              User        │
├──────────────┬──────────────────────────────────────┤
│              │                                      │
│ Dashboard    │  Monitoring Overview                 │
│ Services     │                                      │
│ Notifications│  Metrics                            │
│              │                                      │
│              │  Service Health                      │
│              │                                      │
│              │  Charts / Events                     │
│              │                                      │
└──────────────┴──────────────────────────────────────┘
```

---

# 13. Sidebar

Keep the sidebar minimal.

Width:

```text
220–240px
```

Background:

```text
#FFFFFF
```

Border-right:

```text
1px solid #E4E7EC
```

Navigation items should be simple.

```text
◉ Dashboard

▣ Services

◌ Notifications
```

Use icons from a consistent icon library such as **Lucide**.

Do not mix multiple icon styles.

---

# 14. Navigation States

### Default

```text
Color:
#667085

Background:
transparent
```

### Hover

```text
Background:
#F7F8FA
```

### Active

```text
Background:
#EEF2FF

Text:
#4338CA
```

Use a subtle active indicator if required.

---

# 15. Dashboard Cards

Do not create excessive cards.

Prefer **4 primary metric cards**:

```text
┌────────────────┐ ┌────────────────┐
│ Total Services │ │ Healthy        │
│                │ │ Services       │
│ 5              │ │ 4              │
└────────────────┘ └────────────────┘

┌────────────────┐ ┌────────────────┐
│ Down Services  │ │ Avg Response   │
│                │ │ Time           │
│ 1              │ │ 52 ms          │
└────────────────┘ └────────────────┘
```

Card styling:

```text
Background: #FFFFFF
Border: #E4E7EC
Radius: 12px
Shadow: extremely subtle or none
```

---

# 16. Avoid "Card Explosion"

Do NOT turn every piece of information into a card.

Bad:

```text
┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐
│Card │ │Card │ │Card │ │Card │
└─────┘ └─────┘ └─────┘ └─────┘

┌─────┐ ┌─────┐ ┌─────┐
│Card │ │Card │ │Card │
└─────┘ └─────┘ └─────┘
```

Instead, combine related information into sections.

---

# 17. Service Health Visualization

This is the **hero component of MicroWatch**.

Create a clean service table:

| Service         | Status | Response | Uptime | Last Checked |
| --------------- | ------ | -------: | -----: | ------------ |
| user-service    | ● UP   |    42 ms |  99.9% | 8 sec ago    |
| order-service   | ● UP   |    61 ms |  99.7% | 8 sec ago    |
| payment-service | ● DOWN |        — |  97.1% | 8 sec ago    |

The table should be highly readable.

---

# 18. Status Indicator

Use a small circular indicator.

```text
● UP
```

Do not use huge colored badges.

Recommended:

```text
8px circle
+
UP
```

The status should be instantly recognizable but visually restrained.

---

# 19. Charts

Charts should answer questions rather than decorate the dashboard.

Use charts for:

### Response Time

```text
Response Time
   │
200│          ╭─╮
150│       ╭──╯ ╰╮
100│   ╭───╯     ╰──
 50│───╯
   └────────────────
```

### Service Availability

Show:

```text
UP
DOWN
UNKNOWN
```

Use minimal grid lines.

Avoid:

* 3D charts
* Gradients
* Excessive legends
* Decorative charts
* Donut charts for everything

---

# 20. Forms

Forms are an important part of the **WAD requirement**.

The Service Registration form should look like a professional SaaS form.

```text
Add Service

Service name
[ payment-service                         ]

Service URL
[ http://payment-service:9003             ]

Health endpoint
[ /health                                 ]

Description
[ Payment processing service              ]
[                                         ]

                     [ Cancel ] [ Add Service ]
```

Use:

```text
Input height:
42–44px

Border radius:
8px

Border:
#D0D5DD
```

---

# 21. Form Focus State

When an input is focused:

```text
Border:
#4F46E5

Subtle focus ring:
rgba(79,70,229,0.12)
```

Keep focus states visible and accessible.

---

# 22. Buttons

Use a restrained hierarchy.

### Primary

```text
Background: #4F46E5
Text: #FFFFFF
Radius: 8px
```

Example:

```text
+ Add Service
```

### Secondary

```text
Background: #FFFFFF
Border: #D0D5DD
Text: #344054
```

### Destructive

Use red only for destructive actions:

```text
Delete Service
```

Do not make every button brightly colored.

---

# 23. Tables

Use tables for monitoring information.

Characteristics:

* Lots of whitespace
* Thin separators
* No heavy borders
* Clear column hierarchy
* Sticky header where useful
* Row hover state

Example:

```text
Service                 Status      Response
────────────────────────────────────────────
user-service            ● UP        42 ms
order-service            ● UP        61 ms
payment-service          ● DOWN      —
```

---

# 24. Alerts

Alerts should be subtle.

### Service Down

```text
┌───────────────────────────────────────────┐
│ ●  Payment Service is unavailable         │
│    Last response: 12:42 AM                │
└───────────────────────────────────────────┘
```

Use a pale red background rather than a saturated red block.

---

# 25. Real-Time Feedback

When a service changes:

```text
Payment Service

● DOWN
```

Animate the transition subtly.

Recommended:

* 150–250ms transition
* Small opacity/scale transition
* No dramatic animations

Avoid:

* Flashing screens
* Pulsing entire cards
* Screen shake
* Large animated banners

MicroWatch should feel **calm even when something fails**.

---

# 26. Notifications

Create a notification center:

```text
Notifications

● Payment Service is DOWN
  2 minutes ago

● Payment Service recovered
  4 minutes ago

● Order Service response time increased
  12 minutes ago
```

Use a small colored indicator.

---

# 27. Empty States

Never show blank screens.

Example:

```text
No services registered

Add your first microservice to start monitoring
its health and performance.

[ + Add Service ]
```

Keep empty states simple.

---

# 28. Loading States

Use subtle skeleton loaders.

Avoid spinning loaders everywhere.

Example:

```text
████████████████
██████████
```

Use skeletons for:

* Dashboard metrics
* Service tables
* Charts

---

# 29. Responsive Design

Desktop:

```text
Sidebar + content
```

Tablet:

```text
Collapsed sidebar
```

Mobile:

```text
Top navigation
+
Scrollable content
```

The service table should become a responsive list/card layout on small screens.

---

# 30. Spacing System

Use an **8px spacing system**.

```text
4px   — micro spacing
8px   — small
12px  — compact
16px  — standard
24px  — section
32px  — large
48px  — major section
64px  — page spacing
```

Do not randomly use dozens of different spacing values.

---

# 31. Border Radius

Use consistent, modest rounding.

```text
Inputs:
8px

Buttons:
8px

Cards:
12px

Large containers:
16px
```

Avoid extremely rounded "pill everything" designs.

---

# 32. Shadows

Use shadows sparingly.

Preferred:

```text
No shadow
```

or extremely subtle:

```text
0 1px 2px rgba(...)
```

Borders should provide most of the visual separation.

---

# 33. Icons

Use **Lucide Icons** consistently.

Recommended icons:

```text
Dashboard       LayoutDashboard
Services        Boxes
Monitoring      Activity
Notifications   Bell
Settings        Settings
Add             Plus
Delete           Trash2
Edit             Pencil
Health           HeartPulse
Response Time   Timer
Cloud            Cloud
```

Icons should generally be:

```text
18–20px
```

Do not use emojis as the actual UI icons.

---

# 34. Animation

MicroWatch should use **micro-interactions**, not decorative animation.

Use:

```text
150–250ms
ease-out
```

For:

* Hover
* Button interaction
* Sidebar transitions
* Status changes
* Modal opening
* Toast notifications

Avoid animation that distracts from monitoring information.

---

# 35. MicroWatch Visual Personality

The UI should feel closer to:

> **Modern developer SaaS + observability platform**

than:

> Traditional college project dashboard.

The final interface should look polished enough to resemble a lightweight professional monitoring product.

---

# 36. Design Principle: Information First

Every visual decision must support the project's primary objective:

> **Know the health of your microservices immediately.**

Therefore:

### Most visually important

1. Service status
2. Number of healthy/down services
3. Response time
4. Recent failures
5. Historical performance

### Less visually important

* Metadata
* Descriptions
* Technical IDs
* Timestamps

The user should understand the system's health within **3–5 seconds of opening the dashboard**.

---

# 37. Recommended Dashboard Composition

Use this layout:

```text
┌──────────────────────────────────────────────────────────┐
│  Monitoring Overview                         + Add Service│
│  Real-time health of your distributed services            │
│                                                          │
│ ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌─────────────┐ │
│ │ Services │ │ Healthy  │ │ Down     │ │ Avg Response│ │
│ │    5     │ │    4     │ │    1     │ │    52 ms    │ │
│ └──────────┘ └──────────┘ └──────────┘ └─────────────┘ │
│                                                          │
│ ┌────────────────────────────────┐ ┌───────────────────┐ │
│ │ Response Time                  │ │ Recent Events     │ │
│ │                                │ │                   │ │
│ │       ╭────╮                   │ │ ● Payment DOWN    │ │
│ │   ╭───╯    ╰────               │ │ ● Order UP        │ │
│ │───╯                             │ │                   │ │
│ └────────────────────────────────┘ └───────────────────┘ │
│                                                          │
│ Service Health                                           │
│ ┌──────────────────────────────────────────────────────┐ │
│ │ Service       Status    Response   Uptime  Checked   │ │
│ │ user          ● UP      42ms       99.9%   8s ago    │ │
│ │ order         ● UP      61ms       99.7%   8s ago    │ │
│ │ payment       ● DOWN    —          97.1%   8s ago    │ │
│ └──────────────────────────────────────────────────────┘ │
└──────────────────────────────────────────────────────────┘
```

---

# 38. Mobile App Visual Style

The Android application should use the **same design language** as the React application.

Light theme:

```text
Background: #F7F8FA
Surface: #FFFFFF
Text: #111827
Accent: #4F46E5
```

The mobile dashboard should prioritize:

```text
Service Health
      ↓
Alerts
      ↓
Response Time
      ↓
Service Details
```

Use Material-style components but keep them visually minimal.

---

# 39. Android Notification Style

Notifications should be concise.

### Failure

**MicroWatch Alert**

> Payment Service is DOWN

### Recovery

**MicroWatch Recovery**

> Payment Service is back UP

Do not put excessive technical information into the notification.

---

# 40. Accessibility

Maintain:

* Good contrast
* Visible focus states
* Minimum comfortable touch targets
* Meaningful labels
* Accessible form errors
* Status information not communicated through color alone

For example:

```text
● UP
```

rather than relying solely on a green dot.

---

# 41. Final Antigravity Design Instruction

Add this to the end of the development prompt:

```text
IMPORTANT UI REQUIREMENT:

The MicroWatch interface must follow a premium minimalist LIGHT theme.

The design must communicate reliability, observability, technical precision, and calmness.

Use:
- Inter typography
- JetBrains Mono for technical values
- White surfaces
- Soft gray background
- Subtle borders
- Minimal shadows
- Indigo as the primary interaction accent
- Green/red/amber ONLY for service health states
- Lucide icons
- 8px spacing system
- 8–16px border radius
- restrained animations
- generous whitespace
- clean tables
- purposeful charts
- professional forms

Do NOT create:
- a generic Bootstrap dashboard
- a dark cyberpunk UI
- neon colors
- excessive gradients
- excessive glassmorphism
- excessive cards
- giant headings
- huge status indicators
- decorative animations
- emoji-based UI icons
- unnecessary visual effects

The dashboard must allow a user to understand the health of the entire microservice ecosystem within 3–5 seconds.

The most visually prominent information must be:
1. Service health
2. Healthy/down service count
3. Response time
4. Recent failures
5. Historical performance

The React web app and Android app must share the same visual identity.

The final result should feel like a polished, lightweight observability SaaS product rather than a college-project admin panel.
```
