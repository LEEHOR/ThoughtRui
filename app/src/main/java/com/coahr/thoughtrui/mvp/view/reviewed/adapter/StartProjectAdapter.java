package com.coahr.thoughtrui.mvp.view.reviewed.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/7
 * 描述：
 */
public class StartProjectAdapter extends FragmentPagerAdapter {
    private List<String> stringList;
    private int sizes;
    public StartProjectAdapter(FragmentManager fm, int size, List<String> list) {
        super(fm);
        this.stringList=list;
        this.sizes=size;
    }

    @Override
    public Fragment getItem(int i) {
        return null;
    }

    @Override
    public int getCount() {
        return sizes;
    }
}
