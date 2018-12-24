package com.coahr.thoughtrui.mvp.view.attence;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.print.PrinterId;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.BaiDuLocation.BaiduLocationHelper;
import com.coahr.thoughtrui.Utils.TimeUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.AttendanceFC_k;
import com.coahr.thoughtrui.mvp.model.Bean.AttendRemark;
import com.coahr.thoughtrui.mvp.model.Bean.Attendance;
import com.coahr.thoughtrui.mvp.model.Bean.Event_Attend;
import com.coahr.thoughtrui.mvp.model.Bean.PushAttendanceCard;
import com.coahr.thoughtrui.mvp.presenter.AttendanceFP_k;
import com.coahr.thoughtrui.widgets.AltDialog.EvaluateInputDialogFragment;
import com.coahr.thoughtrui.widgets.HttpUtils.HttpUtilListener;
import com.coahr.thoughtrui.widgets.HttpUtils.HttpUtils;
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
 * on 12:59
 * 考勤页面
 */
public class AttendanceFragment_k extends BaseChildFragment<AttendanceFC_k.Presenter> implements AttendanceFC_k.View {
    @Inject
    AttendanceFP_k p;
    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    @BindView(R.id.end_line)
    LinearLayout end_line;  //下班卡
    @BindView(R.id.re_push_clock)
    RelativeLayout re_push_clock; //打卡
    @BindView(R.id.tv_push_text)
    TextView tv_push_text; //打卡文字
    @BindView(R.id.tv_time_clock)
    TextView tv_time_clock; //时间
    @BindView(R.id.send_remark)
    LinearLayout send_remark;
    @BindView(R.id.my_remarks)
    EditText my_remarks;
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
    private AppCompatDialogFragment dialogs;
    private Attendance.DataBean.AttendanceBean k_bean;
    private int startTimeStatus;
    private int endLocationStatus;

    private int pushType; //打卡状态（开始/结束；1：没有打卡，2：开始打卡，3：结束打卡）

