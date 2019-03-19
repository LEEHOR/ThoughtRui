package com.coahr.thoughtrui.mvp.presenter;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.Utils.BaiDuLocation.BaiduLocationHelper;
import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.MainActivityC;
import com.coahr.thoughtrui.mvp.model.MainActivityM;
import com.coahr.thoughtrui.mvp.view.MainActivity;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 17:50
 */
public class MainActivityP extends BasePresenter<MainActivityC.View,MainActivityC.Model> implements MainActivityC.Presenter
{
    @Inject
    public MainActivityP(MainActivity mview, MainActivityM mModel) {
        super(mview, mModel);
    }

    @Override
    public void getLocation(int type) {
        if (mModle != null) {
            mModle.getLocation(type);
        }
    }

    @Override
    public void getLocationSuccess(BDLocation location, BaiduLocationHelper baiduLocationHelper) {
        if (getView() != null) {
            getView().getLocationSuccess(location, baiduLocationHelper);
        }
    }

    @Override
    public void getLocationFailure(int failure, BaiduLocationHelper baiduLocationHelper) {
        if (getView() != null) {
            getView().getLocationFailure(failure, baiduLocationHelper);
        }
    }

    @Override
    public void sendRtsl(Map<String, Object> map) {
        if (mModle != null) {
            mModle.sendRtsl(map);
        }
    }

    @Override
    public void sendRtslSuccess(String success) {
        if (getView() != null) {
            getView().sendRtslSuccess(success);
        }
    }

    @Override
    public void sendRtslFail(String fail) {
        if (getView() != null) {
            getView().sendRtslFail(fail);
        }
    }
}
