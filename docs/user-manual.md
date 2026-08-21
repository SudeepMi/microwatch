# MicroWatch User Manual

## 1. Web Dashboard Operating Guide

### Dashboard Overview (`/dashboard`)
- High-level metric cards: Total Services, Healthy Services, Down Services, Average Response Time, Overall Uptime.
- Monitored Services grid with real-time status indicators (`🟢 UP`, `🔴 DOWN`, `🟡 UNKNOWN`).
- Recharts historical response time graph.
- Simulation buttons to trigger demo failure (`POST /admin/failure`) and recovery (`POST /admin/recover`).

### Registering a New Service (`/services/new`)
1. Click **Register New Service**.
2. Enter Service Name (e.g. `inventory-service`).
3. Enter Service URL (e.g. `http://inventory-service:9005`).
4. Enter Health Endpoint (must start with `/`, e.g. `/health`).
5. Enter Optional Description.
6. Click **Register Service**.

### Editing / Deleting Services (`/services`)
- Click the pencil icon to edit service properties.
- Click the trash icon to remove a service with confirmation.

---

## 2. Android App Operating Guide

### Connection Configuration
- Set server URL in SharedPreferences (default: `http://10.0.2.2:8080/`).

### Failure Alerts & Background Monitoring
- Automated background sync worker (`StatusSyncWorker`) checks service statuses every 15 minutes.
- When a service status changes to `DOWN` or recovers to `UP`, a local notification is delivered.
- Tapping a notification opens `ServiceDetailsActivity` for that specific microservice.
- If internet connection is lost, an offline banner (`Offline — Check your internet connection`) is displayed.
