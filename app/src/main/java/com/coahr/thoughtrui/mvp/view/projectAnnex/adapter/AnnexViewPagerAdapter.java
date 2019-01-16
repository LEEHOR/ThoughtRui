package com.coahr.thoughtrui.mvp.view.projectAnnex.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.coahr.thoughtrui.mvp.view.projectAnnex.FragmentAnnex;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/16
 * 描述：
 */
public class AnnexViewPagerAdapter extends FragmentPagerAdapter {
    private String[] title = {"全部", "图片","录音"};
    private String projectId;
    public AnnexViewPagerAdapter(FragmentManager fm,String projectIds) {
        super(fm);
        this.projectId=projectIds;
    }

    @Override
    public Fragment getItem(int i) {
        return FragmentAnnex.newInstance(i,projectId);
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
