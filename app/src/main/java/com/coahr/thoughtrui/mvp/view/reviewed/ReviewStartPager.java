package com.coahr.thoughtrui.mvp.view.reviewed;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.model.Bean.CensorInfoList;
import com.coahr.thoughtrui.mvp.view.reviewed.adapter.ReviewStartPagerAdapter;
import com.coahr.thoughtrui.widgets.CustomScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/7
 * 描述：修改答案ViewPager
 */
public class ReviewStartPager extends BaseFragment {
    @BindView(R.id.review_start_viewpager)
    CustomScrollViewPager review_start_viewpager;
    private ArrayList<String> beans;
    private int position;
    private String projectId;
    private ReviewStartPagerAdapter adapter;

    public static ReviewStartPager newInstance(ArrayList<String> beans, int position,String projectId) {
        ReviewStartPager reviewStartPager=new ReviewStartPager();
        Bundle bundle=new Bundle();
        bundle.putStringArrayList("beans",beans);
        bundle.putInt("position",position);
        bundle.putString("projectId",projectId);
        reviewStartPager.setArguments(bundle);
        return  reviewStartPager;
    }

    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }


    @Override
    public int bindLayout() {
        return R.layout.fragment_review_startproject;
    }

    @Override
    public void initView() {
        review_start_viewpager.setScrollable(false);
        if (getArguments() != null) {
            beans = getArguments().getStringArrayList("beans");
            position = getArguments().getInt("position");
            projectId = getArguments().getString("projectId");
        }
    }

    @Override
    public void initData() {
        adapter=new ReviewStartPagerAdapter(getChildFragmentManager(),beans,beans.size());
        review_start_viewpager.setAdapter(adapter);
        review_start_viewpager.setCurrentItem(position);
    }
}
