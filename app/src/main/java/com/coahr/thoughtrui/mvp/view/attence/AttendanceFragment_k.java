package com.coahr.thoughtrui.mvp.view.attence;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.constract.AttendanceFC_k;
import com.coahr.thoughtrui.mvp.model.Bean.AttendRemark;
import com.coahr.thoughtrui.mvp.model.Bean.Attendance;
import com.coahr.thoughtrui.mvp.model.Bean.Event_Attend;
import com.coahr.thoughtrui.mvp.presenter.AttendanceFP_k;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 12:59
 */
public class AttendanceFragment_k extends BaseChildFragment<AttendanceFC_k.Presenter> implements AttendanceFC_k.View {
    @Inject
    AttendanceFP_k p;

    public static AttendanceFragment_k newInstance() {
        AttendanceFragment_k attendanceFragment_k =new AttendanceFragment_k();
        return attendanceFragment_k;
    }

    @Override
    public BaseContract.Presenter getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_attendance_k;
    }



    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        getData();
    }

    @Override
    public void onLocationSuccess(BDLocation location) {

    }

    @Override
    public void onLocationFailure(int failure) {

    }

    @Override
    public void getMainDataSuccess(Attendance attendance) {
        //把数据发送到AttendRootFragment用于显示
          EventBus.getDefault().postSticky(new Event_Attend(attendance.getData().getPname()
                  ,attendance.getData().getStartTime()
                  ,attendance.getData().getEndTime()
                  ,attendance.getData().getCname()
                  ,attendance.getData().getCode()
                  ,attendance.getData().getAreaAddress()));
    }

    @Override
    public void getMainDataFailure(String failure) {

    }

    @Override
    public void sendRemarkSuccess(AttendRemark attendRemark) {

    }

    @Override
    public void sendRemarkFailure(String failure) {

    }
    //网络请求
    private void getData(){
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", Constants.ht_ProjectId);
        map.put("sessionId",Constants.sessionId);
        p.getMainData(map);
    }
}
