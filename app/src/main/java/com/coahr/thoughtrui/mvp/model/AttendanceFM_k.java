package com.coahr.thoughtrui.mvp.model;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.Utils.BaiDuLocation.BaiduLocationHelper;
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

  private int LocationType = 2;
  private int  continuously=4;
  private int type;
  @Inject
  BaiduLocationHelper baiduLocationHelper;

  private BaiduLocationHelper.OnLocationCallBack onLocationCallBack = new BaiduLocationHelper.OnLocationCallBack() {
    @Override
    public void onLocationSuccess(BDLocation location) {
      if (LocationType == type) {
        if (getPresenter() != null) {
          getPresenter().LocationSuccess(location);
          baiduLocationHelper.stopLocation();
        }
        type = 0;
      }
      //连续定位成功
      if (continuously == type){
        if (getPresenter() != null) {
          getPresenter().LocationContinuouslySuccess(location,baiduLocationHelper);
        }
      }

    }

    @Override
    public void onLocationFailure(int locType) {
      if (LocationType == type) {
        if (getPresenter() != null) {
          getPresenter().LocationFailure(locType);
        }
        type=0;
      }
      //连续定位失败
      if (continuously == type){
        if (getPresenter() != null) {
          getPresenter().LocationContinuouslyFailure(locType,baiduLocationHelper);
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
            getPresenter().getPushFail(pushAttendanceCard.getMsg());
          }
        }
      }
    }));
  }

  @Override
    public void startLocations(int type) {
      this.type = type;
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
