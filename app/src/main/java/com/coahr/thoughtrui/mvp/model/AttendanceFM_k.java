package com.coahr.thoughtrui.mvp.model;

import com.amap.api.location.AMapLocation;
import com.coahr.thoughtrui.Utils.BaiDuLocation.GaodeMapLocationHelper;
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.AttendanceFC_k;
import com.coahr.thoughtrui.mvp.model.Bean.AttendRemark;
import com.coahr.thoughtrui.mvp.model.Bean.Attendance;
import com.coahr.thoughtrui.mvp.model.Bean.PushAttendanceCard;

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

  private int  continuously=4;
  private int type;
  @Inject
  GaodeMapLocationHelper gaodeMapLocationHelper;

  private GaodeMapLocationHelper.OnLocationCallBack onLocationCallBack = new GaodeMapLocationHelper.OnLocationCallBack() {
    @Override
    public void onLocationSuccess(AMapLocation location) {
      //连续定位成功
      if (continuously == type){
        if (getPresenter() != null) {
          getPresenter().LocationContinuouslySuccess(location,gaodeMapLocationHelper);
        }
      }

    }

    @Override
    public void onLocationFailure(int locType) {
      //连续定位失败
      if (continuously == type){
        if (getPresenter() != null) {
          getPresenter().LocationContinuouslyFailure(locType,gaodeMapLocationHelper);
        }
      }
    }
  };

  @Override
  public void getPushCard(Map<String, Object> map) {
    mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<PushAttendanceCard>(getApiService().getPushAttendance(map)))
            .subscribeWith(new SimpleDisposableSubscriber<PushAttendanceCard>() {
      @Override
      public void _onNext(PushAttendanceCard pushAttendanceCard) {
        if (getPresenter() != null) {
          if (pushAttendanceCard.getResult()==1) {
            getPresenter().getPushSuccess(pushAttendanceCard);
          }else {
            getPresenter().getPushFail(pushAttendanceCard.getMsg(),pushAttendanceCard.getResult());
          }
        }
      }
    }));
  }

  @Override
    public void startLocations(int type) {
      this.type = type;
      initlocation();
    gaodeMapLocationHelper.startLocation();
    }

    @Override
    public void getMainData(Map<String,Object> map) {
      mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<Attendance>(getApiService().getAttendanceInfo(map))).subscribeWith(new SimpleDisposableSubscriber<Attendance>() {
        @Override
        public void _onNext(Attendance attendance) {
          if (getPresenter() != null) {
            if (attendance.getResult()==1) {
              getPresenter().getMainDataSuccess(attendance);
            }else {
              getPresenter().getMainDataFailure(attendance.getMsg(),attendance.getResult());
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
              getPresenter().sendRemarkFailure(attendRemark.getMsg(),attendRemark.getResult());
            }
          }
        }
      }));
    }
  private void initlocation() {
    gaodeMapLocationHelper.registerLocationCallback(onLocationCallBack);
  }
  @Override
  public void detachPresenter() {
    super.detachPresenter();
    gaodeMapLocationHelper.unRegisterLocationCallback(onLocationCallBack);
  }
}
