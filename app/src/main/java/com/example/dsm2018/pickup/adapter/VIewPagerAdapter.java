package com.example.dsm2018.pickup.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dsm2018.pickup.fragment.CreatePartyFragment;
import com.example.dsm2018.pickup.fragment.PartyListFragment;
import com.example.dsm2018.pickup.fragment.PartyLogFragment;

public class VIewPagerAdapter extends FragmentStatePagerAdapter {

    int tabCount;

    public VIewPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new CreatePartyFragment();
            case 1:
                return new PartyListFragment();
            case 2:
                return new PartyLogFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
