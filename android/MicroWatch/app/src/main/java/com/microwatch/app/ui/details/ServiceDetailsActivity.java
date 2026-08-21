package com.microwatch.app.ui.details;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.microwatch.app.R;
import com.microwatch.app.data.model.MetricItem;
import com.microwatch.app.data.model.ServiceItem;
import com.microwatch.app.data.repository.MonitoringRepository;
import com.microwatch.app.notification.NotificationHelper;
import com.microwatch.app.utils.PreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceDetailsActivity extends AppCompatActivity {

    private TextView tvName, tvStatus, tvUrl, tvDesc, tvResponseTime, tvUptime;
    private MonitoringRepository repository;
    private Long serviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Service Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        serviceId = getIntent().getLongExtra(NotificationHelper.EXTRA_SERVICE_ID, -1);

        PreferenceManager preferenceManager = new PreferenceManager(this);
        repository = new MonitoringRepository(preferenceManager.getServerUrl());

        tvName = findViewById(R.id.tv_detail_name);
        tvStatus = findViewById(R.id.tv_detail_status);
        tvUrl = findViewById(R.id.tv_detail_url);
        tvDesc = findViewById(R.id.tv_detail_desc);
        tvResponseTime = findViewById(R.id.tv_detail_response_time);
        tvUptime = findViewById(R.id.tv_detail_uptime);

        if (serviceId != -1) {
            loadDetails();
        } else {
            Toast.makeText(this, "Invalid Service ID", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadDetails() {
        repository.fetchServiceDetails(serviceId, new Callback<ServiceItem>() {
            @Override
            public void onResponse(Call<ServiceItem> call, Response<ServiceItem> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ServiceItem item = response.body();
                    tvName.setText(item.getName());
                    tvUrl.setText(item.getUrl() + (item.getHealthEndpoint() != null ? item.getHealthEndpoint() : "/health"));
                    tvDesc.setText(item.getDescription() != null ? item.getDescription() : "No description available.");
                }
            }

            @Override
            public void onFailure(Call<ServiceItem> call, Throwable t) {
                Toast.makeText(ServiceDetailsActivity.this, "Failed to load service metadata", Toast.LENGTH_SHORT).show();
            }
        });

        repository.fetchServiceMetrics(serviceId, "24h", new Callback<List<MetricItem>>() {
            @Override
            public void onResponse(Call<List<MetricItem>> call, Response<List<MetricItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MetricItem> metrics = response.body();
                    if (!metrics.isEmpty()) {
                        MetricItem latest = metrics.get(0);
                        String status = latest.getStatus();

                        if ("UP".equalsIgnoreCase(status)) {
                            tvStatus.setText("🟢 UP");
                            tvStatus.setTextColor(ContextCompat.getColor(ServiceDetailsActivity.this, R.color.accent_emerald));
                        } else if ("DOWN".equalsIgnoreCase(status)) {
                            tvStatus.setText("🔴 DOWN");
                            tvStatus.setTextColor(ContextCompat.getColor(ServiceDetailsActivity.this, R.color.accent_rose));
                        } else {
                            tvStatus.setText("🟡 UNKNOWN");
                            tvStatus.setTextColor(ContextCompat.getColor(ServiceDetailsActivity.this, R.color.accent_amber));
                        }

                        tvResponseTime.setText(latest.getResponseTime() != null ? latest.getResponseTime() + " ms" : "N/A");

                        long total = metrics.size();
                        long upCount = 0;
                        for (MetricItem m : metrics) {
                            if ("UP".equalsIgnoreCase(m.getStatus())) upCount++;
                        }
                        double uptime = total > 0 ? ((double) upCount / total) * 100.0 : 100.0;
                        tvUptime.setText(String.format("%.1f%%", uptime));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MetricItem>> call, Throwable t) {
                Toast.makeText(ServiceDetailsActivity.this, "Failed to load service metrics", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
