package com.microwatch.app.ui.notifications;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.microwatch.app.R;
import com.microwatch.app.data.model.NotificationItem;
import com.microwatch.app.data.repository.MonitoringRepository;
import com.microwatch.app.utils.PreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView rvNotifications;
    private NotificationAdapter adapter;
    private MonitoringRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Failure & Recovery Alerts");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        PreferenceManager preferenceManager = new PreferenceManager(this);
        repository = new MonitoringRepository(preferenceManager.getServerUrl());

        swipeRefresh = findViewById(R.id.swipe_refresh);
        rvNotifications = findViewById(R.id.rv_notifications);

        rvNotifications.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotificationAdapter();
        rvNotifications.setAdapter(adapter);

        swipeRefresh.setOnRefreshListener(this::loadNotifications);
        loadNotifications();
    }

    private void loadNotifications() {
        swipeRefresh.setRefreshing(true);
        repository.fetchNotifications(new Callback<List<NotificationItem>>() {
            @Override
            public void onResponse(Call<List<NotificationItem>> call, Response<List<NotificationItem>> response) {
                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setNotifications(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<NotificationItem>> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                Toast.makeText(NotificationsActivity.this, "Failed to load notifications", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
