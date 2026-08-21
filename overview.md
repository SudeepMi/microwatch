# MicroWatch — Microservice Monitoring Platform

## 1. Project Overview

**MicroWatch** is a lightweight cloud-based microservice monitoring platform designed to monitor the availability, health, response time, and uptime of distributed microservices.

The system consists of:

* A **Java Spring Boot backend** implementing microservices and REST APIs.
* A **React web application** for monitoring and managing services.
* An **Android mobile application** for monitoring services and receiving notifications.
* **PostgreSQL** for persistent storage.
* **Docker and Docker Compose** for containerization.
* **AWS EC2** for cloud deployment.

The project is divided into three academic submissions:

| Subject         | Project Component                            | Primary Technology                            |
| --------------- | -------------------------------------------- | --------------------------------------------- |
| Cloud Computing | Cloud-based Microservice Monitoring Platform | Java, Spring Boot, Docker, AWS                |
| WAD             | Web Monitoring Dashboard                     | React, JavaScript, REST API, Forms            |
| MAD             | Mobile Monitoring Application                | Android, Java/Kotlin, Notifications, REST API |

---

# 2. Objectives

The main objectives of MicroWatch are:

1. Monitor the health of multiple microservices.
2. Detect service failures automatically.
3. Measure service response time.
4. Store monitoring metrics in a database.
5. Provide a web-based monitoring dashboard.
6. Provide a mobile monitoring application.
7. Send mobile notifications when a service goes down or comes back up.
8. Demonstrate REST API communication.
9. Demonstrate Docker-based microservice deployment.
10. Deploy the complete system to a cloud environment.

---

# 3. High-Level Architecture

```text
                         MICROWATCH PLATFORM
                                |
              +-----------------+-----------------+
              |                                   |
              v                                   v
       React Web Application               Android Application
              |                                   |
              | REST API                          | REST API
              | WebSocket                         | Notifications
              |                                   |
              +-----------------+-----------------+
                                |
                                v
                       +----------------+
                       |  API Gateway   |
                       | Spring Boot    |
                       |    :8080       |
                       +-------+--------+
                               |
             +-----------------+-----------------+
             |                 |                 |
             v                 v                 v
      +-------------+   +-------------+   +-------------+
      | Monitoring  |   |  Registry   |   | Notification|
      |  Service    |   |  Service    |   |  Service   |
      |   :8081     |   |   :8082     |   |   :8084    |
      +------+------+   +------+------+   +------+------+
             |                 |                 |
             +-----------------+-----------------+
                               |
                               v
                       +---------------+
                       |  PostgreSQL   |
                       |     :5432     |
                       +---------------+

                               |
                Health checks every 10 seconds
                               |
          +--------------------+--------------------+
          |                    |                    |
          v                    v                    v
   +-------------+      +-------------+      +-------------+
   | User        |      | Order       |      | Payment     |
   | Service     |      | Service     |      | Service     |
   | :9001       |      | :9002       |      | :9003       |
   +-------------+      +-------------+      +-------------+
```

---

# 4. System Components

## 4.1 API Gateway

The API Gateway is the single public entry point for the web and mobile applications.

### Responsibilities

* Receive client requests.
* Route requests to appropriate services.
* Provide a common API endpoint.
* Hide internal microservice addresses.
* Handle CORS.
* Provide WebSocket communication for the React dashboard.

External clients communicate with:

```text
http://SERVER_IP:8080
```

Instead of directly accessing individual microservices.

---

# 4.2 Service Registry

The Service Registry maintains information about monitored microservices.

Each registered service contains:

```text
Service ID
Service Name
Service URL
Health Endpoint
Description
Registration Date
```

Example:

```json
{
  "name": "payment-service",
  "url": "http://payment-service:9003",
  "healthEndpoint": "/health",
  "description": "Handles payment operations"
}
```

---

# 4.3 Monitoring Service

The Monitoring Service is the core component of MicroWatch.

It periodically checks all registered services.

### Monitoring process

