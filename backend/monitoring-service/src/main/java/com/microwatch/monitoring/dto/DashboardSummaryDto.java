package com.microwatch.monitoring.dto;

public class DashboardSummaryDto {
    private long totalServices;
    private long healthyServices;
    private long downServices;
    private double averageResponseTime;
    private double overallUptime;

    public DashboardSummaryDto() {}

    public DashboardSummaryDto(long totalServices, long healthyServices, long downServices, double averageResponseTime, double overallUptime) {
        this.totalServices = totalServices;
        this.healthyServices = healthyServices;
        this.downServices = downServices;
        this.averageResponseTime = averageResponseTime;
        this.overallUptime = overallUptime;
    }

    public long getTotalServices() { return totalServices; }
    public void setTotalServices(long totalServices) { this.totalServices = totalServices; }

    public long getHealthyServices() { return healthyServices; }
    public void setHealthyServices(long healthyServices) { this.healthyServices = healthyServices; }

    public long getDownServices() { return downServices; }
    public void setDownServices(long downServices) { this.downServices = downServices; }

    public double getAverageResponseTime() { return averageResponseTime; }
    public void setAverageResponseTime(double averageResponseTime) { this.averageResponseTime = averageResponseTime; }

    public double getOverallUptime() { return overallUptime; }
    public void setOverallUptime(double overallUptime) { this.overallUptime = overallUptime; }
}
