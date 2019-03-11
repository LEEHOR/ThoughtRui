package com.coahr.thoughtrui.mvp.presenter;

import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.MainInfoFragment_C;
import com.coahr.thoughtrui.mvp.model.MainInfoFragment_M;
import com.coahr.thoughtrui.mvp.view.home.MainInfoFragment;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 17:50
 */
public class MainInfoFragment_P extends BasePresenter<MainInfoFragment_C.View,MainInfoFragment_C.Model> implements MainInfoFragment_C.Presenter
{
    @Inject
    public MainInfoFragment_P(MainInfoFragment mview, MainInfoFragment_M mModel) {
        super(mview, mModel);
    }

    @Override
    public void getProject(String sessionId) {
        if (mModle != null) {
            mModle.getProject(sessionId);
        }
    }

    @Override
    public void getProjectSuccess() {
        if (getView() != null) {
            getView().getProjectSuccess();
        }
    }

    @Override
    public void getProjectFailure(String failure) {
        if (getView() != null) {
            getView().getProjectFailure(failure);
        }
    }
}
