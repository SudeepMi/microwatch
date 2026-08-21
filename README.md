# MicroWatch — Cloud-Based Microservice Monitoring Platform

**MicroWatch** is a lightweight, cloud-based microservice monitoring platform built to monitor the health, availability, response time, uptime, and status transitions of distributed microservices.

This project is structured for academic submission across three courses:
1. **Cloud Computing**: Containerized microservice architecture, Docker Compose orchestration, AWS EC2 deployment.
2. **Web Application Development (WAD)**: React + Vite dashboard, forms with validation, CRUD operations, Recharts data visualization, real-time WebSockets.
3. **Mobile Application Development (MAD)**: Native Android application built in **Java**, MVVM architecture, Retrofit API client, WorkManager background sync, ConnectivityManager network check, local NotificationManager alerts.

---

## 🏛️ System Architecture

```
                    React Web Dashboard                 Android App (Java)
                 (Dashboard, Forms, Charts)          (MVVM, WorkManager, Alerts)
                            |                                    |
                       REST / WebSocket                         REST
                            |                                    |
                            v                                    v
                  +--------------------------------------------------+
                  |            Spring Boot Gateway (:8080)          |
                  +------------------------+-------------------------+
                                           |
                   +-----------------------+-----------------------+
                   |                       |                       |
                   v                       v                       v
          +-----------------+     +-----------------+     +-----------------+
          |  Monitoring     |     |  Service        |     |  Notification   |
          |  Service (:8081)|     |  Registry (:8082|     |  Service (:8084)|
          +--------+--------+     +--------+--------+     +--------+--------+
                   |                       |                       |
                   +-----------------------+-----------------------+
                                           |
                                           v
                                 +-------------------+
                                 | PostgreSQL (:5432)|
                                 +-------------------+
                                           | (Health Check Polling every 10s)
                                           v
                   +-----------------------+-----------------------+
                   |                       |                       |
                   v                       v                       v
          +-----------------+     +-----------------+     +-----------------+
          | User Service    |     | Order Service   |     | Payment Service |
          | (:9001)         |     | (:9002)         |     | (:9003)         |
          +-----------------+     +-----------------+     +-----------------+
```

---

## 🚀 Quick Start with Docker

### Prerequisites
- Docker Engine 20.10+
- Docker Compose v2+

### Run Complete System
```bash
docker compose up --build
```

Access the components:
- **React Web Dashboard**: [http://localhost:5173](http://localhost:5173)
- **API Gateway**: [http://localhost:8080](http://localhost:8080)
- **PostgreSQL Database**: `localhost:5432` (`db: microwatch`, `user: microwatch`, `pass: microwatch`)

---

## 💻 Tech Stack & Microservices

### Backend Microservices (Java 21 / Spring Boot 3)
- **API Gateway (`:8080`)**: Unified REST entrypoint, WebSocket broker (`/ws/monitoring`).
- **Registry Service (`:8082`)**: Service registration CRUD APIs (`/api/v1/services`).
- **Monitoring Service (`:8081`)**: `@Scheduled` health check loop (10s), metric recording, state transition detection.
- **Notification Service (`:8084`)**: Event persistence & alert API (`/api/v1/notifications`).
- **Demo Services**:
  - `User Service` (`:9001`)
  - `Order Service` (`:9002`)
  - `Payment Service` (`:9003`)

### Web Dashboard (React + Vite)
- Forms with client-side validation (`ServiceForm.jsx`).
- Full CRUD operations on microservice targets.
- Live WebSockets for instant UI updates on UP/DOWN status changes without page refresh.
- Recharts historical response time graph.

### Android Application (Java + XML)
- Built in pure Java using XML layouts.
- Retrofit REST client (`ApiService.java`).
- `NotificationManager` + `NotificationChannel` (`microwatch_alerts`).
- `WorkManager` for background synchronization.
- `ConnectivityManager` network availability checking.
- Tapping notification opens `ServiceDetailsActivity` for the affected microservice.

---

## 🧪 Demo Failure & Recovery Simulation

To demonstrate automated monitoring and real-time alert generation:

1. **Simulate Service Failure**:
   ```bash
   curl -X POST http://localhost:9003/admin/failure
   ```
   - Monitoring service detects Payment Service is `DOWN`.
   - Metric saved to PostgreSQL, `service_events` logged, `notifications` created.
   - Gateway broadcasts WebSocket event to React Web Dashboard (showing live alert toast & badge change to `🔴 DOWN`).
   - Android background sync detects alert & displays local notification: *"Payment Service is DOWN"*.

2. **Simulate Service Recovery**:
   ```bash
   curl -X POST http://localhost:9003/admin/recover
   ```
   - System detects status transition `DOWN -> UP` and issues recovery alert.

---

## 📚 Documentation

Complete academic documentation is available in [`docs/`](file:///d:/sudeep-projects/microsercvice-monitor/docs/):
- [`docs/architecture.md`](file:///d:/sudeep-projects/microsercvice-monitor/docs/architecture.md): System architecture and component interactions.
- [`docs/api.md`](file:///d:/sudeep-projects/microsercvice-monitor/docs/api.md): REST and WebSocket API specification.
- [`docs/database.md`](file:///d:/sudeep-projects/microsercvice-monitor/docs/database.md): PostgreSQL schema and entity relationships.
- [`docs/deployment.md`](file:///d:/sudeep-projects/microsercvice-monitor/docs/deployment.md): AWS EC2 deployment guide.
- [`docs/user-manual.md`](file:///d:/sudeep-projects/microsercvice-monitor/docs/user-manual.md): Operating instructions for web and Android apps.