    private boolean isOnCircle; //是否在打卡范围
    private int startLocationStatus;
    private int endTimeStatus;
    //门店所在的经纬度
    private double latitude;
    private double longitude;
    //进店打卡的经纬度
    private double inLat;
    private double inLng;
    //离店打卡的经纬度
    private double outLat;
    private double outLng;
    /**
     * 循环定位信息
     */
    private BaiduLocationHelper baiduLocationHelper;
    private static final int LOCATIONMESSAGE = 1;
    private double continueStla;
    private double continueStlo;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case LOCATIONMESSAGE:
                    //当前定位
                    LatLng LocationPoint = new LatLng(continueStla, continueStlo);
                    //门店定位
                    LatLng latLng = new LatLng(latitude, longitude);//假设公司坐标
                    double distance = DistanceUtil.getDistance(LocationPoint, latLng);
                    if (distance > 200) {
                        KLog.d("在范围1");
                        isOnCircle = false;
                        location_message.setTextColor(getResources().getColor(R.color.material_red_A700));
                        location_message.setText("不在考勤范围");
                    } else {
                        isOnCircle = true;
                        KLog.d("在范围21");
                        location_message.setTextColor(getResources().getColor(R.color.material_blue_700));
                        location_message.setText("已考勤范围");
                    }
            }
        }
    };


    /**
     * 设置系统时间
     */
    private Runnable run_time = new Runnable() {
        @Override
        public void run() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");// HH:mm:ss
            Date date = new Date(System.currentTimeMillis());//获取当前时间
            tv_time_clock.setText(simpleDateFormat.format(date)); //更新时间
            mHandler.postDelayed(run_time, 1000);
        }
    };

    //根据开始打卡经纬度获取具体地址
    private Runnable run_getStartAddress = new Runnable() {
        @Override
        public void run() {
            if (inLat != 0 && inLng != 0) {
                //根据经纬度获取地址
                HttpUtils.getAddress(String.valueOf(inLat), String.valueOf(inLng), new HttpUtilListener() {
                    @Override
                    public void getAddressSuccess(String s) {
                        KLog.d("返回数据", s);
                        tv_start_location.setText(s);
                    }

                    @Override
                    public void getAddressFailure(String e) {
                        KLog.d("返回数据", e);
                    }
                });
            }
        }
    };

    //根据借结束打卡经纬度获取具体地址
    private Runnable run_getEndAddress = new Runnable() {
        @Override
        public void run() {
            if (outLat != 0 && outLng != 0) {
                //根据经纬度获取地址
                HttpUtils.getAddress(String.valueOf(outLat), String.valueOf(outLng), new HttpUtilListener() {
                    @Override
                    public void getAddressSuccess(String s) {
                        KLog.d("返回数据", s);
                        tv_end_location.setText(s);
                    }

                    @Override
                    public void getAddressFailure(String e) {
                        KLog.d("返回数据", e);
                    }
                });
            }
        }
    };
    private String classId;
    private String dealerId;
    private String projectId;
    private EvaluateInputDialogFragment dialogFragment;


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
        dialogFragment = new EvaluateInputDialogFragment();
        dialogFragment.setOnInputCallback(new EvaluateInputDialogFragment.InputCallback() {
            @Override
            public void onInputSend(String input, AppCompatDialogFragment dialog) {
                dialogs=dialog;
                PushAttendanceRemark(input);
            }
        });
        start_time_d = include_start.findViewById(R.id.attendance_time_k);
        start_tag = include_start.findViewById(R.id.iv_attendance_tag_start);
        tv_start_location = include_start.findViewById(R.id.tv_attendance_address_k);

        end_tv_time_d = include_end.findViewById(R.id.attendance_time_e);
        end_tag = include_end.findViewById(R.id.iv_attendance_tag_end);
        tv_end_location = include_end.findViewById(R.id.tv_attendance_address_e);
        //重新定位
        relocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (baiduLocationHelper != null) {
                    baiduLocationHelper.stopLocation();
                    if (mHandler != null) {
                        mHandler.removeMessages(LOCATIONMESSAGE);
                        if (latitude != 0 && longitude != 0) {
                            p.startLocations(4);
                            mHandler.sendEmptyMessage(LOCATIONMESSAGE);
                        }
                    }
                }
            }
        });

        //开始打卡
        re_push_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (closeStatus == 0) {
                    if (!isOnCircle) {
                        new MaterialDialog.Builder(_mActivity)
                                .content("当前不在考勤范围是否打卡")
                                .title("提示")
                                .negativeText("取消")
                                .positiveText("确定")
                                .negativeColor(getResources().getColor(R.color.material_blue_700))
                                .positiveColor(getResources().getColor(R.color.material_red_A700))
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                    }
                                })
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        PushCard();
                                    }
                                }).build().show();
                    } else {
                        new MaterialDialog.Builder(_mActivity)
                                .content("确定打卡")
                                .title("提示")
                                .negativeText("取消")
                                .positiveText("确定")
                                .negativeColor(getResources().getColor(R.color.material_red_A700))
                                .positiveColor(getResources().getColor(R.color.material_blue_700))
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                    }
                                })
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        PushCard();
                                    }
                                }).build().show();
                    }
                } else {
                    new MaterialDialog.Builder(_mActivity)
                            .content("项目已结束无法打卡")
                            .title("提示")
                            .negativeText("取消")
                            .positiveText("确定")
                            .negativeColor(getResources().getColor(R.color.material_blue_700))
                            .positiveColor(getResources().getColor(R.color.material_red_A700))
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            })
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    PushCard();
                                }
                            }).build().show();
                }
            }
        });

        my_remarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFragment.show(getChildFragmentManager(),TAG);
            }
        });

    }

    @Override
    public void initData() {
        p.startLocations(2);
        mHandler.post(run_time); //开启时间线程
    }

    @Override
    public void LocationSuccess(BDLocation location) {
        Constants.Longitude = location.getLongitude();
        Constants.Latitude = location.getLatitude();
        KLog.d("打卡定位成功", location.getAddrStr());
        getData();

    }

    @Override
    public void LocationFailure(int failure) {

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
        closeStatus = attendance.getData().getCloseStatus();
        //获取门店的经纬度
        latitude = attendance.getData().getLatitude();
        longitude = attendance.getData().getLongitude();
        classId = attendance.getData().getClassId();
        dealerId = attendance.getData().getDealerId();
        projectId = attendance.getData().getProjectId();
        String str = attendance.getData().getCname();
        if (str.equals("自由班次")){
            tv_start_time.setText(str);
            tv_end_time.setText(str);
        } else {
            String[] split = str.split("-");
            if (split.length > 1) {
                tv_start_time.setText(split[0]);
                tv_end_time.setText(split[1]);
            }
        }

        //开启连续定位
        p.startLocations(4);
        k_bean = attendance.getData().getAttendance();
        if (k_bean != null) {
            k_id = k_bean.getId();
            if (k_id != null && !k_id.equals("")) {
                include_start.setVisibility(View.VISIBLE);
                long inTime = k_bean.getInTime();

                //上班卡
                if (inTime !=0){  //进店时间
                    pushType = 2;
                    end_line.setVisibility(View.VISIBLE);
                    tv_push_text.setText("结束打卡");
                    String stringDate_start1 = TimeUtils.getStringDate_start(inTime);
                    start_time_d.setText(stringDate_start1);
                    startTimeStatus = k_bean.getStartTimeStatus(); //进店打卡时间
                    startLocationStatus = k_bean.getStartLocationStatus(); //进店打卡位置
                    if (startTimeStatus == 1 && startLocationStatus == 1) {
                        start_tag.setImageResource(R.mipmap.kaoqinz);
                    }
                    if (startTimeStatus == -1 || startLocationStatus == -1) {
                        start_tag.setImageResource(R.mipmap.kaoqinyc);
                    }
                    inLat = k_bean.getInLat();
                    inLng = k_bean.getInLng();
                    mHandler.post(run_getStartAddress);
                } else {
                    start_tag.setImageResource(R.mipmap.kaoqinq);
                }

                //下班卡
                if (k_bean.getOutTime() !=0){
                    pushType = 3;
                    include_end.setVisibility(View.VISIBLE);
                    //备注
                    send_remark.setVisibility(View.VISIBLE);
                    my_remarks.setText(attendance.getData().getNotice());
                    String stringDate_start1 = TimeUtils.getStringDate_start(inTime); //下班卡
                    end_tv_time_d.setText(stringDate_start1);
                    endLocationStatus = k_bean.getEndLocationStatus();
                    endTimeStatus = k_bean.getEndTimeStatus();
                    startTimeStatus = k_bean.getStartTimeStatus(); //进店打卡时间
                    if (endTimeStatus == 1 && endLocationStatus == 1) {
                        end_tag.setImageResource(R.mipmap.kaoqinz);
                    }
                    if (endTimeStatus == -1 || endLocationStatus == -1) {
                        end_tag.setImageResource(R.mipmap.kaoqinyc);
                    }
                    outLat = k_bean.getOutLat();
                    outLng = k_bean.getOutLng();
                    //获取地址
                    mHandler.post(run_getEndAddress);
                } else {
                    end_tag.setImageResource(R.mipmap.kaoqinq);
                }

            }
        } else {
            pushType = 1;
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
        dialogs.dismiss();
        ToastUtils.showLong(attendRemark.getMsg());
    }

    @Override
    public void sendRemarkFailure(String failure) {
        dialogs.dismiss();
        ToastUtils.showLong(failure);
    }

    /**
     * 连续定位
     *
     * @param location
     */
    @Override
    public void LocationContinuouslySuccess(BDLocation location, BaiduLocationHelper baiduLocationHelper) {
        this.baiduLocationHelper = baiduLocationHelper;
        continueStla = location.getLatitude();
        continueStlo = location.getLongitude();
        mHandler.sendEmptyMessage(LOCATIONMESSAGE);
    }

    @Override
    public void LocationContinuouslyFailure(int failure, BaiduLocationHelper baiduLocationHelper) {
        this.baiduLocationHelper = baiduLocationHelper;
        baiduLocationHelper.stopLocation();
        mHandler.removeMessages(LOCATIONMESSAGE);
        p.startLocations(4);
    }

    @Override
    public void getPushSuccess(PushAttendanceCard pushAttendanceCard) {
                if (pushType ==1){  //上班卡
                    include_start.setVisibility(View.VISIBLE);
                    end_line.setVisibility(View.VISIBLE);
                    String stringDate_start1 = TimeUtils.getStringDate_start(pushAttendanceCard.getData().getSignInTime());
                    start_time_d.setText(stringDate_start1);
                    int timeStatus = pushAttendanceCard.getData().getTimeStatus();
                        if (timeStatus ==1){
                            start_tag.setImageResource(R.mipmap.kaoqinz);
                        } else {
                            start_tag.setImageResource(R.mipmap.kaoqinyc);
                        }

                }
                //下班卡
                if (pushType ==2 || pushType ==3){
                    include_end.setVisibility(View.VISIBLE);
                    String stringDate_start1 = TimeUtils.getStringDate_start(pushAttendanceCard.getData().getSignInTime()); //下班卡
                    end_tv_time_d.setText(stringDate_start1);
                    int timeStatus = pushAttendanceCard.getData().getTimeStatus();
                    if (timeStatus ==1){
                        end_tag.setImageResource(R.mipmap.kaoqinz);
                    } else {
                        end_tag.setImageResource(R.mipmap.kaoqinyc);
                    }
                    send_remark.setVisibility(View.VISIBLE);
                }
    }

    @Override
    public void getPushFail(String failure) {
        ToastUtils.showLong(failure+"请重新打卡");
    }

    //网络请求考勤信息
    private void getData(){
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", Constants.ht_ProjectId);
        map.put("sessionId",Constants.sessionId);
        p.getMainData(map);
    }

    /**
     * 打卡
     */
    private void PushCard(){
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId",Constants.sessionId);
        map.put("projectId",projectId);
        map.put("classId",classId);
        map.put("dealerId",dealerId);
        map.put("latitude",continueStla);
        map.put("longitude",continueStlo);
        if (pushType ==1){ //上班卡
            map.put("startLocationStatus",isOnCircle?1:-1);
        }
        if (pushType == 2 || pushType ==3){  //下班卡或更新下班卡
            map.put("endLocationStatus",isOnCircle?1:-1);
        }
        p.getPushCard(map);
    }

    /**
     * 考勤备注
     * @param remark
     */
    private void PushAttendanceRemark(String remark){
        Map<String, Object> map=new HashMap();
        map.put("id",classId);
        map.put("remark",remark);
        p.sendRemark(map);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(LOCATIONMESSAGE);
        mHandler.removeCallbacks(run_time);
        if (baiduLocationHelper != null) {
            baiduLocationHelper.stopLocation();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.post(run_time);
        if (latitude != 0 && longitude != 0) {
            p.startLocations(4);
        }
        if (mHandler != null) {
            if (latitude != 0 && longitude != 0) {
                mHandler.sendEmptyMessage(LOCATIONMESSAGE);
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(run_time);
        mHandler.removeMessages(LOCATIONMESSAGE);
        if (baiduLocationHelper != null) {
            baiduLocationHelper.stopLocation();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        KLog.d("隐藏了吗",hidden);
        if (hidden){
            mHandler.removeCallbacks(run_time);
            mHandler.removeMessages(LOCATIONMESSAGE);
            if (baiduLocationHelper != null) {
                baiduLocationHelper.stopLocation();
            }
        } else {
            mHandler.post(run_time);
            if (latitude != 0 && longitude != 0) {
                p.startLocations(4);
            }
            if (mHandler != null) {
                if (latitude != 0 && longitude != 0) {
                    mHandler.sendEmptyMessage(LOCATIONMESSAGE);
                }
            }
        }
    }
}
