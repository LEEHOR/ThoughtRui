package com.coahr.thoughtrui.mvp.view.attence;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 13:22
 */
public class AttendanceFragment_h extends BaseChildFragment<AttendanceFC_h.Presenter> implements AttendanceFC_h.View, OnDateSetListener {
    @Inject
    AttendanceFP_h p;
    @BindView(R.id.start_part)
    View include_start;
    @BindView(R.id.end_part)
    View include_end;
    @BindView(R.id.tv_progress)
    TextView tv_progress;
    @BindView(R.id.tv_select_time)
    TextView tv_select_time;
    @BindView(R.id.iv_select_time)
    ImageView iv_select_time;
    private String projectId;

    private TextView attendance_time_k;
    private ImageView iv_attendance_tag_start;
    private TextView tv_attendance_address_k;

    private TextView attendance_time_e;
    private ImageView iv_attendance_tag_end;
    private TextView tv_attendance_address_e;
    private TimePickerDialog datePickerDialog;
    //

    public static AttendanceFragment_h newInstance() {
        AttendanceFragment_h attendanceFragment_h = new AttendanceFragment_h();
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
        initTimePickerDialog();
        //开始
        attendance_time_k = include_start.findViewById(R.id.attendance_time_k);
        iv_attendance_tag_start = include_start.findViewById(R.id.iv_attendance_tag_start);
        tv_attendance_address_k = include_start.findViewById(R.id.tv_attendance_address_k);
        //结束
        attendance_time_e = include_end.findViewById(R.id.attendance_time_e);
        iv_attendance_tag_end = include_end.findViewById(R.id.iv_attendance_tag_end);
        tv_attendance_address_e = include_end.findViewById(R.id.tv_attendance_address_e);

        //把备注和更新打卡隐藏
        LinearLayout viewById = include_end.findViewById(R.id.line_update);
        viewById.setVisibility(View.GONE);
        include_end.findViewById(R.id.tv_bzhu).setVisibility(View.GONE);
        iv_select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show(getFragmentManager(), "datePickerDialog");
            }
        });
    }

    @Override
    public void initData() {
        getHistory(Constants.ht_ProjectId);
    }

    @Override
    public void getMainDataSuccess(Attendance attendance) {

    }

    @Override
    public void getMainDataFailure(String failure) {
        ToastUtils.showLong(failure);
    }

    @Override
    public void getAttendanceHistorySuccess(AttendanceHistory history) {



    }

    @Override
    public void getAttendanceHistoryFailure(String failure) {
        ToastUtils.showLong(failure);
    }

    //网络请求
    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", Constants.ht_ProjectId);
        map.put("sessionId", Constants.sessionId);
        // p.getMainData(map);
    }

    //打卡历史
    private void getHistory(String projectId) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("sessionId", Constants.sessionId);
        p.getAttendanceHistory(map);
    }

    private void initTimePickerDialog() {
        KLog.e(TAG, "---initTimePickerDialog---pre---");
        long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
        long fourHour = 60 * 60 * 4L * 1000;
        datePickerDialog = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("预约日期")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setHourText("时")
                .setMinuteText("分")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis() - tenYears)
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.material_blue_600))
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.black))
                .setWheelItemTextSize(12)
                .build();

    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {

    }
}
