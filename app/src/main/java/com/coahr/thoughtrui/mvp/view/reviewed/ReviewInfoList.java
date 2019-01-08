package com.coahr.thoughtrui.mvp.view.reviewed;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.ReviewInfoList_C;
import com.coahr.thoughtrui.mvp.presenter.ReviewInfoList_P;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/7
 * 描述：
 */
public class ReviewInfoList extends BaseFragment<ReviewInfoList_C.Presenter> implements ReviewInfoList_C.View{
   @Inject
    ReviewInfoList_P p;
    @Override
    public ReviewInfoList_C.Presenter getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_pager;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
