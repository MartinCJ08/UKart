/**
 * Created by Miguel Balderrama 11/10/2019
 * MyPagerAdapter.java
 */

package com.example.ukartapp.Adapters;

import com.example.ukartapp.Fragments.ConfFragment;
import com.example.ukartapp.Fragments.HomeFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public MyPagerAdapter(FragmentManager fm, int numberofTabs) {
        super(fm);
        this.numberOfTabs = numberofTabs;
    }

    //TODO: Complete this method with the created fragments
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new ConfFragment();
            case 2:
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.numberOfTabs;
    }
}
