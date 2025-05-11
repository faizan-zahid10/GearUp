package com.example.gearup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gearup.models.ServiceModel;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    private List<ServiceModel> serviceList;
    private List<String> serviceKeys;
    private final OnServiceClickListener listener;

    public interface OnServiceClickListener {
        void onEdit(ServiceModel service, String key);
        void onDelete(String key);
    }

    public ServiceAdapter(List<ServiceModel> serviceList, OnServiceClickListener listener) {
        this.serviceList = serviceList;
        this.listener = listener;
    }

    public void updateData(List<ServiceModel> newList, List<String> keys) {
        this.serviceList = newList;
        this.serviceKeys = keys;
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
        ServiceModel service = serviceList.get(position);
        String key = (serviceKeys != null && position < serviceKeys.size()) ? serviceKeys.get(position) : "";

        holder.name.setText("Customer: " + service.getCustomerName());
        holder.phone.setText("Phone: " + service.getCustomerPhone());
        holder.vehicleNumber.setText("Vehicle #: " + service.getVehicleNumber());
        holder.vehicleType.setText("Type: " + service.getVehicleType());
        holder.serviceTime.setText("Time: " + service.getServiceTime());

        if (!key.isEmpty()) {
            holder.btnEdit.setVisibility(View.VISIBLE);
            holder.btnDelete.setVisibility(View.VISIBLE);

            holder.btnEdit.setOnClickListener(v -> listener.onEdit(service, key));
            holder.btnDelete.setOnClickListener(v -> listener.onDelete(key));
        } else {
            holder.btnEdit.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return serviceList != null ? serviceList.size() : 0;
    }

    static class ServiceViewHolder extends RecyclerView.ViewHolder {
        TextView name, phone, vehicleNumber, vehicleType, serviceTime;
        ImageButton btnEdit, btnDelete;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_customer_name);
            phone = itemView.findViewById(R.id.tv_customer_number);
            vehicleNumber = itemView.findViewById(R.id.tv_vehicle_number);
            vehicleType = itemView.findViewById(R.id.tv_vehicle_type);
            serviceTime = itemView.findViewById(R.id.tv_service_time);
            btnEdit = itemView.findViewById(R.id.btn_edit_service);
            btnDelete = itemView.findViewById(R.id.btn_delete_service);
        }
    }
}
