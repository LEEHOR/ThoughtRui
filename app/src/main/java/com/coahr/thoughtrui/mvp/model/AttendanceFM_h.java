package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.AttendanceFC_h;
import com.coahr.thoughtrui.mvp.model.Bean.Attendance;

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
    public void getMainData(Map map) {
      mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<Attendance>(getApiService().getAttendanceInfo(map))).subscribeWith(new SimpleDisposableSubscriber<Attendance>() {
        @Override
        public void _onNext(Attendance attendance) {
          if (getPresenter() != null) {
            if (attendance.getResult()==1) {
              getPresenter().getMainDataSuccess(attendance);
            }else {
              getPresenter().getMainDataFailure(attendance.getMsg());
            }
          }
        }
      }));
    }

}
