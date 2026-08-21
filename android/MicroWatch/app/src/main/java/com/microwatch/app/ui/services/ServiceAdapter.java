package com.microwatch.app.ui.services;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.microwatch.app.R;
import com.microwatch.app.data.model.ServiceItem;

import java.util.ArrayList;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    public interface OnServiceClickListener {
        void onServiceClick(ServiceItem service);
    }

    private List<ServiceItem> services = new ArrayList<>();
    private final OnServiceClickListener listener;

    public ServiceAdapter(OnServiceClickListener listener) {
        this.listener = listener;
    }

    public void setServices(List<ServiceItem> newServices) {
        this.services = newServices != null ? newServices : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        ServiceItem service = services.get(position);
        holder.bind(service, listener);
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    static class ServiceViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvStatus, tvUrl, tvResponseTime;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_service_name);
            tvStatus = itemView.findViewById(R.id.tv_service_status);
            tvUrl = itemView.findViewById(R.id.tv_service_url);
            tvResponseTime = itemView.findViewById(R.id.tv_response_time);
        }

        public void bind(ServiceItem service, OnServiceClickListener listener) {
            tvName.setText(service.getName());
            tvUrl.setText(service.getUrl() + (service.getHealthEndpoint() != null ? service.getHealthEndpoint() : "/health"));

            String status = service.getStatus();
            if ("UP".equalsIgnoreCase(status)) {
                tvStatus.setText("● UP");
                tvStatus.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.accent_emerald));
                tvStatus.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.status_up_bg));
            } else if ("DOWN".equalsIgnoreCase(status)) {
                tvStatus.setText("● DOWN");
                tvStatus.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.accent_rose));
                tvStatus.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.status_down_bg));
            } else {
                tvStatus.setText("● UNKNOWN");
                tvStatus.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.accent_amber));
                tvStatus.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.status_unknown_bg));
            }

            Long rt = service.getResponseTime();
            tvResponseTime.setText(rt != null ? "Response: " + rt + " ms" : "Response: —");

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onServiceClick(service);
                }
            });
        }
    }
}
