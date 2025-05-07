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

            Intent resultIntent = new Intent();
            resultIntent.putExtra("name", name);
            resultIntent.putExtra("phone", phone);
            resultIntent.putExtra("address", address);
            resultIntent.putExtra("vehicle", vehicleName);
            resultIntent.putExtra("vehicleType", vehicleType);
            resultIntent.putExtra("service", serviceType);

            setResult(RESULT_OK, resultIntent);
            finish();

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