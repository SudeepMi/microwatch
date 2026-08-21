# MASTER DEVELOPMENT PROMPT — MICROWATCH

You are a senior software architect and full-stack developer.

Build a complete academic project called:

# MicroWatch — Cloud-Based Microservice Monitoring Platform

The system is a lightweight microservice monitoring platform that monitors the health, availability, response time, uptime, and status changes of distributed microservices.

This project will be submitted as THREE connected academic projects:

1. Cloud Computing
2. Web Application Development (WAD)
3. Mobile Application Development (MAD)

The implementation must be clean, modular, Dockerized, runnable locally, and deployable to AWS.

Do NOT over-engineer the system. This is an academic tiny project, so prioritize a working, understandable, demonstrable architecture over enterprise-level complexity.

---

# 1. TECHNOLOGY STACK

## Backend

Use:

- Java 21
- Spring Boot 3.x
- Maven
- Spring Web
- Spring Data JPA
- Spring WebSocket
- PostgreSQL
- REST APIs
- Scheduled tasks
- Docker

Do NOT use Python or Node.js for the backend.

The backend must consist of multiple Spring Boot microservices.

---

## Web Application

Use:

- React
- Vite
- JavaScript
- React Router
- Axios
- HTML5
- CSS
- Chart library such as Recharts

The React application MUST use forms.

Do not create a static dashboard only.

The web application must include:

- Form-based service registration
- Form-based service editing
- Form validation
- CRUD operations
- REST API integration
- WebSocket/live status updates

---

## Android Application

Use:

- Android Studio
- Java
- XML layouts
- Retrofit
- Android Notification APIs
- NotificationManager
- Notification Channels
- WorkManager
- ConnectivityManager
- Intent APIs
- SharedPreferences

The Android application MUST be implemented in Java.

Do not use Flutter, React Native, or Kotlin.

The Android application must consume the same backend REST APIs used by the React application.

---

## Infrastructure

Use:

- Docker
- Docker Compose
- PostgreSQL
- AWS EC2 for optional cloud deployment

The complete backend should be runnable using:

docker compose up --build

---

# 2. CORE PROJECT OBJECTIVE

Build a centralized platform that allows users to:

1. Register microservices.
2. View all registered services.
3. Monitor service health automatically.
4. Detect UP/DOWN status.
5. Measure response time.
6. Store historical monitoring metrics.
7. Record service status changes.
8. Display service health on a React dashboard.
9. Receive real-time dashboard updates through WebSockets.
10. Receive Android notifications when a service goes DOWN.
11. Receive Android notifications when a service recovers.
12. View historical metrics.
13. Manage services through React forms.
14. Run the complete system using Docker.
15. Deploy the system to AWS EC2.

---

# 3. PROBLEMS THE SYSTEM SOLVES

The system should explicitly address:

1. Manual monitoring of multiple microservices.
2. Delayed detection of service failures.
3. Lack of centralized service visibility.
4. Lack of real-time monitoring.
5. Lack of mobile failure notifications.
6. Difficulty tracking service response time.
7. Lack of historical service-health data.
8. Difficulty managing multiple monitored services.
9. Difficulty monitoring containerized services.
10. Lack of a simple monitoring platform suitable for small development/cloud environments.

---

# 4. SYSTEM ARCHITECTURE

Implement the following architecture:

                    +----------------------+
                    |     React Web App    |
                    |                      |
                    | Dashboard            |
                    | Forms                |
                    | Charts               |
                    | WebSocket             |
                    +----------+-----------+
                               |
                               | REST/WebSocket
                               |
                    +----------v-----------+
                    |     API Gateway      |
                    |     Spring Boot      |
                    |       :8080          |
                    +----------+-----------+
                               |
              +----------------+----------------+
              |                |                |
              v                v                v
      +---------------+ +---------------+ +---------------+
      | Monitoring    | | Service       | | Notification  |
      | Service       | | Registry      | | Service       |
      | :8081         | | :8082         | | :8084         |
      +-------+-------+ +-------+-------+ +-------+-------+
              |                 |                 |
              +-----------------+-----------------+
                                |
                                v
                       +----------------+
                       |  PostgreSQL    |
                       |     :5432      |
                       +----------------+

                                |
                         Health Checks
                                |
              +-----------------+-----------------+
              |                 |                 |
              v                 v                 v
       +-------------+   +-------------+   +-------------+
       | User        |   | Order       |   | Payment     |
       | Service     |   | Service     |   | Service     |
       | :9001       |   | :9002       |   | :9003       |
       +-------------+   +-------------+   +-------------+

