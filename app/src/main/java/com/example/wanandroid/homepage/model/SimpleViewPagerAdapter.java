package com.example.wanandroid.homepage.model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Golden on 2018/2/27.
 */

public class SimpleViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList;

    public SimpleViewPagerAdapter(FragmentManager fm, List<Fragment>fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList==null?null:mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList==null?0:mFragmentList.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
