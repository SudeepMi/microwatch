# MicroWatch Development Todo List

## Status Legend
- [ ] Pending
- [/] In Progress
- [x] Completed

---

## Phase 1: Project Setup & Repository Structure
- [x] Create folder structure (`backend/`, `web/dashboard/`, `android/MicroWatch/`, `database/`, `docs/`, `scripts/`)
- [x] Create root `.gitignore` and `.env.example`

## Phase 2: Database Layer & Initialization
- [x] Create `database/init.sql` with tables (`services`, `metrics`, `service_events`, `notifications`)
- [x] Seed initial database script with default microservices

## Phase 3: Demo Microservices
- [x] Create `demo-user-service` (:9001) with `/health`, `/info`, `/admin/failure`, `/admin/recover`
- [x] Create `demo-order-service` (:9002) with `/health`, `/info`, `/admin/failure`, `/admin/recover`
- [x] Create `demo-payment-service` (:9003) with `/health`, `/info`, `/admin/failure`, `/admin/recover`

## Phase 4: Service Registry Microservice
- [x] Create `registry-service` (:8082) Spring Boot project
- [x] Implement `Service` entity, repository, and service layer
- [x] Implement REST Controller `/api/v1/services` (GET, POST, PUT, DELETE)

## Phase 5: Monitoring Microservice
- [x] Create `monitoring-service` (:8081) Spring Boot project
- [x] Implement `@Scheduled` health check scheduler (10s interval)
- [x] Implement metric collection, status transition detection (UP/DOWN)
- [x] Implement REST APIs for `/api/v1/dashboard/summary`, `/api/v1/metrics/latest`, `/api/v1/services/{id}/metrics`

## Phase 6: Notification Microservice
- [x] Create `notification-service` (:8084) Spring Boot project
- [x] Implement `Notification` entity, repository, and event receiver
- [x] Expose REST APIs `/api/v1/notifications` and `PUT /api/v1/notifications/{id}/read`

## Phase 7: API Gateway Microservice
- [x] Create `api-gateway` (:8080) Spring Boot project
- [x] Implement HTTP routing for internal microservices
- [x] Implement WebSocket endpoint `/ws/monitoring` for status change broadcasts
- [x] Configure global CORS rules

## Phase 8: Docker Containerization & Docker Compose
- [x] Write `Dockerfile` for each Spring Boot microservice
- [x] Write multi-stage `Dockerfile` for React web dashboard
- [x] Write `docker-compose.yml` linking all backend services, database, and frontend dashboard

## Phase 9: React Web Application (WAD Project)
- [x] Setup React + Vite project in `web/dashboard/`
- [x] Implement sleek dark design system CSS with smooth transitions and glassmorphism
- [x] Implement Page Routing (`/dashboard`, `/services`, `/services/new`, `/services/:id`, `/services/:id/edit`, `/notifications`)
- [x] Implement `ServiceForm.jsx` with full client-side validation
- [x] Implement Recharts integration for response-time metrics
- [x] Implement WebSocket client for live dashboard updates

## Phase 10: Android Mobile Application (MAD Project)
- [x] Setup Java Android project in `android/MicroWatch/`
- [x] Implement Retrofit REST client (`ApiService.java`)
- [x] Implement Dashboard, Services, Service Details, and Notifications screens in Java + XML
- [x] Implement `NotificationManager` and `NotificationChannel` (`microwatch_alerts`)
- [x] Implement `WorkManager` for background synchronization
- [x] Implement `ConnectivityManager` network check
- [x] Implement Notification click Intent to open Service Details

## Phase 11: System Integration & Failure Simulation
- [x] Verify full system startup with `docker compose up --build`
- [x] Test failure simulation flow (`POST /admin/failure` -> WebSocket alert -> React update -> Android notification)
- [x] Test recovery simulation flow (`POST /admin/recover` -> UP status recovery)

## Phase 12: Documentation & Academic Deliverables
- [x] Create comprehensive `README.md`
- [x] Create academic docs in `docs/`: `architecture.md`, `api.md`, `database.md`, `deployment.md`, `user-manual.md`
