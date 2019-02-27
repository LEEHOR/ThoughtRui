package com.coahr.thoughtrui.mvp.presenter;

import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.ReviewSubjectList_C;
import com.coahr.thoughtrui.mvp.model.Bean.CensorInfoList;
import com.coahr.thoughtrui.mvp.model.ReviewSubjectList_M;
import com.coahr.thoughtrui.mvp.view.reviewed.ReviewSubjectList;

import java.util.Map;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/7
 * 描述：
 */
public class ReviewSubjectList_P extends BasePresenter<ReviewSubjectList_C.View, ReviewSubjectList_C.Model> implements ReviewSubjectList_C.Presenter {
    @Inject
    public ReviewSubjectList_P(ReviewSubjectList mview, ReviewSubjectList_M mModel) {
        super(mview, mModel);
    }

    @Override
    public void getCensorInfoList(Map<String, Object> map) {
        if (mModle != null) {
            mModle.getCensorInfoList(map);
        }
    }

    @Override
    public void getCensorInfoListSuccess(CensorInfoList censorInfoList) {
        if (getView() != null) {
            getView().getCensorInfoListSuccess(censorInfoList);
        }
    }

    @Override
    public void getCensorInfoListFailure(String failure) {
        if (getView() != null) {
            getView().getCensorInfoListFailure(failure);

        }
    }
}
