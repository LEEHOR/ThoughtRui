package com.coahr.thoughtrui.mvp.presenter;

import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.ReviewPagerFragment_C;
import com.coahr.thoughtrui.mvp.model.Bean.CensorBean;
import com.coahr.thoughtrui.mvp.model.ReviewPagerFragment_M;
import com.coahr.thoughtrui.mvp.view.reviewed.ReviewPager;

import java.util.Map;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/7
 * 描述：
 */
public class ReviewPagerFragment_P extends BasePresenter<ReviewPagerFragment_C.View,ReviewPagerFragment_C.Model> implements ReviewPagerFragment_C.Presenter {
    @Inject
    public ReviewPagerFragment_P(ReviewPager mview, ReviewPagerFragment_M mModel) {
        super(mview, mModel);
    }

    @Override
    public void getCensorList(Map<String, Object> map) {
        if (mModle != null) {
            mModle.getCensorList(map);
        }
    }

    @Override
    public void getCensorListSuccess(CensorBean censorBean) {
        if (getView() != null) {
            getView().getCensorListSuccess(censorBean);
        }
    }

    @Override
    public void getCensorListFailure(String failure) {
        if (getView() != null) {
            getView().getCensorListFailure(failure);
        }
    }
}
