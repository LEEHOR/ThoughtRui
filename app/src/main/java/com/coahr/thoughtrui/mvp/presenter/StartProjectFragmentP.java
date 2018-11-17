package com.coahr.thoughtrui.mvp.presenter;

import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.StartProjectFragmentC;
import com.coahr.thoughtrui.mvp.model.Bean.QuestionBean;
import com.coahr.thoughtrui.mvp.model.StartProjectFragmentM;
import com.coahr.thoughtrui.mvp.view.startProject.StartProjectFragment;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 17:00
 */
public class StartProjectFragmentP extends BasePresenter<StartProjectFragmentC.View,StartProjectFragmentC.Model> implements StartProjectFragmentC.Presenter {
    @Inject
    public StartProjectFragmentP(StartProjectFragment mview, StartProjectFragmentM mModel) {
        super(mview, mModel);
    }

    @Override
    public void getMainData(Map map) {
        if (mModle != null) {
            mModle.getMainData(map);
        }
    }

    @Override
    public void getMainDataSuccess(QuestionBean questionBean) {
        if (getView() != null) {
            getView().getMainDataSuccess(questionBean);
        }
    }

    @Override
    public void getMainDataFailure(String failure) {
        if (getView() != null) {
            getView().getMainDataFailure(failure);
        }
    }

    @Override
    public void getOfflineDate(String dbProjectId,String ht_projectId) {
        if (mModle != null) {
            mModle.getOfflineDate(dbProjectId,ht_projectId);
        }
    }

    @Override
    public void getOfflineSuccess(int size,String dbProjectId,String ht_projectId) {
        if (getView() != null) {
            getView().getOfflineSuccess(size, dbProjectId,ht_projectId);
        }
    }

    @Override
    public void getOfflineFailure(int failure) {
        if (getView() != null) {
            getView().getOfflineFailure(failure);
        }
    }
}
