package com.coahr.thoughtrui.mvp.constract;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.Utils.BaiDuLocation.BaiduLocationHelper;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.AttendRemark;
import com.coahr.thoughtrui.mvp.model.Bean.Attendance;
import com.coahr.thoughtrui.mvp.model.Bean.PushAttendanceCard;

import java.util.Map;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 11:08
 */
public interface AttendanceFC_k {
    interface View extends BaseContract.View {


        void getMainDataSuccess(Attendance attendance);

        void getMainDataFailure(String failure);

        void sendRemarkSuccess(AttendRemark attendRemark);

        void sendRemarkFailure(String failure);

        void LocationContinuouslySuccess(BDLocation location, BaiduLocationHelper baiduLocationHelper);

        void LocationContinuouslyFailure(int failure,BaiduLocationHelper baiduLocationHelper);

        void getPushSuccess(PushAttendanceCard pushAttendanceCard);

        void getPushFail(String failure );

    }

    interface Presenter extends BaseContract.Presenter {

        void startLocations(int type);

        void getMainData(Map<String,Object> map);

        void getMainDataSuccess(Attendance attendance);

        void getMainDataFailure(String failure);

        void sendRemark(Map<String,Object> map);

        void sendRemarkSuccess(AttendRemark attendRemark);

        void sendRemarkFailure(String failure);

        void LocationContinuouslySuccess(BDLocation location, BaiduLocationHelper baiduLocationHelper);

        void LocationContinuouslyFailure(int failure,BaiduLocationHelper baiduLocationHelper);

        void getPushCard(Map<String,Object> map);

        void getPushSuccess(PushAttendanceCard pushAttendanceCard);

        void getPushFail(String failure );

    }

    interface Model extends BaseContract.Model {

        void getPushCard(Map<String,Object> map);

        void startLocations(int type);

        void getMainData(Map<String,Object> map);

        void sendRemark(Map<String,Object> map);

    }
}
