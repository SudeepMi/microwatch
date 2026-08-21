package com.microwatch.app.data.model;

import com.google.gson.annotations.SerializedName;

public class DashboardSummary {
    @SerializedName("totalServices")
    private long totalServices;

    @SerializedName("healthyServices")
    private long healthyServices;

    @SerializedName("downServices")
    private long downServices;

    @SerializedName("averageResponseTime")
    private double averageResponseTime;

    @SerializedName("overallUptime")
    private double overallUptime;

    public DashboardSummary() {}

    public long getTotalServices() { return totalServices; }
    public long getHealthyServices() { return healthyServices; }
    public long getDownServices() { return downServices; }
    public double getAverageResponseTime() { return averageResponseTime; }
    public double getOverallUptime() { return overallUptime; }
}
