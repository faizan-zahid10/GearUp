package com.example.gearup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CustomerDetails extends AppCompatActivity {

    TextView tvName,tvPhone,tvAddress,tvVehicleName,tvVehicleType,tvServiceType;
    ImageView btnCall, btnMessage, btnLocation;


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

        btnCall.setOnClickListener(v -> {
            String phone = tvPhone.getText().toString();
            Intent i = new Intent(Intent.ACTION_DIAL);
            i.setData(Uri.parse("tel:" + phone));
            startActivity(i);
        });


        btnMessage.setOnClickListener(v -> {
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
            smsIntent.setData(Uri.parse("smsto:" + tvPhone.getText().toString()));
            smsIntent.putExtra("sms_body", "Welcome from GearUp!");
            startActivity(smsIntent);
        });

        btnLocation.setOnClickListener(v -> {
            String address = tvAddress.getText().toString();
            Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                // Fallback if Google Maps is not installed
                startActivity(new Intent(Intent.ACTION_VIEW, mapUri));
            }
        });


    }

    private void init(){
        tvName = findViewById(R.id.tvCustomerName);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);
        tvVehicleName = findViewById(R.id.tvVehicleName);
        tvVehicleType = findViewById(R.id.tvVehicleType);
        tvServiceType = findViewById(R.id.tvServiceType);

        btnCall = findViewById(R.id.btnCall);
        btnMessage = findViewById(R.id.btnMessage);
        btnLocation=findViewById(R.id.btnLocation);
    }
}