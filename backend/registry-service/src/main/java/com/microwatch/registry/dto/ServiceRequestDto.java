package com.microwatch.registry.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ServiceRequestDto {

    @NotBlank(message = "Service name is required")
    private String name;

    @NotBlank(message = "Service URL is required")
    private String url;

    @NotBlank(message = "Health endpoint is required")
    @Pattern(regexp = "^/.*", message = "Health endpoint must begin with '/'")
    private String healthEndpoint;

    private String description;

    public ServiceRequestDto() {}

    public ServiceRequestDto(String name, String url, String healthEndpoint, String description) {
        this.name = name;
        this.url = url;
        this.healthEndpoint = healthEndpoint;
        this.description = description;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getHealthEndpoint() { return healthEndpoint; }
    public void setHealthEndpoint(String healthEndpoint) { this.healthEndpoint = healthEndpoint; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
