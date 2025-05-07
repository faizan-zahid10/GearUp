package com.example.gearup;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ViewCustomers extends AppCompatActivity {

    RecyclerView rvCustomers;
    FloatingActionButton fabAddCustomer;
    CustomerAdapter adapter;

    private List<CustomerData> customerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_customers);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        customerList.add(new CustomerData(1,"Faizan Zahid", "+92 300 1234567", "123 Street, Lahore", "CD70", "Bike", "Oil Change"));
        customerList.add(new CustomerData(2,"Hamid Khan", "+92 301 1112233", "456 Avenue, Karachi", "Swift", "Car", "Wheel Alignment"));
        adapter.notifyDataSetChanged();

//        for clicking on add button
        fabAddCustomer.setOnClickListener(v -> {
            // TODO: Open AddCustomer screen
            Toast.makeText(this, "Add Customer Clicked", Toast.LENGTH_SHORT).show();
        });
    }

    private void init(){
        rvCustomers=findViewById(R.id.rvCustomers);
        fabAddCustomer=findViewById(R.id.fabAddCustomer);
        customerList = new ArrayList<>();

//        adapter=new CustomerAdapter(this,customerList);
        rvCustomers.setAdapter(adapter);
    }
}