```text
Monitoring Scheduler
        |
        v
Get registered services
        |
        v
Call /health endpoint
        |
        +-------- Success --------> UP
        |
        +-------- Failure --------> DOWN
        |
        v
Calculate response time
        |
        v
Store metric in PostgreSQL
        |
        v
Detect status change
        |
        +----------+
        |          |
        v          v
 WebSocket    Notification
   Event         Service
```

The default monitoring interval is:

```text
10 seconds
```

---

# 4.4 Notification Service

The Notification Service handles service-status notifications.

When a service changes from:

```text
UP → DOWN
```

or:

```text
DOWN → UP
```

the Notification Service generates an alert.

Example:

```text
MicroWatch Alert

Payment Service is DOWN.

Response:
Health check failed

Time:
10:30 AM
```

When the service recovers:

```text
MicroWatch Recovery

Payment Service is UP again.

Response Time:
48 ms
```

---

# 4.5 Demo Microservices

Three small microservices are included for demonstration.

### User Service

```text
Port: 9001
```

### Order Service

```text
Port: 9002
```

### Payment Service

```text
Port: 9003
```

Each service exposes:

```http
GET /health
GET /info
```

The services are intentionally simple because their main purpose is to provide monitored endpoints.

---

# 5. Repository Structure

```text
microwatch/
│
├── README.md
├── docker-compose.yml
├── .env.example
├── .gitignore
│
├── backend/
│   │
│   ├── api-gateway/
│   │   ├── src/
│   │   ├── pom.xml
│   │   └── Dockerfile
│   │
│   ├── monitoring-service/
│   │   ├── src/
│   │   │   └── main/
│   │   │       ├── java/
│   │   │       └── resources/
│   │   ├── pom.xml
│   │   └── Dockerfile
│   │
│   ├── registry-service/
│   │   ├── src/
│   │   ├── pom.xml
│   │   └── Dockerfile
│   │
│   ├── notification-service/
│   │   ├── src/
│   │   ├── pom.xml
│   │   └── Dockerfile
│   │
│   ├── demo-user-service/
│   │   ├── src/
│   │   ├── pom.xml
│   │   └── Dockerfile
│   │
│   ├── demo-order-service/
│   │   ├── src/
│   │   ├── pom.xml
│   │   └── Dockerfile
│   │
│   └── demo-payment-service/
│       ├── src/
│       ├── pom.xml
│       └── Dockerfile
│
├── web/
│   └── dashboard/
│       ├── src/
│       │   ├── components/
│       │   ├── pages/
│       │   ├── forms/
│       │   ├── services/
│       │   ├── hooks/
│       │   ├── utils/
│       │   ├── App.jsx
│       │   └── main.jsx
│       ├── package.json
│       ├── vite.config.js
│       └── Dockerfile
│
├── android/
│   └── MicroWatch/
│       ├── app/
│       │   └── src/
│       │       └── main/
│       │           ├── java/
│       │           ├── res/
│       │           └── AndroidManifest.xml
│       ├── build.gradle
│       └── settings.gradle
│
├── database/
│   ├── init.sql
│   └── migrations/
│
├── docs/
│   ├── architecture.md
│   ├── api.md
│   ├── database.md
│   ├── deployment.md
│   └── user-manual.md
│
└── scripts/
    ├── start.sh
    ├── stop.sh
    └── health-check.sh
```

---

# 6. Java Spring Boot Structure

Each Spring Boot service follows a layered architecture.

```text
monitoring-service/
│
├── src/main/java/com/microwatch/monitoring/
│
├── controller/
│   └── MonitoringController.java
│
├── service/
│   └── MonitoringService.java
│
├── repository/
│   └── MetricRepository.java
│
├── model/
│   └── Metric.java
│
├── dto/
│   ├── MetricResponse.java
│   └── HealthResponse.java
│
├── scheduler/
│   └── HealthCheckScheduler.java
│
├── client/
│   └── ServiceHealthClient.java
│
└── MonitoringApplication.java
```

