package com.coahr.thoughtrui.mvp.presenter;

import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.FragmentTopicsC;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectListBean;
import com.coahr.thoughtrui.mvp.model.FragmentTopicsM;
import com.coahr.thoughtrui.mvp.view.startProject.Fragment_Topics;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/12/27
 * on 15:13
 */
public class FragmentTopicsP extends BasePresenter<FragmentTopicsC.View,FragmentTopicsC.Model> implements FragmentTopicsC.Presenter {
    @Inject
    public FragmentTopicsP(Fragment_Topics mview, FragmentTopicsM mModel) {
        super(mview, mModel);
    }

    @Override
    public void getSubjectList(Map<String, Object> map) {
        if (mModle != null) {
            mModle.getSubjectList(map);
        }
    }

    @Override
    public void getSubjectListSuccess(SubjectListBean subjectListBean) {
        if (getView() != null) {
            getView().getSubjectListSuccess(subjectListBean);
        }
    }

    @Override
    public void getSubjectListFailure(String failure) {
        if (getView() != null) {
            getView().getSubjectListFailure(failure);
        }
    }
}
