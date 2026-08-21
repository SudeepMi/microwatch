package com.microwatch.app.ui.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.microwatch.app.R;
import com.microwatch.app.data.model.NotificationItem;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<NotificationItem> notifications = new ArrayList<>();

    public void setNotifications(List<NotificationItem> newNotifications) {
        this.notifications = newNotifications != null ? newNotifications : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationItem item = notifications.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvMessage, tvTime;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_notification_title);
            tvMessage = itemView.findViewById(R.id.tv_notification_message);
            tvTime = itemView.findViewById(R.id.tv_notification_time);
        }

        public void bind(NotificationItem item) {
            tvTitle.setText(item.getTitle());
            tvMessage.setText(item.getMessage());
            tvTime.setText(item.getCreatedAt());

            if ("SERVICE_DOWN".equalsIgnoreCase(item.getNotificationType())) {
                tvTitle.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.accent_rose));
            } else {
                tvTitle.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.accent_emerald));
            }
        }
    }
}
