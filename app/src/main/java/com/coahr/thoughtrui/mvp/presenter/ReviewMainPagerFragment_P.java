package com.coahr.thoughtrui.mvp.presenter;

import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.ReviewMainPagerFragment_C;
import com.coahr.thoughtrui.mvp.model.Bean.CensorBean;
import com.coahr.thoughtrui.mvp.model.ReviewMainPagerFragment_M;
import com.coahr.thoughtrui.mvp.view.reviewed.ReviewMainPager;

import java.util.Map;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/7
 * 描述：
 */
public class ReviewMainPagerFragment_P extends BasePresenter<ReviewMainPagerFragment_C.View, ReviewMainPagerFragment_C.Model> implements ReviewMainPagerFragment_C.Presenter {
    @Inject
    public ReviewMainPagerFragment_P(ReviewMainPager mview, ReviewMainPagerFragment_M mModel) {
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
    public void getCensorListFailure(String failure,int code) {
        if (getView() != null) {
            getView().getCensorListFailure(failure,code);
        }
    }
}
