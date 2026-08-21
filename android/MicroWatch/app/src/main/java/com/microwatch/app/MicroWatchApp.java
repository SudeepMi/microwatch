package com.microwatch.app;

import android.app.Application;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.microwatch.app.notification.NotificationHelper;
import com.microwatch.app.notification.StatusSyncWorker;

import java.util.concurrent.TimeUnit;

public class MicroWatchApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationHelper.createNotificationChannel(this);
        scheduleBackgroundSync();
    }

    private void scheduleBackgroundSync() {
        PeriodicWorkRequest syncWorkRequest = new PeriodicWorkRequest.Builder(
                StatusSyncWorker.class,
                15, TimeUnit.MINUTES)
                .build();

        WorkManager.getInstance(this).enqueue(syncWorkRequest);
    }
}
