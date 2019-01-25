package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.AttendanceFC_h;
import com.coahr.thoughtrui.mvp.model.Bean.Attendance;
import com.coahr.thoughtrui.mvp.model.Bean.AttendanceHistory;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 12:54
 */
public class AttendanceFM_h extends BaseModel<AttendanceFC_h.Presenter> implements AttendanceFC_h.Model {
  @Inject
    public AttendanceFM_h() {
        super();
    }


  @Override
  public void getAttendanceHistory(Map<String, Object> map) {
    mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<AttendanceHistory>(getApiService().AttendanceHistory(map)))
            .subscribeWith(new SimpleDisposableSubscriber<AttendanceHistory>() {
      @Override
      public void _onNext(AttendanceHistory attendanceHistory) {
        if (getPresenter() != null) {
          if (attendanceHistory.getResult()==1) {
            getPresenter().getAttendanceHistorySuccess(attendanceHistory);
          }else {
            getPresenter().getAttendanceHistoryFailure(attendanceHistory.getMsg(),attendanceHistory.getResult());
          }
        }
      }
    }));
  }
}
