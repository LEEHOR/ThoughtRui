package com.coahr.thoughtrui.mvp.presenter;

import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.ReviewInfoList_C;
import com.coahr.thoughtrui.mvp.model.ReviewInfoList_M;
import com.coahr.thoughtrui.mvp.view.reviewed.ReviewInfoList;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/7
 * 描述：
 */
public class ReviewInfoList_P extends BasePresenter<ReviewInfoList_C.View,ReviewInfoList_C.Model> implements ReviewInfoList_C.Presenter {
    @Inject
    public ReviewInfoList_P(ReviewInfoList mview, ReviewInfoList_M mModel) {
        super(mview, mModel);
    }
}