### Technologies

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* PostgreSQL Driver
* Spring WebSocket
* Maven

---

# 7. Database Schema

The system uses PostgreSQL.

## 7.1 Services

```sql
CREATE TABLE services (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    url VARCHAR(255) NOT NULL,
    health_endpoint VARCHAR(100) DEFAULT '/health',
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

## 7.2 Metrics

```sql
CREATE TABLE metrics (
    id BIGSERIAL PRIMARY KEY,

    service_id BIGINT NOT NULL,

    status VARCHAR(20) NOT NULL,

    response_time BIGINT,

    checked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_metric_service
        FOREIGN KEY (service_id)
        REFERENCES services(id)
        ON DELETE CASCADE
);
```

---

## 7.3 Service Events

```sql
CREATE TABLE service_events (
    id BIGSERIAL PRIMARY KEY,

    service_id BIGINT NOT NULL,

    previous_status VARCHAR(20),

    current_status VARCHAR(20),

    message TEXT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_event_service
        FOREIGN KEY (service_id)
        REFERENCES services(id)
        ON DELETE CASCADE
);
```

---

## 7.4 Notifications

```sql
CREATE TABLE notifications (
    id BIGSERIAL PRIMARY KEY,

    service_id BIGINT NOT NULL,

    title VARCHAR(255),

    message TEXT,

    notification_type VARCHAR(50),

    is_read BOOLEAN DEFAULT FALSE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_notification_service
        FOREIGN KEY (service_id)
        REFERENCES services(id)
        ON DELETE CASCADE
);
```

---

# 8. Database Relationship

```text
                    SERVICES
                       |
             +---------+---------+
             |         |         |
             v         v         v
          METRICS    EVENTS   NOTIFICATIONS
```

Relationship:

```text
Service 1 ──────── N Metrics

Service 1 ──────── N Events

Service 1 ──────── N Notifications
```

---

# 9. REST API

All public APIs use:

```text
/api/v1
```

---

## 9.1 Service APIs

### Get all services

```http
GET /api/v1/services
```

### Get service

```http
GET /api/v1/services/{id}
```

### Register service

```http
POST /api/v1/services
```

Request:

```json
{
  "name": "notification-service",
  "url": "http://notification-service:9004",
  "healthEndpoint": "/health",
  "description": "Notification microservice"
}
```

### Update service

```http
PUT /api/v1/services/{id}
```

### Delete service

```http
DELETE /api/v1/services/{id}
```

---

# 10. Monitoring APIs

### Dashboard summary

```http
GET /api/v1/dashboard/summary
```

Response:

```json
{
  "totalServices": 5,
  "healthyServices": 4,
  "downServices": 1,
  "averageResponseTime": 52,
  "overallUptime": 98.7
}
```

### Latest metrics

```http
GET /api/v1/metrics/latest
```

### Service metrics

```http
GET /api/v1/services/{id}/metrics
```

### Service events

```http
GET /api/v1/services/{id}/events
```

### Notifications

```http
GET /api/v1/notifications
```

### Mark notification as read

```http
PUT /api/v1/notifications/{id}/read
```

---

# 11. Demo Service APIs

Each demo service implements:

```http
GET /health
GET /info
```

Example:

```http
GET http://user-service:9001/health
```

Response:

```json
{
  "service": "user-service",
  "status": "UP",
  "version": "1.0.0",
  "timestamp": "2026-07-31T10:30:00"
}
```

---

# 12. Failure Simulation API

For demonstration purposes, each demo service can provide:

```http
POST /admin/failure
```

and:

```http
POST /admin/recover
```

Example:

```http
POST /admin/failure
```

causes:

```text
user-service
     ↓
Simulated failure
     ↓
