package com.mycompany.clockplus;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by arsteinm on 5/27/16.
 */
public class SwipeAdapter extends FragmentPagerAdapter {
    public SwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new AlarmFragment();
            case 1:
                return new Timer();
            case 2:
                return new StopWatchFragment();
            case 3:
                return new WorldClockFragment();
            default:
                break;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
