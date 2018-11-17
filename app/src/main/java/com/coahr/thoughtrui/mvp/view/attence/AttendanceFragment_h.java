package com.coahr.thoughtrui.mvp.view.attence;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.constract.AttendanceFC_h;
import com.coahr.thoughtrui.mvp.model.Bean.Attendance;
import com.coahr.thoughtrui.mvp.model.Bean.Event_Attend;
import com.coahr.thoughtrui.mvp.presenter.AttendanceFP_h;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 13:22
 */
public class AttendanceFragment_h extends BaseChildFragment<AttendanceFC_h.Presenter> implements AttendanceFC_h.View {
    @Inject
    AttendanceFP_h p;

    public static AttendanceFragment_h newInstance() {
        AttendanceFragment_h attendanceFragment_h =new AttendanceFragment_h();
        return attendanceFragment_h;
    }

    @Override
    public BaseContract.Presenter getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_attendance_h;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

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
    //网络请求
    private void getData(){
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", Constants.projectId);
        map.put("sessionId",Constants.sessionId);
        p.getMainData(map);
    }
}
