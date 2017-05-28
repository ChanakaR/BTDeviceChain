package com.wbn.uom.btdevicechain.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by inocer on 5/27/17.
 * Purpose : pager adapter for tabbed view in device select screen
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public TabPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                SearchDeviceFragment tab1 = new SearchDeviceFragment();
                return tab1;

            case 1:
                PairedDeviceFragment tab2 = new PairedDeviceFragment();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
