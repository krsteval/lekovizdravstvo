package com.tadi.lekovizdravstvomk.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tadi.lekovizdravstvomk.fragments.Details_Tab1;
import com.tadi.lekovizdravstvomk.fragments.Details_Tab2;
import com.tadi.lekovizdravstvomk.fragments.Details_Tab3;
import com.tadi.lekovizdravstvomk.model.Drug;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Drug data;

    public ViewPagerAdapter(FragmentManager fm, int NumOfTabs, Drug data) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return Details_Tab1.newInstance(data);
            case 1:
//                Details_Tab2 tab2 = new Details_Tab2();
                return Details_Tab2.newInstance(data);
            case 2:
//                Details_Tab3 tab3 = new Details_Tab3();
                return Details_Tab3.newInstance(data);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}