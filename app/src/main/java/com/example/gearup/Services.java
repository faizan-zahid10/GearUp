package com.example.gearup;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gearup.models.ServiceModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Services extends Fragment {

    private Calendar selectedDate;
    private TextView tvSelectedDate;
    private RecyclerView recyclerView;
    private ServiceAdapter adapter;
    private DatabaseReference servicesRef;
    private TextView tvNoServices;

    private final SimpleDateFormat keyFormatter = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private final SimpleDateFormat displayFormatter = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());

    public Services() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_services, container, false);

        selectedDate = Calendar.getInstance();
        tvSelectedDate = view.findViewById(R.id.tv_selected_date);
        ImageButton btnPrevDate = view.findViewById(R.id.btn_prev_date);
        ImageButton btnNextDate = view.findViewById(R.id.btn_next_date);
        recyclerView = view.findViewById(R.id.recycler_services);
        tvNoServices = view.findViewById(R.id.tv_no_services);  // Reference for the "No services" text

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        servicesRef = FirebaseDatabase.getInstance().getReference("services");

        ServiceAdapter.OnServiceClickListener listener = new ServiceAdapter.OnServiceClickListener() {
            @Override
            public void onEdit(ServiceModel service, String key) {
                showEditServiceDialog(service, key);
            }

            @Override
            public void onDelete(String key) {
                String dateKey = keyFormatter.format(selectedDate.getTime());
                servicesRef.child(dateKey).child(key).removeValue().addOnSuccessListener(unused -> {
                    loadServicesForDate(selectedDate);
                });
            }
        };

        adapter = new ServiceAdapter(new ArrayList<>(), listener);
        recyclerView.setAdapter(adapter);

        updateDateDisplay();
        loadServicesForDate(selectedDate);

        tvSelectedDate.setOnClickListener(v -> showDatePickerDialog());

        btnPrevDate.setOnClickListener(v -> {
            selectedDate.add(Calendar.DAY_OF_MONTH, -1);
            updateDateDisplay();
            loadServicesForDate(selectedDate);
        });

        btnNextDate.setOnClickListener(v -> {
            selectedDate.add(Calendar.DAY_OF_MONTH, 1);
            updateDateDisplay();
            loadServicesForDate(selectedDate);
        });

        FloatingActionButton fabAddService = view.findViewById(R.id.fab_add_service);
        fabAddService.setOnClickListener(v -> showAddServiceDialog());

        return view;
    }

    private void updateDateDisplay() {
        tvSelectedDate.setText(displayFormatter.format(selectedDate.getTime()));
    }

    private void loadServicesForDate(Calendar date) {
        String key = keyFormatter.format(date.getTime());

        servicesRef.child(key).get().addOnCompleteListener(task -> {
            List<ServiceModel> services = new ArrayList<>();
            List<String> keys = new ArrayList<>();

            if (task.isSuccessful() && task.getResult().exists()) {
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    ServiceModel service = snapshot.getValue(ServiceModel.class);
                    if (service != null) {
                        services.add(service);
                        keys.add(snapshot.getKey());
                    }
                }
            }

            // If no services for the selected date, show "No services" message
            if (services.isEmpty()) {
                tvNoServices.setVisibility(View.VISIBLE); // Show the "No services" message
                recyclerView.setVisibility(View.GONE); // Hide the RecyclerView
            } else {
                tvNoServices.setVisibility(View.GONE); // Hide the "No services" message
                recyclerView.setVisibility(View.VISIBLE); // Show the RecyclerView
            }

            adapter.updateData(services, keys); // Update adapter with services
        });
    }

    private void showDatePickerDialog() {
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    selectedDate.set(Calendar.YEAR, selectedYear);
                    selectedDate.set(Calendar.MONTH, selectedMonth);
                    selectedDate.set(Calendar.DAY_OF_MONTH, selectedDay);
                    updateDateDisplay();
                    loadServicesForDate(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void showAddServiceDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_service, null);

        EditText etName = dialogView.findViewById(R.id.et_customer_name);
        EditText etPhone = dialogView.findViewById(R.id.et_customer_phone);
        EditText etVehicle = dialogView.findViewById(R.id.et_vehicle_number);
        EditText etType = dialogView.findViewById(R.id.et_vehicle_type);
        EditText etTime = dialogView.findViewById(R.id.et_service_time);
        EditText etDate = dialogView.findViewById(R.id.et_service_date);

        Calendar serviceDate = Calendar.getInstance();
        etDate.setText(displayFormatter.format(serviceDate.getTime()));

        etDate.setOnClickListener(v -> {
            int year = serviceDate.get(Calendar.YEAR);
            int month = serviceDate.get(Calendar.MONTH);
            int day = serviceDate.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePicker = new DatePickerDialog(getContext(), (view, y, m, d) -> {
                Calendar picked = Calendar.getInstance();
                picked.set(y, m, d);

                Calendar today = Calendar.getInstance();
                today.set(Calendar.HOUR_OF_DAY, 0);
                today.set(Calendar.MINUTE, 0);
                today.set(Calendar.SECOND, 0);
                today.set(Calendar.MILLISECOND, 0);

                if (picked.before(today)) {
                    etDate.setError("Cannot select a past date");
                } else {
                    serviceDate.set(y, m, d);
                    etDate.setText(displayFormatter.format(serviceDate.getTime()));
                    etDate.setError(null);
                }
            }, year, month, day);

            datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePicker.show();
        });

        new AlertDialog.Builder(getContext())
                .setTitle("Add New Service")
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    String name = etName.getText().toString().trim();
                    String phone = etPhone.getText().toString().trim();
                    String vehicle = etVehicle.getText().toString().trim();
                    String type = etType.getText().toString().trim();
                    String time = etTime.getText().toString().trim();
                    String dateText = etDate.getText().toString().trim();

                    if (!name.isEmpty() && !phone.isEmpty() && !vehicle.isEmpty() && !type.isEmpty() && !time.isEmpty() && !dateText.isEmpty()) {
                        try {
                            serviceDate.setTime(displayFormatter.parse(dateText));
                        } catch (Exception e) {
                            Log.e("ParseError", "Invalid date format", e);
                            return;
                        }

                        String key = keyFormatter.format(serviceDate.getTime());
                        ServiceModel newService = new ServiceModel(name, phone, vehicle, type, time, "");

                        servicesRef.child(key).push().setValue(newService).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                if (key.equals(keyFormatter.format(selectedDate.getTime()))) {
                                    loadServicesForDate(selectedDate);
                                }
                            } else {
                                Log.e("FirebaseError", "Failed to add service", task.getException());
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void showEditServiceDialog(ServiceModel service, String serviceKey) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_service, null);

        EditText etName = dialogView.findViewById(R.id.et_customer_name);
        EditText etPhone = dialogView.findViewById(R.id.et_customer_phone);
        EditText etVehicle = dialogView.findViewById(R.id.et_vehicle_number);
        EditText etType = dialogView.findViewById(R.id.et_vehicle_type);
        EditText etTime = dialogView.findViewById(R.id.et_service_time);
        EditText etDate = dialogView.findViewById(R.id.et_service_date);

        etName.setText(service.getCustomerName());
        etPhone.setText(service.getCustomerPhone());
        etVehicle.setText(service.getVehicleNumber());
        etType.setText(service.getVehicleType());
        etTime.setText(service.getServiceTime());

        Calendar originalDate = (Calendar) selectedDate.clone();
        Calendar pickedDate = (Calendar) selectedDate.clone();

        etDate.setText(displayFormatter.format(originalDate.getTime()));
        etDate.setEnabled(true);

        etDate.setOnClickListener(v -> {
            int year = pickedDate.get(Calendar.YEAR);
            int month = pickedDate.get(Calendar.MONTH);
            int day = pickedDate.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePicker = new DatePickerDialog(getContext(), (view, y, m, d) -> {
                pickedDate.set(y, m, d);
                etDate.setText(displayFormatter.format(pickedDate.getTime()));
            }, year, month, day);

            datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePicker.show();
        });

        new AlertDialog.Builder(getContext())
                .setTitle("Edit Service")
                .setView(dialogView)
                .setPositiveButton("Update", (dialog, which) -> {
                    String name = etName.getText().toString().trim();
                    String phone = etPhone.getText().toString().trim();
                    String vehicle = etVehicle.getText().toString().trim();
                    String type = etType.getText().toString().trim();
                    String time = etTime.getText().toString().trim();

                    if (!name.isEmpty() && !phone.isEmpty() && !vehicle.isEmpty() && !type.isEmpty() && !time.isEmpty()) {
                        String oldKey = keyFormatter.format(originalDate.getTime());
                        String newKey = keyFormatter.format(pickedDate.getTime());

                        ServiceModel updated = new ServiceModel(name, phone, vehicle, type, time, serviceKey);

                        if (oldKey.equals(newKey)) {
                            // Same date — just update the service
                            servicesRef.child(oldKey).child(serviceKey).setValue(updated).addOnSuccessListener(unused -> {
                                loadServicesForDate(selectedDate);
                            });
                        } else {
                            // Date changed — move to new date node
                            servicesRef.child(oldKey).child(serviceKey).removeValue().addOnSuccessListener(unused -> {
                                servicesRef.child(newKey).push().setValue(updated).addOnSuccessListener(aVoid -> {
                                    if (newKey.equals(keyFormatter.format(selectedDate.getTime()))) {
                                        loadServicesForDate(selectedDate);
                                    }
                                });
                            });
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}
