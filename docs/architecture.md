# MicroWatch Architecture Specification

## 1. Overview
MicroWatch uses a decoupled, event-driven microservice architecture containerized with Docker and orchestrated via Docker Compose.

## 2. Microservice Decomposition
- **API Gateway (Port 8080)**: Reverse proxy routing external clients to internal microservices, managing CORS headers, and hosting the WebSocket server at `/ws/monitoring`.
- **Registry Service (Port 8082)**: Manages target microservice configurations and exposes CRUD APIs (`/api/v1/services`).
- **Monitoring Service (Port 8081)**: Periodically polls target services every 10 seconds via GET `/health`, computes response time, logs metrics to PostgreSQL, and detects state changes.
- **Notification Service (Port 8084)**: Receives status transition events, records notifications in PostgreSQL, and serves `/api/v1/notifications` to Web & Android clients.
- **Demo Microservices (Ports 9001, 9002, 9003)**: Microservices (`User`, `Order`, `Payment`) exposing `/health`, `/info`, `/admin/failure`, `/admin/recover`.

## 3. Communication Patterns
- **REST APIs**: Synchronous HTTP/JSON requests from React Web & Android mobile app to API Gateway.
- **WebSockets**: Real-time push broadcasts from Gateway (`/ws/monitoring`) to connected React web clients upon status changes.
- **Internal Microservice HTTP**: Internal inter-service HTTP calls via Docker service DNS names (`http://registry-service:8082`, `http://monitoring-service:8081`, `http://notification-service:8084`).
