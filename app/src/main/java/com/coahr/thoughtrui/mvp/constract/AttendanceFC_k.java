package com.coahr.thoughtrui.mvp.constract;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.AttendRemark;
import com.coahr.thoughtrui.mvp.model.Bean.Attendance;

import java.util.Map;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 11:08
 */
public interface AttendanceFC_k {
    interface View extends BaseContract.View {

        void onLocationSuccess(BDLocation location);

        void onLocationFailure(int failure);

        void getMainDataSuccess(Attendance attendance);

        void getMainDataFailure(String failure);

        void sendRemarkSuccess(AttendRemark attendRemark);

        void sendRemarkFailure(String failure);

    }

    interface Presenter extends BaseContract.Presenter {

        void startLocation();

        void onLocationSuccess(BDLocation location);

        void onLocationFailure(int failure);

        void getMainData(Map<String,Object> map);

        void getMainDataSuccess(Attendance attendance);

        void getMainDataFailure(String failure);

        void sendRemark(Map<String,Object> map);

        void sendRemarkSuccess(AttendRemark attendRemark);

        void sendRemarkFailure(String failure);
    }

    interface Model extends BaseContract.Model {

        void startLocation();

        void getMainData(Map<String,Object> map);

        void sendRemark(Map<String,Object> map);
    }
}
