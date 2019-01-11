package com.coahr.thoughtrui.mvp.view.reviewed.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/10
 * 描述：
 */
public class ReviewStartPagerAdapter extends FragmentPagerAdapter {
    private List<String> stringList;
    private int size;
    public ReviewStartPagerAdapter(FragmentManager fm, List<String> list,int size) {
        super(fm);
        this.stringList=list;
        this.size=size;
    }

    @Override
    public Fragment getItem(int i) {
        return null;
    }

    @Override
    public int getCount() {
        return size;
    }
}