Android App
     |
     | REST API
     |
     v
API Gateway :8080

---

# 5. MICROSERVICES

Create these Spring Boot services:

## 5.1 API Gateway

Port:

8080

Responsibilities:

- Single public API entry point.
- Route requests to internal services.
- Handle CORS.
- Provide WebSocket endpoint.
- Hide internal service URLs.

---

## 5.2 Monitoring Service

Port:

8081

Responsibilities:

- Periodically check registered services.
- Call their /health endpoint.
- Measure response time.
- Determine UP/DOWN status.
- Store metrics.
- Detect status changes.
- Notify Notification Service.
- Publish WebSocket events.

Monitoring interval:

10 seconds.

Make the interval configurable using application properties.

---

## 5.3 Registry Service

Port:

8082

Responsibilities:

- Register services.
- Update services.
- Delete services.
- List services.
- Retrieve service details.

---

## 5.4 Notification Service

Port:

8084

Responsibilities:

- Create notification records.
- Handle DOWN events.
- Handle RECOVERY events.
- Provide notification REST APIs.

Important:

The Android app should retrieve notification events through the backend API.

For the academic version, do NOT introduce Firebase unless it is genuinely necessary.

Use the Android NotificationManager and periodic WorkManager synchronization to generate local Android notifications based on backend status changes.

---

# 6. DEMO MICROSERVICES

Create three simple Spring Boot services.

## User Service

Port:

9001

## Order Service

Port:

9002

## Payment Service

Port:

9003

Each service must implement:

GET /health

GET /info

POST /admin/failure

POST /admin/recover

Example health response:

{
  "service": "payment-service",
  "status": "UP",
  "version": "1.0.0",
  "timestamp": "..."
}

The /admin/failure endpoint should simulate failure without actually killing the container.

After failure:

GET /health

should return:

{
  "service": "payment-service",
  "status": "DOWN"
}

The /admin/recover endpoint should restore the service.

This is required for the final demonstration.

---

# 7. DATABASE

Use PostgreSQL.

Database:

microwatch

User:

microwatch

Password:

microwatch

Create these tables.

---

## services

Fields:

- id
- name
- url
- health_endpoint
- description
- created_at

SQL:

