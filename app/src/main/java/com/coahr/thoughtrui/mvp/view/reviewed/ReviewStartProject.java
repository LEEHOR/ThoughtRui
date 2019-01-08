package com.coahr.thoughtrui.mvp.view.reviewed;

import android.support.v4.view.ViewPager;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;

import butterknife.BindView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/7
 * 描述：
 */
public class ReviewStartProject extends BaseFragment {
    @BindView(R.id.review_start_viewpager)
    ViewPager review_start_viewpager;
    public static ReviewStartProject newInstance() {
        return new ReviewStartProject();
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

    }

    @Override
    public void initData() {

    }
}
