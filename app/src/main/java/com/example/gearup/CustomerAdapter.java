package com.example.gearup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private Context context;
    private List<CustomerData> customerList;
    private OnCustomerClickListener listener;

    public interface OnCustomerClickListener {
        void onCustomerClick(CustomerData customer);
    }

    public CustomerAdapter(Context context, List<CustomerData> customerList, OnCustomerClickListener listener) {
        this.context = context;
        this.customerList = customerList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_customer_design, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        CustomerData customer = customerList.get(position);
        holder.tvName.setText(customer.getName());
        holder.tvPhone.setText(customer.getPhone());

        // Set vehicle image based on type
        String vehicleType = customer.getVehicleType().toLowerCase();
        switch (vehicleType) {
            case "car":
                holder.ivVehicle.setImageResource(R.drawable.icon_car);
                break;
            case "bike":
                holder.ivVehicle.setImageResource(R.drawable.icon_bike);
                break;
            case "bicycle":
                holder.ivVehicle.setImageResource(R.drawable.icon_bicycle);
                break;
            case "rickshaw":
                holder.ivVehicle.setImageResource(R.drawable.icon_rickshaw);
                break;
            case "bus":
                holder.ivVehicle.setImageResource(R.drawable.icon_bus);
                break;
            default:
                holder.ivVehicle.setImageResource(R.drawable.icon_car);
                break;
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CustomerDetails.class);
            intent.putExtra("name", customer.getName());
            intent.putExtra("phone", customer.getPhone());
            intent.putExtra("address", customer.getAddress());
            intent.putExtra("vehicle", customer.getVehicleName());
            intent.putExtra("vehicleType", customer.getVehicleType());
            intent.putExtra("service", customer.getServiceType());
            context.startActivity(intent);
        });


        // Handle Delete
        holder.btnDelete.setOnClickListener(v -> {
            new android.app.AlertDialog.Builder(context)
                    .setTitle("Delete Customer")
                    .setMessage("Are you sure you want to delete this customer?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Get customer ID
                        String customerId = customer.getId();

                        // Firebase database reference
                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("customers").child(customerId);

                        // Delete from Firebase
                        dbRef.removeValue()
                                .addOnSuccessListener(unused -> {
                                    customerList.remove(position);
//                                    notifyItemRemoved(position);
//                                    notifyItemRangeChanged(position, customerList.size());
//                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Customer deleted from database", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(context, "Failed to delete customer", Toast.LENGTH_SHORT).show();
                                });
                    })
                    .setNegativeButton("No", null)
                    .show();
        });



//edit
        holder.btnEdit.setOnClickListener(v -> {
            // Inflate the dialog layout
            LayoutInflater inflater = LayoutInflater.from(context);
            View dialogView = inflater.inflate(R.layout.dialog_edit_customer, null);

            // Find the EditText fields in the dialog
            EditText etName = dialogView.findViewById(R.id.etCustomerName);
            EditText etPhone = dialogView.findViewById(R.id.etCustomerPhone);
            EditText etAddress = dialogView.findViewById(R.id.etCustomerAddress);
            EditText etVehicleName = dialogView.findViewById(R.id.etCustomerVehicleName);

            // Set the current customer details in the EditText fields
            etName.setText(customer.getName());
            etPhone.setText(customer.getPhone());
            etAddress.setText(customer.getAddress());
            etVehicleName.setText(customer.getVehicleName());

            // Create and show the dialog
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle("Edit Customer")
                    .setView(dialogView)
                    .setCancelable(false)
                    .create();

            // Get references to the Save and Cancel buttons in the dialog
            Button btnSave = dialogView.findViewById(R.id.btnSave);
            Button btnCancel = dialogView.findViewById(R.id.btnCancel);

            // Handle Save button click
            btnSave.setOnClickListener(view -> {
                // Update the customer details with the new input values
                customer.setName(etName.getText().toString());
                customer.setPhone(etPhone.getText().toString());
                customer.setAddress(etAddress.getText().toString());
                customer.setVehicleName(etVehicleName.getText().toString());

                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("customers").child(customer.getId());
                dbRef.setValue(customer)
                        .addOnSuccessListener(unused -> {
                            // Notify the adapter that the customer details have been updated
                            notifyItemChanged(holder.getAdapterPosition());

                            // Show a Toast to confirm the update
                            Toast.makeText(context, "Customer updated in database", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(context, "Failed to update customer", Toast.LENGTH_SHORT).show();
                        });

//                // Notify the adapter that the customer details have been updated
//                notifyItemChanged(holder.getAdapterPosition());
//
//                // Show a Toast to confirm the update
//                Toast.makeText(context, "Customer updated", Toast.LENGTH_SHORT).show();

                // Dismiss the dialog
                dialog.dismiss();
            });

            // Handle Cancel button click (dismiss the dialog without saving)
            btnCancel.setOnClickListener(view -> dialog.dismiss());

            // Show the dialog
            dialog.show();
        });

    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone;
        ImageView ivVehicle;
        ImageView btnDelete;
        ImageView btnEdit;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            ivVehicle=itemView.findViewById(R.id.ivVehicle);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}
