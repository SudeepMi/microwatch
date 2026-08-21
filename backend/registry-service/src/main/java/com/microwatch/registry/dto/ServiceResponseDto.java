package com.microwatch.registry.dto;

import java.time.LocalDateTime;

public class ServiceResponseDto {

    private Long id;
    private String name;
    private String url;
    private String healthEndpoint;
    private String description;
    private LocalDateTime createdAt;

    public ServiceResponseDto() {}

    public ServiceResponseDto(Long id, String name, String url, String healthEndpoint, String description, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.healthEndpoint = healthEndpoint;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getHealthEndpoint() { return healthEndpoint; }
    public void setHealthEndpoint(String healthEndpoint) { this.healthEndpoint = healthEndpoint; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
