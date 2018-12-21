package com.coahr.thoughtrui.mvp.view.attence;

import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.TimeUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.AttendanceFC_k;
import com.coahr.thoughtrui.mvp.model.Bean.AttendRemark;
import com.coahr.thoughtrui.mvp.model.Bean.Attendance;
import com.coahr.thoughtrui.mvp.model.Bean.Event_Attend;
import com.coahr.thoughtrui.mvp.presenter.AttendanceFP_k;
import com.coahr.thoughtrui.widgets.HttpUtils.HttpUtilListener;
import com.coahr.thoughtrui.widgets.HttpUtils.HttpUtils;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import okhttp3.ResponseBody;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 12:59
 * 考勤页面
 */
public class AttendanceFragment_k extends BaseFragment<AttendanceFC_k.Presenter> implements AttendanceFC_k.View {
    @Inject
    AttendanceFP_k p;
    @BindView(R.id.tv_startOrEnd_time)
    TextView tv_startOrEnd_time;
    @BindView(R.id.re_push_clock)
    RelativeLayout re_push_clock; //打卡
    @BindView(R.id.tv_push_text)
    TextView tv_push_text; //打卡文字
    @BindView(R.id.tv_time_clock)
    TextView tv_time_clock; //时间
    @BindView(R.id.location_message)
    TextView location_message;//定位地址
    @BindView(R.id.relocation)
    TextView relocation;//重新定位
    @BindView(R.id.include_start)
    View include_start; //开始打卡区
    @BindView(R.id.include_end)
    View include_end;
    private ImageView start_tag;//开始打卡状态图标
    private TextView start_time_d; //打卡时间
    private TextView tv_start_location; //打卡位置
    private TextView end_tv_time_d; //结束打卡时间
    private ImageView end_tag; //结束状态
    private TextView tv_end_location; //结束状态位置
    private int closeStatus;
    private String k_id;
    private Attendance.DataBean.AttendanceBean k_bean;
    private int startTimeStatus;
    private int endLocationStatus;

    private int pushType; //打卡状态（开始/结束）

    private int isOnCircle;

    public static AttendanceFragment_k newInstance() {
        AttendanceFragment_k attendanceFragment_k =new AttendanceFragment_k();
        return attendanceFragment_k;
    }


    @Override
    public AttendanceFC_k.Presenter getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_attendance_k;
    }



    @Override
    public void initView() {
        start_time_d = include_start.findViewById(R.id.attendance_time_k);
        start_tag = include_start.findViewById(R.id.iv_attendance_tag_start);
        tv_start_location = include_start.findViewById(R.id.tv_attendance_address_k);
        end_tv_time_d = include_end.findViewById(R.id.attendance_time_e);
        end_tag = include_end.findViewById(R.id.iv_attendance_tag_end);
        tv_end_location = include_end.findViewById(R.id.tv_attendance_address_e);

    }

    @Override
    public void initData() {
        p.startLocation();
    }

    @Override
    public void onLocationSuccess(BDLocation location) {
        Constants.Longitude = location.getLongitude();
        Constants.Latitude = location.getLatitude();
        getData();
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
        long startTime = attendance.getData().getStartTime();
        long endTime = attendance.getData().getEndTime();
        String stringDate_start = TimeUtils.getStringDate_start(startTime);
        String stringDate_end = TimeUtils.getStringDate_end(endTime);
        tv_startOrEnd_time.setText(stringDate_start + "-" + stringDate_end);
        closeStatus = attendance.getData().getCloseStatus();
        k_bean = attendance.getData().getAttendance();
        if (k_bean != null) {
            k_id = k_bean.getId();
            if (k_id != null && !k_id.equals("")) {
                include_start.setVisibility(View.VISIBLE);
                long inTime = k_bean.getInTime();
                if (inTime !=0){  //进店时间
                    tv_push_text.setText("结束打卡");
                    String stringDate_start1 = TimeUtils.getStringDate_start(inTime);
                    start_time_d.setText(stringDate_start1);
                    startTimeStatus = k_bean.getStartTimeStatus();
                    if(startTimeStatus == 1){
                        start_tag.setImageDrawable(getResources().getDrawable(R.mipmap.kaoqinz));
                    }
                    if (startTimeStatus==-1){
                        start_tag.setImageDrawable(getResources().getDrawable(R.mipmap.kaoqinyc));
                    }
                    double inLat = k_bean.getInLat();
                    double inLng = k_bean.getInLng();
                    if (inLat !=0 && inLng !=0){
                        HttpUtils.getAddress(String.valueOf(inLat), String.valueOf(inLng), new HttpUtilListener() {
                            @Override
                            public void getAddressSuccess(String s) {
                                KLog.d("返回数据",s);

                            }

                            @Override
                            public void getAddressFailure(String e) {
                                KLog.d("返回数据",e);
                            }
                        });
                    }

                } else {
                    start_tag.setImageDrawable(getResources().getDrawable(R.mipmap.kaoqinq));
                }

                if (k_bean.getOutTime() !=0){
                    include_end.setVisibility(View.VISIBLE);
                    endLocationStatus = k_bean.getEndLocationStatus();
                    if(endLocationStatus == 1){
                        end_tag.setImageDrawable(getResources().getDrawable(R.mipmap.kaoqinz));
                    }
                    if (endLocationStatus ==-1){
                        end_tag.setImageDrawable(getResources().getDrawable(R.mipmap.kaoqinyc));
                    }
                    if (k_bean.getOutLat() !=0)
                    if (inTime==0){
                        end_tag.setImageDrawable(getResources().getDrawable(R.mipmap.kaoqinq));
                    }
                }



            }
        } else {
            tv_push_text.setText("开始打卡");
            include_start.setVisibility(View.GONE);
            include_end.setVisibility(View.GONE);
        }

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
