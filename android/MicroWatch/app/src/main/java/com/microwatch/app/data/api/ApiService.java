package com.microwatch.app.data.api;

import com.microwatch.app.data.model.DashboardSummary;
import com.microwatch.app.data.model.MetricItem;
import com.microwatch.app.data.model.NotificationItem;
import com.microwatch.app.data.model.ServiceItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("api/v1/services")
    Call<List<ServiceItem>> getServices();

    @GET("api/v1/services/{id}")
    Call<ServiceItem> getServiceById(@Path("id") Long id);

    @GET("api/v1/dashboard/summary")
    Call<DashboardSummary> getDashboardSummary();

    @GET("api/v1/metrics/latest")
    Call<List<ServiceItem>> getLatestMetrics();

    @GET("api/v1/services/{id}/metrics")
    Call<List<MetricItem>> getMetrics(@Path("id") Long id, @Query("period") String period);

    @GET("api/v1/notifications")
    Call<List<NotificationItem>> getNotifications();
}
