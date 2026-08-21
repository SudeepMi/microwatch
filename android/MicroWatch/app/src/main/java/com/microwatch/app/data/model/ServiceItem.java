package com.microwatch.app.data.model;

import com.google.gson.annotations.SerializedName;

public class ServiceItem {
    @SerializedName("id")
    private Long id;

    @SerializedName("serviceId")
    private Long serviceId;

    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;

    @SerializedName("healthEndpoint")
    private String healthEndpoint;

    @SerializedName("description")
    private String description;

    @SerializedName("status")
    private String status;

    @SerializedName("responseTime")
    private Long responseTime;

    @SerializedName("checkedAt")
    private String checkedAt;

    public ServiceItem() {}

    public Long getId() {
        return id != null ? id : serviceId;
    }
    public void setId(Long id) { this.id = id; }

    public Long getServiceId() { return serviceId != null ? serviceId : id; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getHealthEndpoint() { return healthEndpoint; }
    public void setHealthEndpoint(String healthEndpoint) { this.healthEndpoint = healthEndpoint; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status != null ? status : "UNKNOWN"; }
    public void setStatus(String status) { this.status = status; }

    public Long getResponseTime() { return responseTime; }
    public void setResponseTime(Long responseTime) { this.responseTime = responseTime; }

    public String getCheckedAt() { return checkedAt; }
    public void setCheckedAt(String checkedAt) { this.checkedAt = checkedAt; }
}
