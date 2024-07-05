package com.example.bookingandr;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new RoomFragment();
            case 1:
                return new HistoryFragment();
            case 2:
                return new SwapRoomFragment();
            case 3:
                return new NewsFragment();
            case 4:
                return new ProfileFragment();

            default:
                return new RoomFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Room";
            case 1:
                return "History";
            case 2:
                return "Swap room";
            case 3:
                return "News";
            case 4:
                return "Profile";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
