package com.coahr.thoughtrui.mvp.view.reviewed.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.coahr.thoughtrui.mvp.view.reviewed.ReviewPager;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/7
 * 描述：
 */
public class censor_viewPager_Adapter extends FragmentPagerAdapter {
    private String[] title = {"未通过", "已通过"};

    public censor_viewPager_Adapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int i) {
        return ReviewPager.newInstance(i);
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
