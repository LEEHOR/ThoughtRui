package com.coahr.thoughtrui.mvp.constract;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.Utils.BaiDuLocation.BaiduLocationHelper;
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

        void getLocationSuccess(BDLocation location, BaiduLocationHelper baiduLocationHelper);

        void getLocationFailure(int failure, BaiduLocationHelper baiduLocationHelper);

        void getMainDataSuccess(QuestionBean questionBean);

        void getMainDataFailure(String failure,int code);

        void getOfflineSuccess(int size, String ht_projectId, List<String> ht_id);

        void getOfflineFailure(int failure);

    }

    interface Presenter extends BaseContract.Presenter {

        void getLocation(int type);

        void getLocationSuccess(BDLocation location, BaiduLocationHelper baiduLocationHelper);

        void getLocationFailure(int failure, BaiduLocationHelper baiduLocationHelper);

        void getMainData(Map map);

        void getMainDataSuccess(QuestionBean questionBean);

        void getMainDataFailure(String failure,int code);

        void getOfflineDate(String ht_projectId);

        void getOfflineSuccess(int size,String ht_projectId, List<String> ht_id);

        void getOfflineFailure(int failure);


    }

    interface Model extends BaseContract.Model {

        void getLocation(int type);

        void getMainData(Map map);


        void getOfflineDate( String ht_projectId);

    }
}