CREATE TABLE services (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    url VARCHAR(255) NOT NULL,
    health_endpoint VARCHAR(100) DEFAULT '/health',
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

---

## metrics

Fields:

- id
- service_id
- status
- response_time
- checked_at

SQL:

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

---

## service_events

Fields:

- id
- service_id
- previous_status
- current_status
- message
- created_at

---

## notifications

Fields:

- id
- service_id
- title
- message
- notification_type
- is_read
- created_at

---

# 8. DATABASE RELATIONSHIPS

Implement:

Service 1 ---- N Metrics

Service 1 ---- N ServiceEvents

Service 1 ---- N Notifications

Use JPA relationships appropriately.

Avoid unnecessary bidirectional relationships if they create serialization problems.

Use DTOs for REST responses.

---

# 9. REST API

All external APIs must use:

/api/v1

---

## Services

GET /api/v1/services

GET /api/v1/services/{id}

POST /api/v1/services

PUT /api/v1/services/{id}

DELETE /api/v1/services/{id}

---

## Dashboard

GET /api/v1/dashboard/summary

Example:

{
  "totalServices": 5,
  "healthyServices": 4,
  "downServices": 1,
  "averageResponseTime": 52,
  "overallUptime": 98.7
}

---

## Metrics

GET /api/v1/metrics/latest

GET /api/v1/services/{id}/metrics

GET /api/v1/services/{id}/metrics?period=24h

---

## Events

GET /api/v1/services/{id}/events

---

## Notifications

GET /api/v1/notifications

PUT /api/v1/notifications/{id}/read

---

# 10. MONITORING ALGORITHM

Implement this exact logic:

Every 10 seconds:

1. Retrieve all registered services.
2. Send HTTP GET request to each service's health endpoint.
3. Start response-time timer.
4. Receive response.
5. Calculate response time.
6. Determine status.
7. Save metric.
8. Compare current status with previous status.
9. If status changed:
   - Create service event.
   - Create notification.
   - Publish WebSocket event.
10. Continue monitoring.

Status transitions:

UP → DOWN

DOWN → UP

Do NOT generate repeated notifications every 10 seconds if a service remains DOWN.

Only generate a notification when the status actually changes.

---

# 11. WEB SOCKET

Create:

/ws/monitoring

When status changes, broadcast:

{
  "type": "STATUS_CHANGE",
  "serviceId": 3,
  "service": "payment-service",
  "status": "DOWN",
  "responseTime": null,
  "timestamp": "..."
}

Recovery:

{
  "type": "STATUS_CHANGE",
  "serviceId": 3,
  "service": "payment-service",
  "status": "UP",
  "responseTime": 45,
  "timestamp": "..."
}

---

# 12. REACT WEB APPLICATION

The React application must NOT be a static dashboard.

It must contain working forms and CRUD operations.

Use:

- React
- Vite
- React Router
- Axios
- Recharts
- CSS

---

# 13. REACT PAGES

Create:

/dashboard

/services

/services/new

/services/:id

/services/:id/edit

/notifications

---

# 14. REACT DASHBOARD

Display:

- Total Services
- Healthy Services
- Down Services
- Average Response Time
- Overall Uptime

Also display:

- Service status cards.
- Response-time chart.
- Recent events.
- Recent notifications.

Example:

Total Services: 5

Healthy: 4

Down: 1

Average Response Time: 52 ms

Overall Uptime: 98.7%

---

# 15. REACT FORMS

This is mandatory.

Create:

ServiceForm.jsx

Fields:

- Service Name
- Service URL
- Health Endpoint
- Description

Example:

Service Name
[________________]

Service URL
[________________]

Health Endpoint
[________________]

Description
[________________]

[ Register Service ]

---

# 16. REACT FORM VALIDATION

Validate:

- Service name is required.
- URL is required.
- URL must be valid.
- Health endpoint is required.
- Health endpoint must begin with /.
- Description is optional.

Show validation messages below the appropriate field.

---

# 17. REACT CRUD

Implement:

CREATE

Register new service.

READ

Display services.

UPDATE

Edit service using the same form.

DELETE

Delete service with confirmation.

---

# 18. REACT SERVICE DETAILS

Display:

- Service name
- URL
- Current status
- Uptime
- Average response time
- Latest response time
- Historical response-time graph
- Status history
- Recent events

---

# 19. REACT REAL-TIME UPDATES

Connect to:

/ws/monitoring

When an event is received:

1. Update service status immediately.
2. Update dashboard counters.
3. Update service card.
4. Add event to recent events.
5. Show a visual notification.

Do not require page refresh.

---

# 20. ANDROID APPLICATION

Build the Android application in:

Java

Use XML layouts.

Do NOT use Kotlin.

---

# 21. ANDROID ARCHITECTURE

Use a simple MVVM-style architecture:

UI
↓
ViewModel
↓
Repository
↓
Retrofit API
↓
Spring Boot API Gateway

Create:

data/
api/
model/
repository/
ui/
notification/
utils/

---

# 22. ANDROID SCREENS

Create:

1. Dashboard
2. Services
3. Service Details
4. Notifications

---

# 23. ANDROID DASHBOARD

Display:

- Total services
- Healthy services
- Down services
- Average response time
- Recent alerts

Use cards and a clean mobile UI.

---

# 24. ANDROID SERVICES SCREEN

Display:

User Service
🟢 UP

Order Service
🟢 UP

Payment Service
🔴 DOWN

Each item should be clickable.

Clicking a service opens Service Details.

---

# 25. ANDROID SERVICE DETAILS

Display:

- Service name
- Current status
- Response time
- Uptime
- Last checked
- Recent events

---

# 26. ANDROID REST INTEGRATION

Use Retrofit.

Create an ApiService.java interface.

Example:

@GET("api/v1/services")
Call<List<Service>> getServices();

@GET("api/v1/dashboard/summary")
Call<DashboardSummary> getDashboardSummary();

@GET("api/v1/services/{id}/metrics")
Call<List<Metric>> getMetrics(@Path("id") Long id);

@GET("api/v1/notifications")
Call<List<Notification>> getNotifications();

---

# 27. ANDROID NOTIFICATION REQUIREMENT

This is mandatory.

Use Android:

- NotificationManager
- NotificationChannel
- PendingIntent
- BroadcastReceiver where appropriate
- Runtime notification permission

Create notification channel:

ID:
microwatch_alerts

Name:
MicroWatch Alerts

---

# 28. ANDROID NOTIFICATION LOGIC

Use WorkManager for periodic synchronization.

Workflow:

Every configured interval:

1. Fetch notifications/status from backend.
2. Compare with locally stored previous status.
3. Detect new DOWN event.
4. Detect RECOVERY event.
5. Generate local Android notification.
6. Store latest status locally.
7. Avoid duplicate notifications.

Example DOWN notification:

Title:

MicroWatch Alert

Message:

Payment Service is DOWN

Example recovery notification:

Title:

MicroWatch Recovery

Message:

Payment Service is UP again.

---

# 29. OTHER ANDROID APIs

Use Android APIs meaningfully.

Implement:

## NotificationManager

For alerts.

## WorkManager

For periodic background synchronization.

## ConnectivityManager

For checking network availability.

Display:

"Offline — Check your internet connection"

when necessary.

## SharedPreferences

Store:

- Backend server URL
- Notification preference
- Last known service status

## Intent

Open Service Details when a notification is tapped.

---

# 30. ANDROID NOTIFICATION CLICK

When the user taps:

"Payment Service is DOWN"

open:

ServiceDetailsActivity

for Payment Service.

Pass the service ID using Intent extras.

---

# 31. DOCKER

Every Spring Boot service must have a Dockerfile.

Use a Java 21 runtime image.

Example:

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

---

# 32. REACT DOCKERFILE

Use a multi-stage build:

Stage 1:

Node.js

Build React application.

Stage 2:

Nginx

Serve the compiled application.

---

# 33. DOCKER COMPOSE

Create:

docker-compose.yml

Services:

postgres

api-gateway

monitoring-service

registry-service

notification-service

user-service

order-service

payment-service

dashboard

Create:

microwatch

Docker network.

Create:

postgres_data

Docker volume.

---

# 34. INTERNAL SERVICE COMMUNICATION

Inside Docker, services must communicate using Docker service names.

Correct:

http://monitoring-service:8081

Incorrect:

http://localhost:8081

Use environment variables for service URLs.

Example:

REGISTRY_URL=http://registry-service:8082

MONITORING_URL=http://monitoring-service:8081

NOTIFICATION_URL=http://notification-service:8084

---

# 35. ENVIRONMENT CONFIGURATION

Create:

.env.example

Include:

POSTGRES_DB=microwatch
POSTGRES_USER=microwatch
POSTGRES_PASSWORD=microwatch

REGISTRY_URL=http://registry-service:8082

MONITORING_URL=http://monitoring-service:8081

NOTIFICATION_URL=http://notification-service:8084

Do not commit real secrets.

---

# 36. HEALTH CHECKS

All Spring Boot services should expose a simple:

GET /health

The monitoring service uses these endpoints.

Do not make the monitoring service dependent on Spring Boot Actuator for the core functionality.

Actuator can optionally be included for additional technical monitoring.

---

# 37. ERROR HANDLING

Implement proper REST error responses.

Example:

{
  "timestamp": "...",
  "status": 404,
  "error": "SERVICE_NOT_FOUND",
  "message": "Service with ID 10 was not found"
}

Handle:

- 400 Bad Request
- 404 Not Found
- 500 Internal Server Error
- Service connection failures
- Database failures

Use Spring's global exception handling.

---

# 38. CORS

Configure CORS properly for the React development server.

Development:

http://localhost:5173

Production:

Allow configuration through environment variables.

Do not use:

allow all origins

in a production configuration.

---

# 39. PROJECT SECURITY

This is an academic project.

Do NOT initially implement complex authentication.

However:

- Do not hardcode production passwords.
- Use environment variables.
- Do not expose PostgreSQL unnecessarily.
- Do not expose internal microservice ports in production.
- Validate all input forms.
- Validate URLs received from users.

Authentication can be listed as future enhancement.

---

# 40. PROJECT UI DESIGN

Use a clean professional monitoring-dashboard design.

Recommended visual structure:

Sidebar
+
Top navigation
+
Dashboard cards
+
Service status cards
+
Charts
+
Tables
+
Forms

Status indicators:

🟢 UP

🔴 DOWN

🟡 UNKNOWN

Use a responsive design.

The UI must work on:

- Desktop
- Tablet
- Mobile browser

---

# 41. IMPORTANT DEMO FEATURE

Implement a service failure simulation.

Start the system:

User Service       🟢 UP
Order Service      🟢 UP
Payment Service    🟢 UP

Then call:

POST /admin/failure

on Payment Service.

The system should detect:

Payment Service
DOWN

Then:

1. PostgreSQL metric is created.
2. Service event is created.
3. Notification is created.
4. React receives WebSocket event.
5. React changes Payment Service to DOWN.
6. Android detects the new notification.
7. Android displays a notification.

Then call:

POST /admin/recover

Payment Service becomes UP.

The same workflow happens in reverse.

This must work reliably.

---

# 42. DEVELOPMENT PHASES

Do not generate the entire application blindly in one step.

Develop in phases.

## Phase 1

Project setup.

Create:

- Repository
- Backend structure
- React structure
- Android structure
- Docker structure

---

## Phase 2

Database.

Implement:

- PostgreSQL
- Tables
- JPA entities
- Repositories

---

## Phase 3

Demo microservices.

Implement:

- User Service
- Order Service
- Payment Service

---

## Phase 4

Registry Service.

Implement CRUD APIs.

---

## Phase 5

Monitoring Service.

Implement:

- Scheduler
- Health checks
- Response time
- Metrics
- Status detection

---

## Phase 6

Notification Service.

Implement:

- Event handling
- Notification storage
- Notification APIs

---

## Phase 7

API Gateway.

Connect all backend services.

---

## Phase 8

Docker Compose.

Make the entire backend runnable with:

docker compose up --build

---

## Phase 9

React.

Implement:

- Routing
- Dashboard
- Forms
- CRUD
- Charts
- WebSocket

---

## Phase 10

Android.

Implement:

- Java activities/fragments
- Retrofit
- Dashboard
- Services
- Details
- Notifications
- WorkManager
- ConnectivityManager

---

## Phase 11

Integration.

Test:

React → Gateway → Backend

Android → Gateway → Backend

Backend → PostgreSQL

Monitoring → Demo Services

---

## Phase 12

Cloud.

Deploy Docker Compose to AWS EC2.

---

# 43. TESTING REQUIREMENTS

Create tests for:

## Backend

- Service registration
- Service update
- Service deletion
- Health check
- Failure detection
- Recovery detection
- Metrics storage
- Notification creation

## React

- Form validation
- Service creation
- Service editing
- Service deletion
- Dashboard rendering
- WebSocket updates

## Android

- API communication
- Service list
- Service details
- Notification generation
- Network unavailable state

---

# 44. README REQUIREMENTS

Generate a comprehensive README.md containing:

1. Project title
2. Problem statement
3. Objectives
4. Features
5. Architecture
6. Technology stack
7. Folder structure
8. Database schema
9. REST APIs
10. WebSocket API
11. Docker setup
12. Local installation
13. React setup
14. Android setup
15. AWS deployment
16. Testing
17. Demo scenario
18. Future enhancements

---

# 45. DOCUMENTATION

Generate:

docs/
├── architecture.md
├── api.md
├── database.md
├── deployment.md
└── user-manual.md

The documentation should explain the project in a way suitable for a Master's-level academic project.

---

# 46. CODE QUALITY RULES

Follow these rules:

1. Use meaningful class and variable names.
2. Use DTOs instead of exposing JPA entities directly.
3. Use service/repository/controller separation.
4. Keep controllers thin.
5. Put business logic in services.
6. Use environment variables for configuration.
7. Add comments only where they improve understanding.
8. Avoid unnecessary abstraction.
9. Avoid duplicate code.
10. Keep APIs RESTful.
11. Handle errors properly.
12. Validate incoming requests.
13. Use proper HTTP status codes.
14. Keep each microservice independently buildable.
15. Ensure Docker builds work.
16. Ensure the complete application works together.

---

# 47. IMPORTANT DEVELOPMENT RULE

Do not create fake/mock APIs once the backend is implemented.

The React and Android applications must communicate with the actual Spring Boot backend.

Do not use hardcoded service lists in React.

Do not use hardcoded monitoring status in Android.

All service status information must originate from the backend.

---

# 48. FINAL EXPECTED PROJECT

The completed repository should look approximately like:

microwatch/

├── backend/
│   ├── api-gateway/
│   ├── monitoring-service/
│   ├── registry-service/
│   ├── notification-service/
│   ├── demo-user-service/
│   ├── demo-order-service/
│   └── demo-payment-service/
│
├── web/
│   └── dashboard/
│
├── android/
│   └── MicroWatch/
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
├── docker-compose.yml
├── .env.example
└── README.md

---

# 49. ACCEPTANCE CRITERIA

The project is considered complete only when all of the following work:

[ ] Backend builds successfully.

[ ] All Spring Boot services start successfully.

[ ] PostgreSQL starts successfully.

[ ] Docker Compose starts the complete system.

[ ] User Service health endpoint works.

[ ] Order Service health endpoint works.

[ ] Payment Service health endpoint works.

[ ] Services can be registered through React.

[ ] Services can be edited through React forms.

[ ] Services can be deleted through React.

[ ] Monitoring automatically checks services.

[ ] UP/DOWN status is detected.

[ ] Response time is stored.

[ ] Historical metrics are available.

[ ] Service events are stored.

[ ] Notifications are created.

[ ] React dashboard displays real data.

[ ] React forms perform actual CRUD operations.

[ ] React receives WebSocket updates.

[ ] Android retrieves actual backend data.

[ ] Android displays service status.

[ ] Android notifications work.

[ ] WorkManager synchronization works.

[ ] ConnectivityManager is implemented.

[ ] Notification click opens service details.

[ ] Failure simulation works.

[ ] Recovery simulation works.

[ ] Docker Compose works from a clean machine.

[ ] AWS deployment documentation is provided.

---

# 50. DEVELOPMENT INSTRUCTION TO THE AI

Build this project incrementally.

Do NOT skip directly to a giant generated codebase.

At each phase:

1. Create the required files.
2. Implement the functionality.
3. Verify imports and dependencies.
4. Verify API consistency.
5. Verify database relationships.
6. Verify Docker configuration.
7. Fix compilation errors.
8. Continue to the next phase only after the current phase is coherent.

Whenever you create an API, immediately ensure that:

React and Android can consume the same API.

Maintain a single source of truth for:

- API paths
- DTO structures
- Service status values
- Database relationships
- Environment variables

Use these status values consistently:

UP
DOWN
UNKNOWN

Use these notification types:

SERVICE_DOWN
SERVICE_RECOVERED
SYSTEM_ALERT

---

# FINAL GOAL

The final result must be a fully integrated project:

                    React Web
                        |
                        |
                    REST/WebSocket
                        |
                        v
                 Spring Boot Gateway
                        |
             +----------+----------+
             |          |          |
             v          v          v
        Monitoring   Registry   Notification
             |
             v
         PostgreSQL
             |
             v
      Monitored Services

                    Android
                        |
                        |
                      REST
                        |
                        v
                 Spring Boot Gateway

The application should be simple enough for an academic tiny project but technically strong enough to demonstrate:

- Cloud Computing
- Microservices
- REST APIs
- Docker
- AWS
- React
- Forms
- WebSockets
- Android
- Java
- Android Notification APIs
- Background processing
- PostgreSQL
- Distributed service monitoring

Prioritize correctness, integration, and demonstrability over unnecessary complexity.