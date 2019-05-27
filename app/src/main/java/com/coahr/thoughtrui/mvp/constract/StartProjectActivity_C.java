package com.coahr.thoughtrui.mvp.constract;

import com.amap.api.location.AMapLocation;
import com.coahr.thoughtrui.Utils.BaiDuLocation.GaodeMapLocationHelper;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.QuestionBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 11:08
 */
public interface StartProjectActivity_C {
    interface View extends BaseContract.View {

        void getLocationSuccess(AMapLocation location, GaodeMapLocationHelper gaodeMapLocationHelper);

        void getLocationFailure(int failure, GaodeMapLocationHelper gaodeMapLocationHelper);

        void getMainDataSuccess(QuestionBean questionBean);

        void getMainDataFailure(String failure,int code);

        void getOfflineSuccess(int size, String ht_projectId, List<String> ht_id);

        void getOfflineFailure(int failure);



        void sendRtslSuccess(String success,int result);

        void sendRtslFail(String fail,int result);

    }

    interface Presenter extends BaseContract.Presenter {

        void getLocation(int type);

        void getLocationSuccess(AMapLocation location, GaodeMapLocationHelper gaodeMapLocationHelper);

        void getLocationFailure(int failure, GaodeMapLocationHelper gaodeMapLocationHelper);

        void getMainData(Map map);

        void getMainDataSuccess(QuestionBean questionBean);

        void getMainDataFailure(String failure,int code);

        void getOfflineDate(String ht_projectId);

        void getOfflineSuccess(int size,String ht_projectId, List<String> ht_id);

        void getOfflineFailure(int failure);

        void sendRtsl(Map<String,Object> map);

        void sendRtslSuccess(String success,int result);

        void sendRtslFail(String fail,int result);


    }

    interface Model extends BaseContract.Model {

        void getLocation(int type);

        void getMainData(Map map);


        void getOfflineDate( String ht_projectId);

        void sendRtsl(Map<String,Object> map);

    }
}