/health → DOWN
```

This allows the monitoring functionality to be demonstrated without actually crashing the application.

---

# 13. WebSocket API

The React dashboard can receive real-time monitoring events through:

```text
/ws/monitoring
```

Example event:

```json
{
  "type": "STATUS_CHANGE",
  "service": "payment-service",
  "status": "DOWN",
  "responseTime": null,
  "timestamp": "2026-07-31T10:30:00"
}
```

Recovery:

```json
{
  "type": "STATUS_CHANGE",
  "service": "payment-service",
  "status": "UP",
  "responseTime": 45,
  "timestamp": "2026-07-31T10:31:00"
}
```

---

# 14. React Web Application

## WAD Requirements

The React application must demonstrate:

* React components
* React Router
* REST API integration
* Forms
* Form validation
* CRUD operations
* State management
* WebSocket/live updates
* Responsive design

---

# 15. React Folder Structure

```text
web/dashboard/

src/
│
├── components/
│   ├── Navbar.jsx
│   ├── Sidebar.jsx
│   ├── ServiceCard.jsx
│   ├── StatusBadge.jsx
│   ├── MetricCard.jsx
│   ├── ResponseChart.jsx
│   └── NotificationList.jsx
│
├── pages/
│   ├── Dashboard.jsx
│   ├── Services.jsx
│   ├── AddService.jsx
│   ├── EditService.jsx
│   ├── ServiceDetails.jsx
│   └── Notifications.jsx
│
├── forms/
│   ├── ServiceForm.jsx
│   └── ServiceValidation.js
│
├── services/
│   ├── api.js
│   └── websocket.js
│
├── hooks/
│   └── useMonitoringSocket.js
│
├── utils/
│   └── formatters.js
│
├── App.jsx
└── main.jsx
```

---

# 16. React Forms

Forms are a required part of the WAD implementation.

## Add Service Form

```text
+----------------------------------+
|       Register Service           |
+----------------------------------+
|                                  |
| Service Name                     |
| [____________________________]   |
|                                  |
| Service URL                      |
| [____________________________]   |
|                                  |
| Health Endpoint                  |
| [____________________________]   |
|                                  |
| Description                      |
| [____________________________]   |
| [____________________________]   |
|                                  |
|        [ Register Service ]      |
|                                  |
+----------------------------------+
```

Form fields:

```text
name
url
healthEndpoint
description
```

Validation:

* Name cannot be empty.
* URL must be valid.
* Health endpoint must start with `/`.
* Description is optional.

---

# 17. React Pages

## Dashboard

Display:

```text
Total Services
Healthy Services
Down Services
Average Response Time
Overall Uptime
```

And:

* Service status cards.
* Response-time chart.
* Recent service events.

---

## Services

Display all registered services.

Actions:

```text
View
Edit
Delete
```

---

## Add Service

Use the React form to register a new microservice.

---

## Edit Service

Use the same form with existing service data.

---

## Service Details

Display:

```text
Service Name
Current Status
Response Time
Uptime
Historical Metrics
Recent Events
```

---

## Notifications

Display:

```text
Payment Service DOWN
10:30 AM

Order Service RECOVERED
10:32 AM
```

---

# 18. Android Application

The Android application acts as a mobile monitoring client.

## MAD Requirements

The application should demonstrate:

* Android UI
* REST API integration
* Notifications
* Navigation
* Android permissions
* Background processing
* Network connectivity
* Local notification handling
* Service details
* Mobile-friendly UI

---

# 19. Android Architecture

```text
Android App
     |
     +-- UI
     |
     +-- ViewModel
     |
     +-- Repository
     |
     +-- Retrofit API
     |
     +-- Notification Manager
     |
     +-- Android APIs
     |
     v
