package com.microwatch.app.notification;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.microwatch.app.data.api.ApiClient;
import com.microwatch.app.data.api.ApiService;
import com.microwatch.app.data.model.ServiceItem;
import com.microwatch.app.utils.PreferenceManager;

import java.util.List;

import retrofit2.Response;

public class StatusSyncWorker extends Worker {

    public StatusSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();
        PreferenceManager prefs = new PreferenceManager(context);

        if (!prefs.isNotificationsEnabled()) {
            return Result.success();
        }

        try {
            ApiService api = ApiClient.getApiService(prefs.getServerUrl());
            Response<List<ServiceItem>> response = api.getLatestMetrics().execute();

            if (response.isSuccessful() && response.body() != null) {
                List<ServiceItem> services = response.body();

                for (ServiceItem service : services) {
                    Long serviceId = service.getServiceId();
                    if (serviceId == null) continue;

                    String currentStatus = service.getStatus();
                    String previousStatus = prefs.getLastKnownStatus(serviceId);

                    if (!"UNKNOWN".equals(previousStatus) && !previousStatus.equalsIgnoreCase(currentStatus)) {
                        int notificationId = serviceId.intValue();

                        if ("DOWN".equalsIgnoreCase(currentStatus)) {
                            NotificationHelper.sendNotification(
                                    context,
                                    notificationId,
                                    serviceId,
                                    "MicroWatch Alert",
                                    service.getName() + " is DOWN"
                            );
                        } else if ("UP".equalsIgnoreCase(currentStatus)) {
                            NotificationHelper.sendNotification(
                                    context,
                                    notificationId,
                                    serviceId,
                                    "MicroWatch Recovery",
                                    service.getName() + " is UP again."
                            );
                        }
                    }

                    prefs.setLastKnownStatus(serviceId, currentStatus);
                }
            }
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.retry();
        }
    }
}
