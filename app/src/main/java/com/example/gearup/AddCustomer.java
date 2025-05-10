package com.example.gearup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddCustomer extends AppCompatActivity {

    EditText etCustomerName, etPhone, etAddress, etVehicleName;
    RadioGroup rgVehicleType, rgServiceType;
    Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_customer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();

        btnCancel.setOnClickListener(v -> finish()); // Close activity

        btnSave.setOnClickListener(v -> {
            String name = etCustomerName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String vehicleName = etVehicleName.getText().toString().trim();

            int vehicleTypeId = rgVehicleType.getCheckedRadioButtonId();
            int serviceTypeId = rgServiceType.getCheckedRadioButtonId();

            // Input validation
            if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || vehicleName.isEmpty() ||
                    vehicleTypeId == -1 || serviceTypeId == -1) {
                Toast.makeText(AddCustomer.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedVehicle = findViewById(vehicleTypeId);
            RadioButton selectedService = findViewById(serviceTypeId);
            String vehicleType = selectedVehicle.getText().toString();
            String serviceType = selectedService.getText().toString();

            // Create customer data
            CustomerData customer = new CustomerData(name, phone, address, vehicleName, vehicleType, serviceType);

            // Get reference to Firebase database
            DatabaseReference database = FirebaseDatabase.getInstance().getReference("customers");

            // Generate unique key for customer
            String customerId = database.push().getKey(); // Generate unique key
            customer.setId(customerId); // Set the generated ID to the customer object

            // Save customer data to Firebase Realtime Database
            if (customerId != null) {
                database.child(customerId).setValue(customer)
                        .addOnSuccessListener(aVoid -> {
//                            Toast.makeText(this, "Customer added", Toast.LENGTH_SHORT).show();
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("name", name);
                            resultIntent.putExtra("phone", phone);
                            resultIntent.putExtra("address", address);
                            resultIntent.putExtra("vehicle", vehicleName);
                            resultIntent.putExtra("vehicleType", vehicleType);
                            resultIntent.putExtra("service", serviceType);
                            setResult(RESULT_OK, resultIntent);
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Failed to add customer: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(this, "Failed to generate customer ID", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void init(){
        etCustomerName = findViewById(R.id.etCustomerName);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        etVehicleName = findViewById(R.id.etVehicleName);
        rgVehicleType = findViewById(R.id.rgVehicleType);
        rgServiceType = findViewById(R.id.rgServiceType);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }
}