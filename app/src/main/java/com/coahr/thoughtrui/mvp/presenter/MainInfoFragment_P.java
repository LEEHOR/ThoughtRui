package com.coahr.thoughtrui.mvp.presenter;

import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.MainInfoFragment_C;
import com.coahr.thoughtrui.mvp.model.Bean.CensorBean;
import com.coahr.thoughtrui.mvp.model.Bean.NotificationBean;
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

    @Override
    public void getNotification_net(Map<String, Object> map) {
        if (mModle != null) {
            mModle.getNotification_net(map);
        }
    }

    @Override
    public void getNotification_netSuccess(CensorBean censorBean) {
        if (getView() != null) {
            getView().getNotification_netSuccess(censorBean);
        }
    }

    @Override
    public void getNotification_netFailure(String failure) {
        if (getView() != null) {
            getView().getNotification_netFailure(failure);
        }
    }

    @Override
    public void getNotification_Db(String sessionId) {
        if (mModle != null) {
            mModle.getNotification_Db(sessionId);
        }
    }

    @Override
    public void getNotification_DbSuccess(NotificationBean notificationBean, int notificationNum) {
        if (getView() != null) {
            getView().getNotification_DbSuccess(notificationBean, notificationNum);
        }
    }

    @Override
    public void getNotification_DbFailure(String failure) {
        if (getView() != null) {
            getView().getNotification_DbFailure(failure);
        }
    }
}
