package com.microwatch.monitoring.scheduler;

import com.microwatch.monitoring.service.MonitoringService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HealthCheckScheduler {

    private final MonitoringService monitoringService;

    public HealthCheckScheduler(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @Scheduled(fixedRateString = "${monitoring.interval.ms:10000}")
    public void runHealthChecks() {
        monitoringService.checkAllServices();
    }
}
