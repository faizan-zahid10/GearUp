package com.example.gearup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Customers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Customers extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView rvCustomers;
    FloatingActionButton fabAddCustomer;
    ProgressBar progressBar;
    CustomerAdapter adapter;
    private List<CustomerData> customerList;

    private ActivityResultLauncher<Intent> addCustomerLauncher;

    private DatabaseReference databaseReference;  // Firebase reference



    public Customers() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Customers.
     */
    // TODO: Rename and change types and number of parameters
    public static Customers newInstance(String param1, String param2) {
        Customers fragment = new Customers();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addCustomerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Toast.makeText(getActivity(), "New Customer added", Toast.LENGTH_SHORT).show();

                        // Extract data
//                        Intent data = result.getData();
//                        String name = data.getStringExtra("name");
//                        String phone = data.getStringExtra("phone");
//                        String address = data.getStringExtra("address");
//                        String vehicle = data.getStringExtra("vehicle");
//                        String vehicleType = data.getStringExtra("vehicleType");
//                        String service = data.getStringExtra("service");
//
//                        // Add to list and notify adapter
//                        int newId = customerList.size() + 1;
//                        CustomerData newCustomer = new CustomerData(name, phone, address, vehicle, vehicleType, service);
//                        customerList.add(newCustomer);
//                        Toast.makeText(getActivity(), "New Customer added", Toast.LENGTH_SHORT).show();
//                        adapter.notifyItemInserted(customerList.size() - 1);
                    }
                }
        );



        // Initialize views
        rvCustomers = view.findViewById(R.id.rvCustomers);
        fabAddCustomer = view.findViewById(R.id.fabAddCustomer);
        progressBar = view.findViewById(R.id.progressBar);
        customerList = new ArrayList<>();

//        adapter = new CustomerAdapter(getContext(), customerList);
//        rvCustomers.setLayoutManager(new LinearLayoutManager(getContext()));
//        rvCustomers.setAdapter(adapter);

        // Adding sample customer data to list
//        customerList.add(new CustomerData("Faizan Zahid", "+92 300 1234567", "123 Street, Lahore", "CD70", "Bike", "Oil Change"));
//        customerList.add(new CustomerData("Hamid Khan", "+92 301 1112233", "456 Avenue, Karachi", "Swift", "Car", "Wheel Alignment"));

        // Setting up Firebase reference to "customers" node
        databaseReference = FirebaseDatabase.getInstance().getReference("customers");

        adapter = new CustomerAdapter(getContext(), customerList, customer -> {
            Intent intent = new Intent(getContext(), CustomerDetails.class);
            intent.putExtra("name", customer.getName());
            intent.putExtra("phone", customer.getPhone());
            intent.putExtra("address", customer.getAddress());
            intent.putExtra("vehicle", customer.getVehicleName());
            intent.putExtra("vehicleType", customer.getVehicleType());
            intent.putExtra("service", customer.getServiceType());


            startActivity(intent);
        });



        rvCustomers.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCustomers.setAdapter(adapter);

        // Fetch customer data from Firebase
        fetchCustomerData();

        // Notify adapter about the data change
//        adapter.notifyDataSetChanged();

        // Set up FloatingActionButton click listener
        fabAddCustomer.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), AddCustomer.class);
            addCustomerLauncher.launch(i);
        });
    }

    private void fetchCustomerData(){
        progressBar.setVisibility(View.VISIBLE);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                customerList.clear();  // Clear existing data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CustomerData customer = snapshot.getValue(CustomerData.class);
                    if (customer != null) {
                        customer.setId(snapshot.getKey());  // Set Firebase ID
                        customerList.add(customer);  // Add customer to list
                    }
                }
                adapter.notifyDataSetChanged();  // Notify adapter of data change
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}