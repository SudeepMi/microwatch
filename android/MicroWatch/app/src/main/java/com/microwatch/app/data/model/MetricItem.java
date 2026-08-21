package com.microwatch.app.data.model;

import com.google.gson.annotations.SerializedName;

public class MetricItem {
    @SerializedName("id")
    private Long id;

    @SerializedName("serviceId")
    private Long serviceId;

    @SerializedName("status")
    private String status;

    @SerializedName("responseTime")
    private Long responseTime;

    @SerializedName("checkedAt")
    private String checkedAt;

    public MetricItem() {}

    public Long getId() { return id; }
    public Long getServiceId() { return serviceId; }
    public String getStatus() { return status; }
    public Long getResponseTime() { return responseTime; }
    public String getCheckedAt() { return checkedAt; }
}
