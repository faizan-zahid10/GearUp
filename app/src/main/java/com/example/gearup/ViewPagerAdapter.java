package com.example.gearup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new home();
            case 1:
                return new Customers();
            case 2:
                return new Services();
            case 3: //to be set to settings
                return new Fragment_settings();
            default:
                return new home();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
