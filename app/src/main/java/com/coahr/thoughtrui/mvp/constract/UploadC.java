package com.coahr.thoughtrui.mvp.constract;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.mvp.Base.BaseContract;

import java.util.Map;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 17:07
 */
public interface UploadC  {
    interface View extends BaseContract.View {

        void onLocationSuccess(BDLocation location);

        void onLocationFailure(int failure);

        void getHomeDataSuccess();

        void getHomeDataFailure(String fail);

    }

    interface Presenter extends BaseContract.Presenter {

        void startLocation();

        void onLocationSuccess(BDLocation location);

        void onLocationFailure(int failure);

        void getUploadData(Map map);

        void getUploadDataSuccess();

        void getUploadDataFailure(String fail);

    }

    interface Model extends BaseContract.Model {

        void startLocation();

        void getHomeData(Map map);
        ;

    }
}
