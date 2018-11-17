package com.coahr.thoughtrui.mvp.presenter;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.AttendanceFC_k;
import com.coahr.thoughtrui.mvp.model.AttendanceFM_k;
import com.coahr.thoughtrui.mvp.model.Bean.AttendRemark;
import com.coahr.thoughtrui.mvp.model.Bean.Attendance;
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
    public void startLocation() {
        if (mModle != null) {
            mModle.startLocation();
        }
    }

    @Override
    public void onLocationSuccess(BDLocation location) {
        if (getView() != null) {
            getView().onLocationSuccess(location);
        }
    }

    @Override
    public void onLocationFailure(int failure) {
        if (getView() != null) {
            getView().onLocationFailure(failure);
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
    public void getMainDataFailure(String failure) {
        if (getView() != null) {
            getView().getMainDataFailure(failure);
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
    public void sendRemarkFailure(String failure) {
        if (getView() != null) {
            getView().sendRemarkFailure(failure);
        }
    }
}
