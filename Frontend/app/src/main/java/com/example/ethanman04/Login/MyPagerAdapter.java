package com.example.ethanman04.Login;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    public MyPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new LoginTabFragment();
            case 1: return new NewUserFragment();
        }
        return null;
    }
    @Override
    public int getCount() {
        return 2;
    }
    @Override    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Login";
            case 1: return "New User";
            default: return null;
        }
    }
}
