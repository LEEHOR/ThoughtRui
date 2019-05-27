package com.coahr.thoughtrui.mvp.model;

import com.amap.api.location.AMapLocation;
import com.coahr.thoughtrui.Utils.BaiDuLocation.GaodeMapLocationHelper;
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.MainActivityC;
import com.coahr.thoughtrui.mvp.model.Bean.RTSL;


import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 17:53
 */
public class MainActivityM extends BaseModel<MainActivityC.Presenter> implements MainActivityC.Model {
    @Inject
    public MainActivityM() {
        super();
    }

    private int i=1;
    private int type_s;

    @Inject
    GaodeMapLocationHelper gaodeMapLocationHelper;

    private GaodeMapLocationHelper.OnLocationCallBack onLocationCallBack = new GaodeMapLocationHelper.OnLocationCallBack() {
        @Override
        public void onLocationSuccess(AMapLocation location) {
            //连续定位成功
            if (i == type_s){
                if (getPresenter() != null) {
                    getPresenter().getLocationSuccess(location,gaodeMapLocationHelper);
                }
            }

        }

        @Override
        public void onLocationFailure(int locType) {
            //连续定位失败
            if (i == type_s){
                if (getPresenter() != null) {
                    getPresenter().getLocationFailure(locType,gaodeMapLocationHelper);
                }
            }
        }
    };

    @Override
    public void getLocation(int type) {
       this.type_s=type;
        initlocation();
        gaodeMapLocationHelper.startLocation();
    }

    @Override
    public void sendRtsl(Map<String, Object> map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<RTSL>(getApiService().RTSL(map)))
                .subscribeWith(new SimpleDisposableSubscriber<RTSL>() {
                    @Override
                    public void _onNext(RTSL rtsl) {
                        if (getPresenter() != null) {
                            if (rtsl.getResult()==1){
                                getPresenter().sendRtslSuccess(rtsl.getMsg(),rtsl.getResult());
                            } else {
                                getPresenter().sendRtslFail(rtsl.getMsg(),rtsl.getResult());
                            }
                        }
                    }
        }));
    }

    private void initlocation() {
        gaodeMapLocationHelper.registerLocationCallback(onLocationCallBack);
    }
    @Override
    public void detachPresenter() {
        super.detachPresenter();
        gaodeMapLocationHelper.unRegisterLocationCallback(onLocationCallBack);
    }
}
