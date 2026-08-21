package com.microwatch.app.data.repository;

import com.microwatch.app.data.api.ApiClient;
import com.microwatch.app.data.api.ApiService;
import com.microwatch.app.data.model.DashboardSummary;
import com.microwatch.app.data.model.MetricItem;
import com.microwatch.app.data.model.NotificationItem;
import com.microwatch.app.data.model.ServiceItem;

import java.util.List;

import retrofit2.Callback;

public class MonitoringRepository {

    private final ApiService apiService;

    public MonitoringRepository(String baseUrl) {
        this.apiService = ApiClient.getApiService(baseUrl);
    }

    public void fetchDashboardSummary(Callback<DashboardSummary> callback) {
        apiService.getDashboardSummary().enqueue(callback);
    }

    public void fetchServices(Callback<List<ServiceItem>> callback) {
        apiService.getServices().enqueue(callback);
    }

    public void fetchLatestMetrics(Callback<List<ServiceItem>> callback) {
        apiService.getLatestMetrics().enqueue(callback);
    }

    public void fetchServiceDetails(Long id, Callback<ServiceItem> callback) {
        apiService.getServiceById(id).enqueue(callback);
    }

    public void fetchServiceMetrics(Long id, String period, Callback<List<MetricItem>> callback) {
        apiService.getMetrics(id, period).enqueue(callback);
    }

    public void fetchNotifications(Callback<List<NotificationItem>> callback) {
        apiService.getNotifications().enqueue(callback);
    }
}
