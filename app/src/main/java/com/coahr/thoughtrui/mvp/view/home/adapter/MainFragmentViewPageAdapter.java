package com.coahr.thoughtrui.mvp.view.home.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.coahr.thoughtrui.mvp.view.home.MyTabFragment;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 17:41
 */
public class MainFragmentViewPageAdapter extends FragmentPagerAdapter {
    //private String[] title = {"新项目", "已完成","未完成","全部"};
    private String[] title = { "全部","未完成","已完成"};
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
