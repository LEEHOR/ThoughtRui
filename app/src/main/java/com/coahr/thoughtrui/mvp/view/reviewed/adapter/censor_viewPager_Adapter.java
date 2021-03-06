package com.coahr.thoughtrui.mvp.view.reviewed.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.view.reviewed.ReviewMainPager;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/7
 * 描述：
 */
public class censor_viewPager_Adapter extends FragmentPagerAdapter {
    private String[] title = {BaseApplication.mContext.getResources().getString(R.string.review_fragment_2), BaseApplication.mContext.getResources().getString(R.string.review_fragment_1)};

    public censor_viewPager_Adapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int i) {
        return ReviewMainPager.newInstance(i);
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
