package com.coahr.thoughtrui.mvp.constract;

import com.amap.api.location.AMapLocation;
import com.coahr.thoughtrui.Utils.BaiDuLocation.GaodeMapLocationHelper;
import com.coahr.thoughtrui.mvp.Base.BaseContract;

import java.util.Map;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 17:49
 */
public interface MainActivityC {

    interface View extends BaseContract.View {

        void getLocationSuccess(AMapLocation location, GaodeMapLocationHelper gaodeMapLocationHelper);

        void getLocationFailure(int failure, GaodeMapLocationHelper gaodeMapLocationHelper);

        void sendRtslSuccess(String success,int result);

        void sendRtslFail(String fail,int result);

    }

    interface Presenter extends BaseContract.Presenter {

        void getLocation(int type);

        void getLocationSuccess(AMapLocation location, GaodeMapLocationHelper gaodeMapLocationHelper);

        void getLocationFailure(int failure, GaodeMapLocationHelper gaodeMapLocationHelper);

        void sendRtsl(Map<String,Object> map);

        void sendRtslSuccess(String success,int result);

        void sendRtslFail(String fail,int result);

    }

    interface Model extends BaseContract.Model {
        void getLocation(int type);
        void sendRtsl(Map<String,Object> map);
    }
}
