package com.coahr.thoughtrui.mvp.view.startProject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.socks.library.KLog;

import java.util.ArrayList;


/**
 * Created by Leehor
 * on 2018/11/15
 * on 21:18
 */
public class StartProjectAdapter extends FragmentPagerAdapter {

    ArrayList<BaseChildFragment> fragmentArrayList=new ArrayList<>();
    public StartProjectAdapter(FragmentManager fm,ArrayList<BaseChildFragment> fragmentArrayList) {
        super(fm);
        this.fragmentArrayList=fragmentArrayList;
    }


    @Override
    public Fragment getItem(int i) {
        if (fragmentArrayList ==null ){
            return null;
        }
        return fragmentArrayList.get(i);
    }

    @Override
    public int getCount() {
        if (fragmentArrayList == null ) {
            return 0;
        }
        KLog.d("个数",fragmentArrayList.size());
        return fragmentArrayList.size();
    }
}
