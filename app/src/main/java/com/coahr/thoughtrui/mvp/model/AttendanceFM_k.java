package com.coahr.thoughtrui.mvp.model;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.Utils.BaiDuLocation.BaiduLocationHelper;
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.AttendanceFC_k;
import com.coahr.thoughtrui.mvp.model.Bean.AttendRemark;
import com.coahr.thoughtrui.mvp.model.Bean.Attendance;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 12:54
 */
public class AttendanceFM_k extends BaseModel<AttendanceFC_k.Presenter> implements AttendanceFC_k.Model {
  @Inject
    public AttendanceFM_k() {
        super();
    }
  @Inject
  BaiduLocationHelper baiduLocationHelper;
  private BaiduLocationHelper.OnLocationCallBack onLocationCallBack = new BaiduLocationHelper.OnLocationCallBack() {
    @Override
    public void onLocationSuccess(BDLocation location) {
      if (getPresenter() != null) {
        getPresenter().onLocationSuccess(location);
        baiduLocationHelper.stopLocation();
      }
    }

    @Override
    public void onLocationFailure(int locType) {
      if (getPresenter() != null) {
        getPresenter().onLocationFailure(locType);
      }
    }
  };
    @Override
    public void startLocation() {
      initlocation();
      baiduLocationHelper.startLocation();
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

    @Override
    public void sendRemark(Map map) {
      mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<AttendRemark>(getApiService().getRemark(map))).subscribeWith(new SimpleDisposableSubscriber<AttendRemark>() {
        @Override
        public void _onNext(AttendRemark attendRemark) {
          if (getPresenter() != null) {
            if (attendRemark.getResult()==1) {
              getPresenter().sendRemarkSuccess(attendRemark);
            }else {
              getPresenter().sendRemarkFailure(attendRemark.getMsg());
            }
          }
        }
      }));
    }
  private void initlocation() {
    baiduLocationHelper.registerLocationCallback(onLocationCallBack);
  }
  @Override
  public void detachPresenter() {
    super.detachPresenter();
    baiduLocationHelper.unRegisterLocationCallback(onLocationCallBack);
  }
}