API Gateway
```

Recommended structure:

```text
MicroWatch/
│
└── app/src/main/
    │
    ├── java/com/microwatch/
    │   │
    │   ├── data/
    │   │   ├── api/
    │   │   │   ├── ApiService.java
    │   │   │   └── RetrofitClient.java
    │   │   │
    │   │   └── model/
    │   │       ├── Service.java
    │   │       ├── Metric.java
    │   │       ├── Notification.java
    │   │       └── DashboardSummary.java
    │   │
    │   ├── repository/
    │   │   └── MonitoringRepository.java
    │   │
    │   ├── ui/
    │   │   ├── dashboard/
    │   │   ├── services/
    │   │   ├── details/
    │   │   └── notifications/
    │   │
    │   ├── notification/
    │   │   ├── NotificationHelper.java
    │   │   └── NotificationReceiver.java
    │   │
    │   └── MainActivity.java
    │
    └── res/
        ├── layout/
        ├── drawable/
        ├── mipmap/
        └── values/
```

Java can be used for the Android implementation to keep the entire backend/mobile stack Java-oriented.

---

# 20. Android REST APIs

The Android application uses Retrofit to communicate with the backend.

Example interface:

```java
@GET("api/v1/services")
Call<List<Service>> getServices();

@GET("api/v1/dashboard/summary")
Call<DashboardSummary> getDashboardSummary();

@GET("api/v1/services/{id}/metrics")
Call<List<Metric>> getMetrics(@Path("id") Long id);

@GET("api/v1/notifications")
Call<List<Notification>> getNotifications();
```

---

# 21. Android Notification APIs

Notifications are an important feature of the MAD submission.

The application should use Android's notification APIs.

### Notification flow

```text
Monitoring Service
       |
       v
Payment Service DOWN
       |
       v
Notification Service
       |
       v
Notification API
       |
       v
Android Device
       |
       v
┌─────────────────────────────┐
│ MicroWatch                  │
│                             │
│ 🔴 Payment Service DOWN     │
│ Health check failed         │
└─────────────────────────────┘
```

---

# 22. Android Notification Channel

Create a notification channel:

```text
Channel ID:
microwatch_alerts

Channel Name:
MicroWatch Alerts
```

Notification types:

```text
SERVICE_DOWN
SERVICE_RECOVERED
SYSTEM_ALERT
```

Example:

```text
🔴 MicroWatch Alert

Payment Service is DOWN

Tap to view service details.
```

Recovery:

```text
🟢 MicroWatch Recovery

Payment Service is UP again

Response time: 47 ms
```

---

# 23. Android Notification Permissions

For newer Android versions, request the appropriate notification permission at runtime.

The application should:

1. Create notification channel.
2. Check notification permission.
3. Request permission when required.
4. Display service-status notifications.
5. Open the relevant service details when the notification is tapped.

---

# 24. Other Android APIs

To make the MAD project demonstrate more than just a REST client, the application can use:

### NotificationManager

For service alerts.

### WorkManager

For periodic background synchronization.

```text
Every 15 minutes
      ↓
Fetch latest service status
      ↓
Compare with previous state
      ↓
Generate notification if required
```

### ConnectivityManager

To detect whether the mobile device has network connectivity.

Example:

```text
Internet Available
      ↓
Call API

Internet Unavailable
      ↓
Show offline state
```

### SharedPreferences / DataStore

Store:

```text
Server URL
Notification preference
Last known service status
```

### Intent

Use intents to:

* Open service details.
* Open notification details.
* Navigate between Android screens.

---

# 25. Android Screens

## Dashboard

```text
MicroWatc
--------------------------------
Total Services       5

Healthy              4

Down                 1

Average Response     52 ms
--------------------------------

Recent Alerts

🔴 Payment Service DOWN
🟢 Order Service RECOVERED
```

---

## Services

```text
Services

🟢 User Service
   32 ms

🟢 Order Service
   45 ms

🔴 Payment Service
   DOWN
```

---

## Service Details

```text
Payment Service

Status
🔴 DOWN

Uptime
96.4%

Response Time
--

Last Checked
10:30 AM

Recent Events
----------------
DOWN
UP
UP
DOWN
```

---

## Notifications

```text
Notifications

🔴 Payment Service DOWN
   10:30 AM

🟢 Order Service RECOVERED
   10:28 AM
