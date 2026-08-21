package com.microwatch.app.data.model;

import com.google.gson.annotations.SerializedName;

public class NotificationItem {
    @SerializedName("id")
    private Long id;

    @SerializedName("serviceId")
    private Long serviceId;

    @SerializedName("title")
    private String title;

    @SerializedName("message")
    private String message;

    @SerializedName("notificationType")
    private String notificationType;

    @SerializedName("isRead")
    private Boolean isRead;

    @SerializedName("createdAt")
    private String createdAt;

    public NotificationItem() {}

    public Long getId() { return id; }
    public Long getServiceId() { return serviceId; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getNotificationType() { return notificationType; }
    public Boolean getIsRead() { return isRead; }
    public String getCreatedAt() { return createdAt; }
}
