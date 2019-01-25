package com.coahr.thoughtrui.mvp.presenter;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.Utils.BaiDuLocation.BaiduLocationHelper;
import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.AttendanceFC_k;
import com.coahr.thoughtrui.mvp.model.AttendanceFM_k;
import com.coahr.thoughtrui.mvp.model.Bean.AttendRemark;
import com.coahr.thoughtrui.mvp.model.Bean.Attendance;
import com.coahr.thoughtrui.mvp.model.Bean.PushAttendanceCard;
import com.coahr.thoughtrui.mvp.view.attence.AttendanceFragment_k;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 12:56
 */
public class AttendanceFP_k extends BasePresenter<AttendanceFC_k.View,AttendanceFC_k.Model> implements AttendanceFC_k.Presenter {

    @Inject
    public AttendanceFP_k(AttendanceFragment_k mview, AttendanceFM_k mModel) {
        super(mview, mModel);
    }

    @Override
    public void startLocations( int type) {
        if (mModle != null) {
            mModle.startLocations(type);
        }
    }


    @Override
    public void getMainData(Map<String,Object> map) {
        if (mModle != null) {
            mModle.getMainData(map);
        }
    }

    @Override
    public void getMainDataSuccess(Attendance attendance) {
        if (getView() != null) {
            getView().getMainDataSuccess(attendance);
        }
    }

    @Override
    public void getMainDataFailure(String failure,int code) {
        if (getView() != null) {
            getView().getMainDataFailure(failure,code);
        }
    }

    @Override
    public void sendRemark(Map map) {
        if (mModle != null) {
            mModle.sendRemark(map);
        }
    }

    @Override
    public void sendRemarkSuccess(AttendRemark attendRemark) {
        if (getView() != null) {
            getView().sendRemarkSuccess(attendRemark);
        }
    }

    @Override
    public void sendRemarkFailure(String failure,int code) {
        if (getView() != null) {
            getView().sendRemarkFailure(failure,code);
        }
    }

    @Override
    public void LocationContinuouslySuccess(BDLocation location, BaiduLocationHelper baiduLocationHelper) {
        if (getView() != null) {
            getView().LocationContinuouslySuccess(location,baiduLocationHelper);
        }
    }

    @Override
    public void LocationContinuouslyFailure(int failure,BaiduLocationHelper baiduLocationHelper) {
        if (getView() != null) {
            getView().LocationContinuouslyFailure(failure,baiduLocationHelper);
        }
    }

    @Override
    public void getPushCard(Map<String, Object> map) {
        if (mModle != null) {
            mModle.getPushCard(map);
        }
    }

    @Override
    public void getPushSuccess(PushAttendanceCard pushAttendanceCard) {
        if (getView() != null) {
            getView().getPushSuccess(pushAttendanceCard);
        }
    }

    @Override
    public void getPushFail(String failure,int code) {
        if (getView() != null) {
            getView().getPushFail(failure,code);
        }
    }
}
