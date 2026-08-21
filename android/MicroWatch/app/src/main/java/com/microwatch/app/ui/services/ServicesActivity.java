package com.microwatch.app.ui.services;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.microwatch.app.R;
import com.microwatch.app.data.model.ServiceItem;
import com.microwatch.app.data.repository.MonitoringRepository;
import com.microwatch.app.notification.NotificationHelper;
import com.microwatch.app.ui.details.ServiceDetailsActivity;
import com.microwatch.app.utils.PreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView rvServices;
    private ServiceAdapter adapter;
    private MonitoringRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Monitored Microservices");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        PreferenceManager preferenceManager = new PreferenceManager(this);
        repository = new MonitoringRepository(preferenceManager.getServerUrl());

        swipeRefresh = findViewById(R.id.swipe_refresh);
        rvServices = findViewById(R.id.rv_services);

        rvServices.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ServiceAdapter(service -> {
            Intent intent = new Intent(ServicesActivity.this, ServiceDetailsActivity.class);
            intent.putExtra(NotificationHelper.EXTRA_SERVICE_ID, service.getServiceId());
            startActivity(intent);
        });
        rvServices.setAdapter(adapter);

        swipeRefresh.setOnRefreshListener(this::loadServices);
        loadServices();
    }

    private void loadServices() {
        swipeRefresh.setRefreshing(true);
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
                Toast.makeText(ServicesActivity.this, "Failed to load services", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
