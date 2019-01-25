package com.coahr.thoughtrui.mvp.presenter;

import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.AttendanceFC_h;
import com.coahr.thoughtrui.mvp.model.AttendanceFM_h;
import com.coahr.thoughtrui.mvp.model.Bean.Attendance;
import com.coahr.thoughtrui.mvp.model.Bean.AttendanceHistory;
import com.coahr.thoughtrui.mvp.view.attence.AttendanceFragment_h;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 12:56
 */
public class AttendanceFP_h extends BasePresenter<AttendanceFC_h.View,AttendanceFC_h.Model> implements AttendanceFC_h.Presenter {

    @Inject
    public AttendanceFP_h(AttendanceFragment_h mview, AttendanceFM_h mModel) {
        super(mview, mModel);
    }

    @Override
    public void getAttendanceHistory(Map<String, Object> map) {
        if (mModle != null) {
            mModle.getAttendanceHistory(map);
        }
    }

    @Override
    public void getAttendanceHistorySuccess(AttendanceHistory history) {
        if (getView() != null) {
            getView().getAttendanceHistorySuccess(history);
        }
    }

    @Override
    public void getAttendanceHistoryFailure(String failure,int code) {
        if (getView() != null) {
            getView().getAttendanceHistoryFailure(failure,code);
        }
    }

}
