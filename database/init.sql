-- Database initialization script for MicroWatch

-- Create Services Table
CREATE TABLE IF NOT EXISTS services (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    url VARCHAR(255) NOT NULL,
    health_endpoint VARCHAR(100) DEFAULT '/health',
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Metrics Table
CREATE TABLE IF NOT EXISTS metrics (
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

-- Create Service Events Table
CREATE TABLE IF NOT EXISTS service_events (
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

-- Create Notifications Table
CREATE TABLE IF NOT EXISTS notifications (
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

-- Seed initial demo services
INSERT INTO services (name, url, health_endpoint, description)
VALUES 
    ('user-service', 'http://user-service:9001', '/health', 'Demo User Microservice'),
    ('order-service', 'http://order-service:9002', '/health', 'Demo Order Microservice'),
    ('payment-service', 'http://payment-service:9003', '/health', 'Demo Payment Microservice')
ON CONFLICT (name) DO NOTHING;
