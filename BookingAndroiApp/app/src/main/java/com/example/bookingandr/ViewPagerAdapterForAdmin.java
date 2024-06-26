package com.example.bookingandr;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapterForAdmin extends FragmentStatePagerAdapter {
    public ViewPagerAdapterForAdmin(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new DashBoardAdminFragment() ;
            case 1:
                return new CrudRoomFragment();
            case 2:
                return new CrudTypeRoomFragment();
            case 3:
                return new CrudNewsFragment();

            default:
                return new DashBoardAdminFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Admin Dashboard";
            case 1:
                return "Manage Room";
            case 2:
                return "Manage Type room";
            case 3:
                return "Manage News";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
