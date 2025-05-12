package com.example.gearup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class home extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private TextView customerCountText;
    private TextView serviceCountText;

    private DatabaseReference customerRef;
    private DatabaseReference serviceRef;

    public home() {}

    public static home newInstance(String param1, String param2) {
        home fragment = new home();
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

        customerRef = FirebaseDatabase.getInstance().getReference("customers");
        serviceRef = FirebaseDatabase.getInstance().getReference("services"); // NEW
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        customerCountText = view.findViewById(R.id.customer_count_text);
        serviceCountText = view.findViewById(R.id.service_count_text); // NEW

        getCustomerCount();
        getServiceCount(); // NEW

        return view;
    }

    private void getCustomerCount() {
        customerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long customerCount = snapshot.getChildrenCount();
                customerCountText.setText(String.valueOf(customerCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Customer count error: " + error.getMessage());
            }
        });
    }

    private void getServiceCount() {
        serviceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long totalServices = 0;

                // Loop through each date node
                for (DataSnapshot dateSnapshot : snapshot.getChildren()) {
                    totalServices += dateSnapshot.getChildrenCount();
                }

                serviceCountText.setText(String.valueOf(totalServices));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Service count error: " + error.getMessage());
            }
        });
    }
}