```

---

# 26. Docker Compose

```yaml
services:

  postgres:
    image: postgres:16
    container_name: microwatch-db

    environment:
      POSTGRES_DB: microwatch
      POSTGRES_USER: microwatch
      POSTGRES_PASSWORD: microwatch

    volumes:
      - postgres_data:/var/lib/postgresql/data

    networks:
      - microwatch


  registry-service:
    build: ./backend/registry-service
    container_name: registry-service

    environment:
      DB_HOST: postgres
      DB_NAME: microwatch
      DB_USER: microwatch
      DB_PASSWORD: microwatch

    depends_on:
      - postgres

    ports:
      - "8082:8082"

    networks:
      - microwatch


  monitoring-service:
    build: ./backend/monitoring-service
    container_name: monitoring-service

    environment:
      DB_HOST: postgres
      DB_NAME: microwatch
      DB_USER: microwatch
      DB_PASSWORD: microwatch
      REGISTRY_URL: http://registry-service:8082

    depends_on:
      - postgres
      - registry-service

    ports:
      - "8081:8081"

    networks:
      - microwatch


  notification-service:
    build: ./backend/notification-service
    container_name: notification-service

    environment:
      DB_HOST: postgres
      DB_NAME: microwatch
      DB_USER: microwatch
      DB_PASSWORD: microwatch

    depends_on:
      - postgres

    ports:
      - "8084:8084"

    networks:
      - microwatch


  api-gateway:
    build: ./backend/api-gateway
    container_name: api-gateway

    environment:
      MONITORING_URL: http://monitoring-service:8081
      REGISTRY_URL: http://registry-service:8082
      NOTIFICATION_URL: http://notification-service:8084

    depends_on:
      - monitoring-service
      - registry-service
      - notification-service

    ports:
      - "8080:8080"

    networks:
      - microwatch


  user-service:
    build: ./backend/demo-user-service
    container_name: user-service

    ports:
      - "9001:9001"

    networks:
      - microwatch


  order-service:
    build: ./backend/demo-order-service
    container_name: order-service

    ports:
      - "9002:9002"

    networks:
      - microwatch


  payment-service:
    build: ./backend/demo-payment-service
    container_name: payment-service

    ports:
      - "9003:9003"

    networks:
      - microwatch


  dashboard:
    build: ./web/dashboard
    container_name: microwatch-dashboard

    ports:
      - "3000:80"

    depends_on:
      - api-gateway

    networks:
      - microwatch


volumes:

  postgres_data:


networks:

  microwatch:
    driver: bridge
```

---

# 27. Docker Architecture

```text
Docker Host
│
├── API Gateway
│
├── Monitoring Service
│
├── Registry Service
│
├── Notification Service
│
├── User Service
│
├── Order Service
│
├── Payment Service
│
├── PostgreSQL
│
└── React Dashboard
```

All backend services communicate through the Docker network:

```text
microwatch
```

---

# 28. Cloud Deployment

The recommended cloud architecture is:

```text
                         Internet
                             |
                             v
                    +----------------+
                    |    AWS EC2     |
                    |                |
                    | Docker Compose |
                    +-------+--------+
                            |
          +-----------------+------------------+
          |                 |                  |
          v                 v                  v
       React          API Gateway          PostgreSQL
          |                 |
          |          +------+------+
          |          |             |
          |          v             v
          |     Monitoring      Registry
          |          |
          |          v
          |    Demo Services
          |
          v
       Browser


Android
   |
   v
AWS EC2
   |
   v
