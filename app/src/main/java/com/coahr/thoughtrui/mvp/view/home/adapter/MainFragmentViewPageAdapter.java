package com.coahr.thoughtrui.mvp.view.home.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.coahr.thoughtrui.mvp.view.home.MyTabFragment;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 17:41
 */
public class MainFragmentViewPageAdapter extends FragmentPagerAdapter {
    private String[] title = {"新项目", "已完成","未完成","全部"};
    public MainFragmentViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return MyTabFragment.newInstance(i);
    }

    @Override
    public int getCount() {
        return title.length;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
