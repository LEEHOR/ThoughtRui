package com.coahr.thoughtrui.mvp.view.reviewed.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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
    public ReviewStartPagerAdapter(FragmentManager fm,String db_ProjectId,String ht_ProjectId, List<String> list,int size) {
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
