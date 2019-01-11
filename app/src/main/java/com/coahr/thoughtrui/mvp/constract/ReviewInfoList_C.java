package com.coahr.thoughtrui.mvp.constract;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.CensorInfoList;

import java.util.Map;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 17:07
 */
public interface ReviewInfoList_C {
    interface View extends BaseContract.View {

        void  getCensorInfoListSuccess(CensorInfoList censorInfoList);
        void  getCensorInfoListFailure(String failure);

    }

    interface Presenter extends BaseContract.Presenter {
        void getCensorInfoList(Map<String,Object> map);
        void  getCensorInfoListSuccess(CensorInfoList censorInfoList);
        void  getCensorInfoListFailure(String failure);

    }

    interface Model extends BaseContract.Model {

        void getCensorInfoList(Map<String,Object> map);

    }
}
