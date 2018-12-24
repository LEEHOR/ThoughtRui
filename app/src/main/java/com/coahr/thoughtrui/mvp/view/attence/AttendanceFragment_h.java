package com.coahr.thoughtrui.mvp.view.attence;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.util.TouchEventUtil;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.DensityUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.Base.BaseSupportActivity;
import com.coahr.thoughtrui.mvp.constract.AttendanceFC_h;
import com.coahr.thoughtrui.mvp.model.Bean.Attendance;
import com.coahr.thoughtrui.mvp.model.Bean.AttendanceHistory;
import com.coahr.thoughtrui.mvp.model.Bean.Event_Attend;
import com.coahr.thoughtrui.mvp.presenter.AttendanceFP_h;
import com.coahr.thoughtrui.mvp.view.attence.adapter.AttendanceHistoryAdapter;
import com.coahr.thoughtrui.mvp.view.decoration.SpacesItemDecoration;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 13:22
 */
public class AttendanceFragment_h extends BaseChildFragment<AttendanceFC_h.Presenter> implements AttendanceFC_h.View {
    @Inject
    AttendanceFP_h p;
    @BindView(R.id.history_recyclerView)
    RecyclerView history_recyclerView;
    private AttendanceHistoryAdapter attendanceHistoryAdapter;
    private LinearLayoutManager linearLayoutManager;
    private String projectId;

    public static AttendanceFragment_h newInstance() {
        AttendanceFragment_h attendanceFragment_h =new AttendanceFragment_h();
        return attendanceFragment_h;
    }


    @Override
    public AttendanceFC_h.Presenter getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_attendance_h;
    }

    @Override
    public void initView() {
        attendanceHistoryAdapter = new AttendanceHistoryAdapter();
        linearLayoutManager = new LinearLayoutManager(BaseApplication.mContext);
        history_recyclerView.setLayoutManager(linearLayoutManager);
        history_recyclerView.setAdapter(attendanceHistoryAdapter);
        history_recyclerView.addItemDecoration(new SpacesItemDecoration(0,DensityUtils.dp2px(BaseApplication.mContext,5)),getResources().getColor(R.color.material_grey_400));

        for (int i = 0; i < history_recyclerView.getItemDecorationCount(); i++) {
            if (i!=0){
                history_recyclerView.removeItemDecorationAt(i);
            }
        }
    }

    @Override
    public void initData() {
        getData();
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
        projectId = attendance.getData().getProjectId();
        getHistory(projectId);
    }

    @Override
    public void getMainDataFailure(String failure) {
        ToastUtils.showLong(failure);
    }

    @Override
    public void getAttendanceHistorySuccess(AttendanceHistory history) {
        KLog.d("开始了1");


        attendanceHistoryAdapter.setNewData(history.getData().getAttendanceList());
    }

    @Override
    public void getAttendanceHistoryFailure(String failure) {
        ToastUtils.showLong(failure);
    }

    //网络请求
    private void getData(){
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", Constants.ht_ProjectId);
        map.put("sessionId",Constants.sessionId);
        p.getMainData(map);
    }

    private void getHistory(String projectId){
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("sessionId",Constants.sessionId);
        p.getAttendanceHistory(map);
    }
}
