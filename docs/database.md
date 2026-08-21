# MicroWatch Database Schema & JPA Entity Documentation

PostgreSQL database name: `microwatch`

## Database Schema Tables

### 1. `services`
- `id`: BIGSERIAL PRIMARY KEY
- `name`: VARCHAR(100) UNIQUE NOT NULL
- `url`: VARCHAR(255) NOT NULL
- `health_endpoint`: VARCHAR(100) DEFAULT '/health'
- `description`: TEXT
- `created_at`: TIMESTAMP DEFAULT CURRENT_TIMESTAMP

### 2. `metrics`
- `id`: BIGSERIAL PRIMARY KEY
- `service_id`: BIGINT NOT NULL (FK -> services.id ON DELETE CASCADE)
- `status`: VARCHAR(20) NOT NULL
- `response_time`: BIGINT
- `checked_at`: TIMESTAMP DEFAULT CURRENT_TIMESTAMP

### 3. `service_events`
- `id`: BIGSERIAL PRIMARY KEY
- `service_id`: BIGINT NOT NULL (FK -> services.id ON DELETE CASCADE)
- `previous_status`: VARCHAR(20)
- `current_status`: VARCHAR(20)
- `message`: TEXT
- `created_at`: TIMESTAMP DEFAULT CURRENT_TIMESTAMP

### 4. `notifications`
- `id`: BIGSERIAL PRIMARY KEY
- `service_id`: BIGINT NOT NULL (FK -> services.id ON DELETE CASCADE)
- `title`: VARCHAR(255)
- `message`: TEXT
- `notification_type`: VARCHAR(50)
- `is_read`: BOOLEAN DEFAULT FALSE
- `created_at`: TIMESTAMP DEFAULT CURRENT_TIMESTAMP
