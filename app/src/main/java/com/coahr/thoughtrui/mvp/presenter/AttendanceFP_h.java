package com.coahr.thoughtrui.mvp.presenter;

import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.AttendanceFC_h;
import com.coahr.thoughtrui.mvp.model.AttendanceFM_h;
import com.coahr.thoughtrui.mvp.model.Bean.Attendance;
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

}
