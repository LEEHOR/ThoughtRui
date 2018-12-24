package com.coahr.thoughtrui.mvp.model;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.Utils.BaiDuLocation.BaiduLocationHelper;
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.MyMainFragmentC;
import com.coahr.thoughtrui.mvp.constract.MyTabFragmentC;
import com.coahr.thoughtrui.mvp.model.Bean.HomeDataList;
import com.socks.library.KLog;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 15:49
 */
public class MyMainFragmentM extends BaseModel<MyMainFragmentC.Presenter> implements MyMainFragmentC.Model {

    @Inject
    public MyMainFragmentM() {
        super();
    }
private int type;
    private int locationType = 1;
    @Inject
    BaiduLocationHelper baiduLocationHelper;

    private BaiduLocationHelper.OnLocationCallBack onLocationCallBack = new BaiduLocationHelper.OnLocationCallBack() {
        @Override
        public void onLocationSuccess(BDLocation location) {
            KLog.d("首页定位---========================"+type);
            if (locationType == type) {
                if (getPresenter() != null) {
                    getPresenter().onLocationSuccess(location);
                }
                type=0;
                baiduLocationHelper.stopLocation();
            }


        }

        @Override
        public void onLocationFailure(int locType) {
            if (locationType == type) {
                if (getPresenter() != null) {
                    getPresenter().onLocationFailure(locType);
                }
                type=0;
            }

        }
    };

    @Override
    public void startLocation(int type) {
        this.type=type;
        KLog.d("首页定位--------------------------------");
        initlocation();
        baiduLocationHelper.startLocation();
    }

    @Override
    public void getHomeData(Map map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<HomeDataList>(getApiService().getHomeData(map)))
                .subscribeWith(new SimpleDisposableSubscriber<HomeDataList>() {
                    @Override
                    public void _onNext(HomeDataList homeDataList) {
                        if (getPresenter() != null) {
                            if (homeDataList.getResult()== 1) {
                                getPresenter().getHomeDataSuccess(homeDataList);
                            } else {
                                getPresenter().getHomeDataFailure(homeDataList.getMsg());
                            }
                        }
                    }
                }));

    }

    @Override
    public void getHomeMore(Map map) {

    }

    private void initlocation() {
        baiduLocationHelper.registerLocationCallback(onLocationCallBack);
    }

    @Override
    public void detachPresenter() {
        super.detachPresenter();
        baiduLocationHelper.unRegisterLocationCallback(onLocationCallBack);
    }


}
