package com.coahr.thoughtrui.mvp.view.reviewed.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.coahr.thoughtrui.mvp.view.reviewed.ReViewStart;

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
    private String ht_projectId;
    private String db_projectId;
    public ReviewStartPagerAdapter(FragmentManager fm, List<String> list,int size,String db_ProjectId,String ht_ProjectId) {
        super(fm);
        this.stringList=list;
        this.size=size;
        this.db_projectId=db_ProjectId;
        this.ht_projectId=ht_ProjectId;
    }

    @Override
    public Fragment getItem(int i) {
        return ReViewStart.newInstance(i,db_projectId,ht_projectId,size,stringList.get(i));
    }

    @Override
    public int getCount() {
        return size;
    }
}
