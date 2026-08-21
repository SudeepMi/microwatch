package com.microwatch.notification.dto;

public class EventDto {
    private String type;
    private Long serviceId;
    private String service;
    private String status;
    private Long responseTime;
    private String timestamp;

    public EventDto() {}

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
