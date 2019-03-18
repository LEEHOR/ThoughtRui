package com.coahr.thoughtrui.mvp.view.startProject.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.coahr.thoughtrui.mvp.view.startProject.PagerFragment_a;

import java.util.List;


/**
 * Created by Leehor
 * on 2018/11/15
 * on 21:18
 */
public class StartProjectAdapter extends FragmentPagerAdapter {

  private int size;
  private String ht_ProjectId; //服务器端Id
    private String name_project; //项目名
    private List<String> id_list;
    public StartProjectAdapter(FragmentManager fm, int size, String ht_ProjectId, String name_project, List<String> ht_list) {
        super(fm);
       this.size=size;
       this.ht_ProjectId=ht_ProjectId;
       this.name_project=name_project;
       this.id_list=ht_list;
    }


    @Override
    public Fragment getItem(int i) {

            return PagerFragment_a.newInstance(i,ht_ProjectId,size,name_project,id_list.get(i));
        }

    @Override
    public int getCount() {

            return size;
        }
}
