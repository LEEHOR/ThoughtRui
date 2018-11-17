package com.coahr.thoughtrui.mvp.presenter;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.UploadC;
import com.coahr.thoughtrui.mvp.model.UploadM;
import com.coahr.thoughtrui.mvp.view.upload.UploadFragment;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 17:10
 */
public class UploadP extends BasePresenter<UploadC.View,UploadC.Model> implements UploadC.Presenter {
    @Inject
    public UploadP(UploadFragment mview, UploadM mModel) {
        super(mview, mModel);
    }

    @Override
    public void startLocation() {
        if (mModle != null) {
            mModle.startLocation();
        }
    }

    @Override
    public void onLocationSuccess(BDLocation location) {
        if (getView() != null) {
            getView().onLocationSuccess(location);
        }
    }

    @Override
    public void onLocationFailure(int failure) {
        if (getView() != null) {
            getView().onLocationFailure(failure);
        }
    }

    @Override
    public void getUploadData(Map map) {
        if (mModle != null) {
            mModle.getHomeData(map);
        }
    }

    @Override
    public void getUploadDataSuccess() {
        if (getView() != null) {
            getView().getHomeDataSuccess();
        }
    }

    @Override
    public void getUploadDataFailure(String fail) {
        if (getView() != null) {
            getView().getHomeDataFailure(fail);
        }
    }
}
