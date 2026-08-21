package com.microwatch.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private static final String PREF_NAME = "microwatch_prefs";
    private static final String KEY_SERVER_URL = "server_url";
    private static final String KEY_NOTIFICATIONS_ENABLED = "notifications_enabled";
    private static final String KEY_LAST_STATUS = "last_known_status_";

    private final SharedPreferences prefs;

    public PreferenceManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public String getServerUrl() {
        return prefs.getString(KEY_SERVER_URL, "http://10.0.2.2:8080/");
    }

    public void setServerUrl(String url) {
        prefs.edit().putString(KEY_SERVER_URL, url).apply();
    }

    public boolean isNotificationsEnabled() {
        return prefs.getBoolean(KEY_NOTIFICATIONS_ENABLED, true);
    }

    public void setNotificationsEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_NOTIFICATIONS_ENABLED, enabled).apply();
    }

    public String getLastKnownStatus(Long serviceId) {
        return prefs.getString(KEY_LAST_STATUS + serviceId, "UNKNOWN");
    }

    public void setLastKnownStatus(Long serviceId, String status) {
        prefs.edit().putString(KEY_LAST_STATUS + serviceId, status).apply();
    }
}
