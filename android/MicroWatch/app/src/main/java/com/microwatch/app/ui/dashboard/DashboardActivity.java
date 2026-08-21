package com.microwatch.app.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.microwatch.app.R;
import com.microwatch.app.data.model.DashboardSummary;
import com.microwatch.app.data.model.ServiceItem;
import com.microwatch.app.data.repository.MonitoringRepository;
import com.microwatch.app.notification.NotificationHelper;
import com.microwatch.app.ui.details.ServiceDetailsActivity;
import com.microwatch.app.ui.notifications.NotificationsActivity;
import com.microwatch.app.ui.services.ServiceAdapter;
import com.microwatch.app.ui.services.ServicesActivity;
import com.microwatch.app.utils.NetworkUtils;
import com.microwatch.app.utils.PreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvTotal, tvHealthy, tvDown, tvAvgResponse, tvOfflineBanner;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView rvServices;
    private ServiceAdapter adapter;
    private MonitoringRepository repository;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        preferenceManager = new PreferenceManager(this);
        repository = new MonitoringRepository(preferenceManager.getServerUrl());

        tvTotal = findViewById(R.id.tv_total_services);
        tvHealthy = findViewById(R.id.tv_healthy_services);
        tvDown = findViewById(R.id.tv_down_services);
        tvAvgResponse = findViewById(R.id.tv_avg_response_time);
        tvOfflineBanner = findViewById(R.id.tv_offline_banner);

        swipeRefresh = findViewById(R.id.swipe_refresh);
        rvServices = findViewById(R.id.rv_services);

        Button btnViewAll = findViewById(R.id.btn_view_all_services);
        Button btnNotifications = findViewById(R.id.btn_nav_notifications);

        rvServices.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ServiceAdapter(service -> {
            Intent intent = new Intent(DashboardActivity.this, ServiceDetailsActivity.class);
            intent.putExtra(NotificationHelper.EXTRA_SERVICE_ID, service.getServiceId());
            startActivity(intent);
        });
        rvServices.setAdapter(adapter);

        swipeRefresh.setOnRefreshListener(this::loadDashboardData);

        btnViewAll.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, ServicesActivity.class)));
        btnNotifications.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, NotificationsActivity.class)));

        loadDashboardData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDashboardData();
    }

    private void loadDashboardData() {
        boolean isOnline = NetworkUtils.isNetworkAvailable(this);
        tvOfflineBanner.setVisibility(isOnline ? View.GONE : View.VISIBLE);

        if (!isOnline) {
            swipeRefresh.setRefreshing(false);
            Toast.makeText(this, R.string.offline_message, Toast.LENGTH_SHORT).show();
            return;
        }

        swipeRefresh.setRefreshing(true);

        repository.fetchDashboardSummary(new Callback<DashboardSummary>() {
            @Override
            public void onResponse(Call<DashboardSummary> call, Response<DashboardSummary> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DashboardSummary summary = response.body();
                    tvTotal.setText(String.valueOf(summary.getTotalServices()));
                    tvHealthy.setText(String.valueOf(summary.getHealthyServices()));
                    tvDown.setText(String.valueOf(summary.getDownServices()));
                    tvAvgResponse.setText(summary.getAverageResponseTime() + " ms");
                }
            }

            @Override
            public void onFailure(Call<DashboardSummary> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Failed to load summary", Toast.LENGTH_SHORT).show();
            }
        });

        repository.fetchLatestMetrics(new Callback<List<ServiceItem>>() {
            @Override
            public void onResponse(Call<List<ServiceItem>> call, Response<List<ServiceItem>> response) {
                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setServices(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ServiceItem>> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                Toast.makeText(DashboardActivity.this, "Failed to load service metrics", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
