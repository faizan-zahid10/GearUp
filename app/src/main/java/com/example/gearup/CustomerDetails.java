package com.example.gearup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CustomerDetails extends AppCompatActivity {

    TextView tvName,tvPhone,tvAddress,tvVehicleName,tvVehicleType,tvServiceType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();

        Intent intent = getIntent();
        tvName.setText(intent.getStringExtra("name"));
        tvPhone.setText(intent.getStringExtra("phone"));
        tvAddress.setText(intent.getStringExtra("address"));
        tvVehicleName.setText(intent.getStringExtra("vehicle"));
        tvVehicleType.setText(intent.getStringExtra("vehicleType"));
        tvServiceType.setText(intent.getStringExtra("service"));


    }

    private void init(){
        tvName = findViewById(R.id.tvCustomerName);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);
        tvVehicleName = findViewById(R.id.tvVehicleName);
        tvVehicleType = findViewById(R.id.tvVehicleType);
        tvServiceType = findViewById(R.id.tvServiceType);
    }
}