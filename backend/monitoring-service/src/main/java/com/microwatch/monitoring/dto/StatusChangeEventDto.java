package com.microwatch.monitoring.dto;

import java.time.Instant;

public class StatusChangeEventDto {
    private String type = "STATUS_CHANGE";
    private Long serviceId;
    private String service;
    private String status;
    private Long responseTime;
    private String timestamp;

    public StatusChangeEventDto() {
        this.timestamp = Instant.now().toString();
    }

    public StatusChangeEventDto(Long serviceId, String service, String status, Long responseTime) {
        this.type = "STATUS_CHANGE";
        this.serviceId = serviceId;
        this.service = service;
        this.status = status;
        this.responseTime = responseTime;
        this.timestamp = Instant.now().toString();
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }

    public String getService() { return service; }
    public void setService(String service) { this.service = service; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getResponseTime() { return responseTime; }
    public void setResponseTime(Long responseTime) { this.responseTime = responseTime; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
