package com.example.ethanman04.multiplayer;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MultiPagerAdapter extends FragmentStatePagerAdapter {
    public MultiPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new GameFragment();
            case 1: return new ChatFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
