package com.example.gearup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gearup.models.ServiceModel;
import com.example.gearup.R;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {
    private List<ServiceModel> serviceList;

    public ServiceAdapter(List<ServiceModel> serviceList) {
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_service, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        ServiceModel service = serviceList.get(position);
        holder.tvName.setText("Customer: " + service.getCustomerName());
        holder.tvPhone.setText("Phone: " + service.getCustomerPhone());
        holder.tvVehicle.setText("Vehicle: " + service.getVehicleNumber());
        holder.tvTime.setText("Time: " + service.getServiceTime());
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    // ✅ New method to update list dynamically
    public void updateData(List<ServiceModel> newList) {
        serviceList.clear();
        serviceList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone, tvVehicle, tvTime;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_customer_name);
            tvPhone = itemView.findViewById(R.id.tv_customer_number);
            tvVehicle = itemView.findViewById(R.id.tv_vehicle_number);
            tvTime = itemView.findViewById(R.id.tv_service_time);
        }
    }
}
