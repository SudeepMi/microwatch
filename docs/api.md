# MicroWatch API Specification

All external APIs are exposed through the API Gateway on port `8080` with the `/api/v1` base path.

## 1. Services Registry APIs

### Get All Services
- **Endpoint**: `GET /api/v1/services`
- **Response**: `200 OK` (Array of Service objects)

### Register Service
- **Endpoint**: `POST /api/v1/services`
- **Request Body**:
```json
{
  "name": "payment-service",
  "url": "http://payment-service:9003",
  "healthEndpoint": "/health",
  "description": "Payment microservice"
}
```

### Update Service
- **Endpoint**: `PUT /api/v1/services/{id}`

### Delete Service
- **Endpoint**: `DELETE /api/v1/services/{id}`

---

## 2. Dashboard & Monitoring APIs

### Get Dashboard Summary
- **Endpoint**: `GET /api/v1/dashboard/summary`
- **Response**:
```json
{
  "totalServices": 3,
  "healthyServices": 3,
  "downServices": 0,
  "averageResponseTime": 45.2,
  "overallUptime": 100.0
}
```

### Get Latest Metrics
- **Endpoint**: `GET /api/v1/metrics/latest`

### Get Service Historical Metrics
- **Endpoint**: `GET /api/v1/services/{id}/metrics?period=24h`

---

## 3. Notification APIs

### Get Notifications
- **Endpoint**: `GET /api/v1/notifications`

### Mark Notification Read
- **Endpoint**: `PUT /api/v1/notifications/{id}/read`

---

## 4. WebSocket Real-Time API
- **Endpoint**: `ws://localhost:8080/ws/monitoring`
- **Event Message Payload**:
```json
{
  "type": "STATUS_CHANGE",
  "serviceId": 3,
  "service": "payment-service",
  "status": "DOWN",
  "responseTime": null,
  "timestamp": "2026-08-20T23:55:00Z"
}
```
