package com.coahr.thoughtrui.mvp.presenter;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.Utils.BaiDuLocation.BaiduLocationHelper;
import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.Fragment_Umeng_C;
import com.coahr.thoughtrui.mvp.constract.MainActivityC;
import com.coahr.thoughtrui.mvp.model.Bean.CensorBean;
import com.coahr.thoughtrui.mvp.model.Bean.NotificationBean;
import com.coahr.thoughtrui.mvp.model.Fragment_Umeng_M;
import com.coahr.thoughtrui.mvp.model.MainActivityM;
import com.coahr.thoughtrui.mvp.view.MainActivity;
import com.coahr.thoughtrui.mvp.view.UMPush.Fragment_Umeng;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 17:50
 */
public class Fragment_UmengP extends BasePresenter<Fragment_Umeng_C.View,Fragment_Umeng_C.Model> implements Fragment_Umeng_C.Presenter
{
    @Inject
    public Fragment_UmengP(Fragment_Umeng mview, Fragment_Umeng_M mModel) {
        super(mview, mModel);
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
    public void getNotification_DbSuccess(NotificationBean notificationBean) {
        if (getView() != null) {
            getView().getNotification_DbSuccess(notificationBean);
        }
    }

    @Override
    public void getNotification_DbFailure(String failure) {
        if (getView() != null) {
            getView().getNotification_DbFailure(failure);
        }
    }
}