API Gateway
```

For a production-style deployment, PostgreSQL can later be moved to AWS RDS.

For the tiny academic version, PostgreSQL inside Docker is sufficient.

---

# 29. Development Sequence

## Phase 1 — Project Setup

### Step 1

Create Git repository.

```text
microwatch
```

### Step 2

Create Spring Boot projects.

```text
api-gateway
monitoring-service
registry-service
notification-service
user-service
order-service
payment-service
```

### Step 3

Create React application.

### Step 4

Create Android application.

---

# Phase 2 — Database

### Step 5

Set up PostgreSQL.

### Step 6

Create:

```text
services
metrics
service_events
notifications
```

### Step 7

Insert initial demo services.

```text
user-service
order-service
payment-service
```

---

# Phase 3 — Demo Microservices

Implement:

```http
GET /health
GET /info
POST /admin/failure
POST /admin/recover
```

Test every service individually.

---

# Phase 4 — Service Registry

Implement:

```http
GET /services
POST /services
PUT /services/{id}
DELETE /services/{id}
```

Register the demo services.

---

# Phase 5 — Monitoring Service

Implement the scheduler.

```text
Every 10 seconds:

Get services
      ↓
Call health endpoint
      ↓
Measure response time
      ↓
UP / DOWN
      ↓
Save metric
```

---

# Phase 6 — Notification Service

Detect:

```text
UP → DOWN
```

and:

```text
DOWN → UP
```

Create notification records.

---

# Phase 7 — API Gateway

Route:

```text
/api/v1/services
/api/v1/metrics
/api/v1/dashboard
/api/v1/notifications
```

through the gateway.

---

# Phase 8 — Docker

Create Dockerfiles for all backend services.

Create:

```text
docker-compose.yml
```

Run:

```bash
docker compose up --build
```

Verify every container.

---

# Phase 9 — React Application

Implement in this order:

1. Dashboard.
2. Service list.
3. Service registration form.
4. Service edit form.
5. Service deletion.
6. Service details.
7. Metrics chart.
8. Notifications.
9. WebSocket live updates.

This satisfies the WAD requirement for **React forms and CRUD operations**.

---

# Phase 10 — Android Application

Implement:

1. Dashboard.
2. Service list.
3. Service details.
4. Retrofit API integration.
5. Notification channel.
6. Notification permission.
7. Service-down notification.
8. Service-recovery notification.
9. WorkManager periodic synchronization.
10. Connectivity detection.
11. Notification click navigation.

This satisfies the MAD requirement for **Android APIs and notifications**.

---

# Phase 11 — Cloud Deployment

Deploy Docker Compose to AWS EC2.

Verify:

```text
React
   ↓
API Gateway
   ↓
Spring Boot Microservices
   ↓
PostgreSQL
```

Then connect Android to the cloud API.

---

# 30. Final Demonstration Scenario

The best final demonstration is a simulated service failure.

### Initial state

```text
User Service       🟢 UP
Order Service      🟢 UP
Payment Service    🟢 UP
```

### Stop Payment Service

```bash
docker stop payment-service
```

Monitoring detects:

```text
Payment Service
      ↓
Health check failed
      ↓
Status = DOWN
      ↓
Metric stored
      ↓
Event created
      ↓
Notification generated
      ↓
WebSocket update
```

### React

Immediately displays:

```text
🔴 Payment Service DOWN
```

### Android

Receives:

```text
┌─────────────────────────────┐
│ MicroWatch Alert            │
│                             │
│ 🔴 Payment Service DOWN     │
│ Health check failed         │
└─────────────────────────────┘
```

### Restart service

```bash
docker start payment-service
```

The system detects:

```text
Payment Service
      ↓
Health check successful
      ↓
Status = UP
      ↓
Recovery event
      ↓
Notification
```

Android displays:

```text
🟢 MicroWatch Recovery

Payment Service is UP again.
Response time: 47 ms
```

---

# 31. Academic Mapping

## Cloud Computing

Demonstrates:

* Cloud deployment
* Microservices
* REST architecture
* Docker
* Docker Compose
* Container networking
* Service monitoring
* Database persistence
* AWS EC2
* Distributed architecture

## WAD

Demonstrates:

* React
* Components
* Routing
* Forms
* Form validation
* CRUD
* REST API
* WebSocket
* Responsive UI
* Dynamic dashboard

## MAD

Demonstrates:

* Android application
* Java
* REST API
* Retrofit
* Notifications
* Notification channels
* WorkManager
* ConnectivityManager
* Intents
* Runtime permissions
* Mobile navigation

---

# 32. Minimum Viable Version

If development time becomes limited, the minimum version should contain:

```text
Backend
├── API Gateway
├── Monitoring Service
├── Registry Service
├── 3 Demo Services
└── PostgreSQL

