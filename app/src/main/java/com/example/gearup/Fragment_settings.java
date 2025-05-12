package com.example.gearup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

public class Fragment_settings extends Fragment {

    private Switch switchTheme;
    private LinearLayout rateLayout;
    private LinearLayout clearCacheLayout;

    public Fragment_settings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        switchTheme = view.findViewById(R.id.switch_theme);
        rateLayout = view.findViewById(R.id.layout_rate);
        clearCacheLayout = view.findViewById(R.id.layout_clear_cache);

        SharedPreferences prefs = requireActivity().getSharedPreferences("AppSettingsPrefs", Context.MODE_PRIVATE);
        boolean isDark = prefs.getBoolean("DarkBackground", false);
        switchTheme.setChecked(isDark);
        applyBackground(view, isDark);

        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("DarkBackground", isChecked).apply();
            applyBackground(view, isChecked);
        });

        rateLayout.setOnClickListener(v -> {
            Uri uri = Uri.parse("market://details?id=" + requireActivity().getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        clearCacheLayout.setOnClickListener(v -> {
            try {
                String[] cacheDirs = requireContext().getCacheDir().list();
                if (cacheDirs != null) {
                    for (String fileName : cacheDirs) {
                        new java.io.File(requireContext().getCacheDir(), fileName).delete();
                    }
                }
                Toast.makeText(requireContext(), "Cache cleared", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(requireContext(), "Error clearing cache", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void applyBackground(View view, boolean isDark) {
        int color = isDark ? android.R.color.black : android.R.color.white;
        view.setBackgroundColor(getResources().getColor(color));
    }
}
