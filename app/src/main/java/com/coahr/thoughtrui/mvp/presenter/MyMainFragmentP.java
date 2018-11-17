package com.coahr.thoughtrui.mvp.presenter;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.MyMainFragmentC;
import com.coahr.thoughtrui.mvp.constract.MyTabFragmentC;
import com.coahr.thoughtrui.mvp.model.Bean.HomeDataList;
import com.coahr.thoughtrui.mvp.model.MyMainFragmentM;
import com.coahr.thoughtrui.mvp.model.MyTabFragmentM;
import com.coahr.thoughtrui.mvp.view.home.MainFragment;
import com.coahr.thoughtrui.mvp.view.home.MyTabFragment;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 15:51
 */
public class MyMainFragmentP extends BasePresenter<MyMainFragmentC.View,MyMainFragmentC.Model> implements MyMainFragmentC.Presenter {

  @Inject
    public MyMainFragmentP(MainFragment mview, MyMainFragmentM mModel) {
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
    public void getHomeData(Map map) {
        if (mModle != null) {
            mModle.getHomeData(map);
        }
    }

    @Override
    public void getHomeDataSuccess(HomeDataList homeDataList) {
        if (getView() != null) {
            getView().getHomeDataSuccess(homeDataList);
        }
    }

    @Override
    public void getHomeDataFailure(String fail) {
        if (getView() != null) {
            getView().getHomeDataFailure(fail);
        }
    }

    @Override
    public void getHomeMore(Map map) {
        if (mModle != null) {
            mModle.getHomeMore(map);
        }
    }

    @Override
    public void getHomeMoreSuccess(HomeDataList homeDataList) {
        if (getView() != null) {
            getView().getHomeMoreSuccess(homeDataList);
        }
    }

    @Override
    public void getHomeMoreFailure(String fail) {
        if (getView() != null) {
            getView().getHomeMoreFailure(fail);
        }
    }
}
