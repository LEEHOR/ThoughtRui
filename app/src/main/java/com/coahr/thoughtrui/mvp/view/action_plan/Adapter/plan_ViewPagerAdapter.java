package com.coahr.thoughtrui.mvp.view.action_plan.Adapter;

import android.view.View;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/4/15
 * 描述：
 */
public class plan_ViewPagerAdapter extends FragmentPagerAdapter {
    private String [] tittle={BaseApplication.mContext.getResources().getString(R.string.plan_viewpager_tittle_1)
    ,BaseApplication.mContext.getResources().getString(R.string.plan_viewpager_tittle_2)
    ,BaseApplication.mContext.getResources().getString(R.string.plan_viewpager_tittle_3)};
    public plan_ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return tittle.length;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tittle[position];
    }
}
