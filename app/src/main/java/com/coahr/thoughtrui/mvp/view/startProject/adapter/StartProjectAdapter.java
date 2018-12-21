package com.coahr.thoughtrui.mvp.view.startProject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.view.startProject.PagerFragment_a;
import com.socks.library.KLog;

import java.util.ArrayList;


/**
 * Created by Leehor
 * on 2018/11/15
 * on 21:18
 */
public class StartProjectAdapter extends FragmentPagerAdapter {

  private int size;
  private String DbProjectId;  //本地数据库id
  private String ht_ProjectId; //服务器端Id
    public StartProjectAdapter(FragmentManager fm,int size,String DbProjectId,String ht_ProjectId) {
        super(fm);
       this.size=size;
       this.DbProjectId=DbProjectId;
       this.ht_ProjectId=ht_ProjectId;
    }


    @Override
    public Fragment getItem(int i) {

            return PagerFragment_a.newInstance(i,DbProjectId,ht_ProjectId,size);
        }

    @Override
    public int getCount() {

            return size;
        }
}
