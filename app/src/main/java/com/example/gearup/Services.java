package com.example.gearup;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gearup.models.ServiceModel;

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

    public Services() {
        // Required empty public constructor
    }

    public static Services newInstance(String param1, String param2) {
        Services fragment = new Services();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_services, container, false);

        selectedDate = Calendar.getInstance();
        tvSelectedDate = view.findViewById(R.id.tv_selected_date);
        ImageButton btnPrevDate = view.findViewById(R.id.btn_prev_date);
        ImageButton btnNextDate = view.findViewById(R.id.btn_next_date);
        recyclerView = view.findViewById(R.id.recycler_services);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ServiceAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        updateDateDisplay();
        loadServicesForDate(selectedDate);

        tvSelectedDate.setOnClickListener(v -> {
            int year = selectedDate.get(Calendar.YEAR);
            int month = selectedDate.get(Calendar.MONTH);
            int day = selectedDate.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (DatePicker view1, int selectedYear, int selectedMonth, int selectedDay) -> {
                        selectedDate.set(Calendar.YEAR, selectedYear);
                        selectedDate.set(Calendar.MONTH, selectedMonth);
                        selectedDate.set(Calendar.DAY_OF_MONTH, selectedDay);
                        updateDateDisplay();
                        loadServicesForDate(selectedDate);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

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

        return view;
    }

    private void updateDateDisplay() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        tvSelectedDate.setText(sdf.format(selectedDate.getTime()));
    }

    private void loadServicesForDate(Calendar date) {
        // This should be replaced by real filtering logic or database queries
        SimpleDateFormat sdfKey = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String key = sdfKey.format(date.getTime());

        List<ServiceModel> services = new ArrayList<>();

        // Simulate different services on different days
        switch (key) {
            case "20250510":
                services.add(new ServiceModel("Ali Khan", "03001234567", "ABC-123", "10:00 AM"));
                services.add(new ServiceModel("Sara Ahmed", "03111234567", "XYZ-987", "11:30 AM"));
                break;
            case "20250511":
                services.add(new ServiceModel("Usman Tariq", "03211234567", "DEF-456", "1:00 PM"));
                break;
            default:
                services.add(new ServiceModel("No bookings", "-", "-", "-"));
                break;
        }

        adapter.updateData(services);
    }
}