Web
├── Dashboard
├── Service List
├── Add Service Form
└── Service Details

Android
├── Dashboard
├── Service List
├── Service Details
└── Notifications

Cloud
└── Docker Compose + AWS EC2
```

Do not add additional technologies until this version works.

---

# 33. Future Enhancements

These can be mentioned in the report without implementing them:

* Prometheus integration
* Grafana dashboards
* Kubernetes deployment
* Kafka event streaming
* Redis caching
* Email alerts
* SMS alerts
* Role-based authentication
* JWT authentication
* AI-based anomaly detection
* Automatic service scaling
* Load balancing
* Distributed tracing
* AWS RDS
* AWS ECS/EKS
* CI/CD using GitHub Actions

---

# 34. Final Technology Stack

| Layer                   | Technology                 |
| ----------------------- | -------------------------- |
| Backend Language        | Java 21                    |
| Backend Framework       | Spring Boot                |
| API                     | REST                       |
| Real-time Communication | WebSocket                  |
| Database                | PostgreSQL                 |
| ORM                     | Spring Data JPA            |
| Build Tool              | Maven                      |
| Containerization        | Docker                     |
| Container Orchestration | Docker Compose             |
| Cloud                   | AWS EC2                    |
| Web Frontend            | React                      |
| Web Build Tool          | Vite                       |
| Web Styling             | CSS / Bootstrap / Tailwind |
| Mobile                  | Android                    |
| Mobile Language         | Java                       |
| Mobile Networking       | Retrofit                   |
| Mobile Notifications    | Android Notification APIs  |
| Background Tasks        | WorkManager                |
| Connectivity            | ConnectivityManager        |
| Version Control         | Git/GitHub                 |

---

# 35. Project Titles for Submission

### Cloud Computing

**MicroWatch: A Dockerized Cloud-Based Microservice Monitoring Platform**

### WAD

**MicroWatch: React-Based Web Dashboard for Microservice Monitoring**

### MAD

**MicroWatch: Android-Based Microservice Monitoring and Notification Application**

---

# 36. Final Architecture Summary

```text
                         ┌───────────────────┐
                         │    React Web      │
                         │                   │
                         │ Forms + Dashboard │
                         │ Charts + WebSocket│
                         └─────────┬─────────┘
                                   │
                                   │
                         ┌─────────▼─────────┐
                         │    API Gateway    │
                         │    Spring Boot    │
                         └─────────┬─────────┘
                                   │
              ┌────────────────────┼────────────────────┐
              │                    │                    │
              ▼                    ▼                    ▼
       ┌─────────────┐      ┌─────────────┐      ┌─────────────┐
       │ Monitoring  │      │  Registry   │      │Notification │
       │   Service   │      │   Service   │      │   Service   │
       └──────┬──────┘      └─────────────┘      └──────┬──────┘
              │                                         │
              │                                         │
              └────────────────┬────────────────────────┘
                               │
                               ▼
                       ┌───────────────┐
                       │  PostgreSQL   │
                       └───────────────┘

                               │
                         Health Checks
                               │
             ┌─────────────────┼─────────────────┐
             ▼                 ▼                 ▼
       User Service      Order Service      Payment Service


                         ┌───────────────────┐
                         │   Android App     │
                         │                   │
                         │ REST + Retrofit   │
                         │ Notifications     │
                         │ WorkManager       │
                         │ Android APIs      │
                         └───────────────────┘
```

**The key design principle is to build one backend platform and reuse it for all three subjects.** The Cloud Computing submission emphasizes the distributed Docker/AWS infrastructure, the WAD submission emphasizes React forms and the monitoring dashboard, and the MAD submission emphasizes the Android client, REST integration, background APIs, and notification system.
