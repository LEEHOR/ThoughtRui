package com.coahr.thoughtrui.mvp.view.attence;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.print.PrinterId;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
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
import com.coahr.thoughtrui.mvp.model.Bean.BaiduApiBean;
import com.coahr.thoughtrui.mvp.model.Bean.Event_Attend;
import com.coahr.thoughtrui.mvp.model.Bean.PushAttendanceCard;
import com.coahr.thoughtrui.mvp.presenter.AttendanceFP_k;
import com.coahr.thoughtrui.widgets.AltDialog.EvaluateInputDialogFragment;
import com.coahr.thoughtrui.widgets.HttpUtils.BaiduApi;
import com.coahr.thoughtrui.widgets.HttpUtils.HttpUtilListener;
import com.coahr.thoughtrui.widgets.HttpUtils.HttpUtils;
import com.coahr.thoughtrui.widgets.HttpUtils.OkCallBack;
import com.coahr.thoughtrui.widgets.HttpUtils.OkHttpHelper;
import com.google.gson.Gson;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 12:59
 * 考勤打卡页面
 */
public class AttendanceFragment_k extends BaseChildFragment<AttendanceFC_k.Presenter> implements AttendanceFC_k.View {
    @Inject
    AttendanceFP_k p;
    @BindView(R.id.tv_in_time)
    TextView tv_in_time;   //早班卡时间
    @BindView(R.id.tv_out_time)
    TextView tv_out_time;  //晚班卡时间
    //早班卡
    @BindView(R.id.push_in2_re)
    FrameLayout push_in2_re; //早班卡打卡区域隐藏
    @BindView(R.id.re_push_clock_in)
    RelativeLayout re_push_clock_in;  //早班卡打卡
    @BindView(R.id.tv_time_clock_in)
    TextView tv_time_clock_in;  //早班卡时钟
    @BindView(R.id.location_message_in)
    TextView location_message_in;   //早班卡定位状态
    @BindView(R.id.location_address_in)
    TextView location_address_in; //早班卡定位地址
    @BindView(R.id.relocation_in)
    TextView relocation_in;   //早班重新定位
    @BindView(R.id.in_bottom_address_in)
    LinearLayout in_bottom_address_in;  //早班卡定位栏


    //晚班卡
    @BindView(R.id.push_out_re)
    RelativeLayout push_out_re; //晚班显示隐藏
    @BindView(R.id.push_out2_re)
    FrameLayout push_out2_re;  //晚班卡打卡区域
    @BindView(R.id.re_push_clock_out)
    RelativeLayout re_push_clock_out;  //晚班打卡
    @BindView(R.id.tv_time_clock_out)
    TextView tv_time_clock_out; //晚班卡时钟
    @BindView(R.id.location_message_out)
    TextView location_message_out; //晚班卡定位状态
    @BindView(R.id.location_address_out)
    TextView location_address_out; //晚班卡定位地址
    @BindView(R.id.relocation_out)
    TextView relocation_out; //晚班卡重新定位
    @BindView(R.id.in_bottom_address)
    LinearLayout in_bottom_address;  //晚班卡底部定位栏

    @BindView(R.id.include_start)
    View include_start; //早班打卡信息
    @BindView(R.id.include_end)
    View include_end;   //晚班打卡信息


    private ImageView start_tag;//早班卡打卡状态图标
    private TextView start_time_d; //早班打卡时间
    private TextView tv_start_location; //早班打卡位置

    private ImageView end_tag; //晚班打卡状态图标
    private TextView end_tv_time_d; //晚班打卡时间
    private TextView tv_end_location; //晚班打卡位置
    private View update_daka;  //更新打卡
    private View end_tv_bz;  //考勤备注

    private int closeStatus;
    private String k_id;
    private AppCompatDialogFragment dialogs;
    private Attendance.DataBean.AttendanceBean k_bean;

    private boolean isOnCircle; //是否在打卡范围
    private int startLocationStatus; //早班卡位置是否正确
    private int startTimeStatus;    //早班卡时间是否正确

    private int endLocationStatus;  //晚班打卡位置状态
    private int endTimeStatus;   //晚班打卡时间状态
    private int type;  //打卡类型
    private String Location_now; //当前定位的位置
    //进店打卡的经纬度
    private double inLat;
    private double inLng;


    private String classId;
    private String dealerId;
    private String projectId;
    private EvaluateInputDialogFragment dialogFragment;
    //门店所在的经纬度
    private double latitude;
    private double longitude;

    //离店打卡的经纬度
    private double outLat;
    private double outLng;
    /**
     * 循环定位信息
     */
    private BaiduLocationHelper baiduLocationHelper;
    private static final int LOCATIONMESSAGE = 1;
    private static final  int zao_daka=2;
    private static final  int wan_daka=3;
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
                    LatLng latLng = new LatLng(latitude, longitude);//公司坐标
                    double distance = DistanceUtil.getDistance(LocationPoint, latLng);
                    if (distance > 200) {
                        KLog.d("在范围1");
                        isOnCircle = false;
                        //定位状态
                        //早班卡
                        location_message_in.setTextColor(getResources().getColor(R.color.material_red_A700));
                        location_message_in.setText("不在考勤范围");
                        //晚班卡
                        location_message_out.setTextColor(getResources().getColor(R.color.material_red_A700));
                        location_message_out.setText("不在考勤范围");
                    } else {
                        isOnCircle = true;
                        KLog.d("在范围21");
                        //早班卡
                        location_message_in.setTextColor(Color.rgb(153, 153, 153));
                        location_message_in.setText("已进入考勤范围");
                        //晚班卡
                        location_message_out.setTextColor(Color.rgb(153, 153, 153));
                        location_message_out.setText("已进入考勤范围");
                    }
                    break;
                case zao_daka:
                    String s = msg.obj.toString();
                    tv_start_location.setText(s);
                    break;
                case wan_daka:
                    String s2 = msg.obj.toString();
                    tv_end_location.setText(s2);
                    break;
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
            // tv_time_clock.setText(simpleDateFormat.format(date)); //更新时间
            tv_time_clock_in.setText(simpleDateFormat.format(date));
            tv_time_clock_out.setText(simpleDateFormat.format(date));
            mHandler.postDelayed(run_time, 1000);
        }
    };
    public static AttendanceFragment_k newInstance() {
        AttendanceFragment_k attendanceFragment_k = new AttendanceFragment_k();
        return attendanceFragment_k;
    }


    @Override
    public AttendanceFC_k.Presenter getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_kaoqin;
    }


    @Override
    public void initView() {
        dialogFragment = new EvaluateInputDialogFragment();
        dialogFragment.setOnInputCallback(new EvaluateInputDialogFragment.InputCallback() {
            @Override
            public void onInputSend(String input, AppCompatDialogFragment dialog) {
                dialogs = dialog;
                PushAttendanceRemark(input);
            }
        });
        start_time_d = include_start.findViewById(R.id.attendance_time_k);
        start_tag = include_start.findViewById(R.id.iv_attendance_tag_start);
        tv_start_location = include_start.findViewById(R.id.tv_attendance_address_k);

        end_tv_time_d = include_end.findViewById(R.id.attendance_time_e);
        end_tag = include_end.findViewById(R.id.iv_attendance_tag_end);
        tv_end_location = include_end.findViewById(R.id.tv_attendance_address_e);
        update_daka = include_end.findViewById(R.id.tv_update_daka);
        end_tv_bz = include_end.findViewById(R.id.tv_bzhu);

  /*      //重新定位
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
        });*/
        /**
         * 晚班更新打卡
         */
        update_daka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (baiduLocationHelper != null) {
                    baiduLocationHelper.stopLocation();
                }
                //连续定位
                p.startLocations(4);
                if (continueStla ==0 || continueStlo==0){
                    ToastUtils.showLong("正在定位请稍后");
                    return;
                }
                PushCard();
            }
        });
  //早班卡重新定位
        relocation_in.setOnClickListener(new View.OnClickListener() {
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
//晚班卡重新定位
        relocation_out.setOnClickListener(new View.OnClickListener() {
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
        //早班卡
        re_push_clock_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (continueStla ==0 || continueStlo==0){
                    ToastUtils.showLong("正在定位请稍后");
                    return;
                }
                if (isOnCircle) {
                    PushCard();
                }else {
                    new MaterialDialog.Builder(_mActivity)
                            .title("提示")
                            .content("当前不在考勤范围内")
                            .positiveText("确定")
                            .positiveColor(getResources().getColor(R.color.material_red_A700))
                            .negativeText("取消")
                            .negativeColor(getResources().getColor(R.color.material_blue_800))
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    PushCard();
                                    dialog.dismiss();
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            })
                            .build().show();
                }

            }
        });

        //晚班打卡
        re_push_clock_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (continueStla ==0 || continueStlo==0){
                    ToastUtils.showLong("正在定位请稍后");
                    return;
                }
                if (isOnCircle) {
                    PushCard();
                }else {
                    new MaterialDialog.Builder(_mActivity)
                            .title("提示")
                            .content("当前不在考勤范围内")
                            .positiveText("确定")
                            .positiveColor(getResources().getColor(R.color.material_red_A700))
                            .negativeText("取消")
                            .negativeColor(getResources().getColor(R.color.material_blue_800))
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    PushCard();
                                    dialog.dismiss();
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            })
                            .build().show();
                }
            }
        });
        //考勤备注
        end_tv_bz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragment.show(_mActivity.getSupportFragmentManager(), TAG);
            }
        });
    }

    @Override
    public void initData() {
        //获取打卡信息
        getData();
        mHandler.post(run_time); //开启时间线程
    }
    @Override
    public void getMainDataSuccess(Attendance attendance) {
   /*     //把数据发送到AttendRootFragment用于显示
          EventBus.getDefault().postSticky(new Event_Attend(attendance.getData().getPname()
                  ,attendance.getData().getStartTime()
                  ,attendance.getData().getEndTime()
                  ,attendance.getData().getCname()
                  ,attendance.getData().getCode()
                  ,attendance.getData().getAreaAddress()));*/
        closeStatus = attendance.getData().getCloseStatus();
        //获取门店的经纬度
        latitude = attendance.getData().getLatitude();
        longitude = attendance.getData().getLongitude();
        classId = attendance.getData().getClassId();
        dealerId = attendance.getData().getDealerId();
        projectId = attendance.getData().getProjectId();
        String str = attendance.getData().getCname();
        if (str.equals("自由班次")) {
            tv_in_time.setText(str);
            tv_out_time.setText(str);
        } else {
            String[] split = str.split("-");
            if (split.length > 1) {
                tv_in_time.setText(split[0]);
                tv_out_time.setText(split[1]);
            }
        }
        //判断上下班卡 1上班卡 2下班卡


        type = attendance.getData().getType();
        k_bean = attendance.getData().getAttendance();
        if (type == 1) { //渲染上班卡（没打卡）
            //早班打卡信息隐藏
            include_start.setVisibility(View.GONE);
            //早班打卡区域显示
            push_in2_re.setVisibility(View.VISIBLE);
            //早班卡底部定位栏显示
            in_bottom_address_in.setVisibility(View.VISIBLE);
            //晚班区域隐藏
            push_out_re.setVisibility(View.GONE);
            //先关闭定位
            if (baiduLocationHelper != null) {
                baiduLocationHelper.stopLocation();
            }
            //开启连续定位
            p.startLocations(4);

        } else if(type==2) {  //（打了早班卡，下班卡没打）
            if (k_bean != null) { //判断是否为空
                k_id = k_bean.getId();
                if (k_id != null && !k_id.equals("")) {
                    //早班卡逻辑判断
                    long inTime = k_bean.getInTime();
                    if (inTime != 0) {   //如果不为0则打卡了否则没打卡
                        //早班打卡信息显示
                        include_start.setVisibility(View.VISIBLE);
                        //早班打卡区域隐藏
                        push_in2_re.setVisibility(View.GONE);
                        //早班卡底部定位栏隐藏
                        in_bottom_address_in.setVisibility(View.GONE);
                        //早班卡打卡所在位置经纬度
                        inLat = k_bean.getInLat();
                        inLng = k_bean.getInLng();
                        //早班打卡位置状态
                        startLocationStatus = k_bean.getStartLocationStatus();
                        //早班打卡时间状态
                        startTimeStatus = k_bean.getStartTimeStatus();
                        //早班打卡时间
                        String stringDate_start = TimeUtils.getStingHM(k_bean.getInTime());
                        start_time_d.setText(stringDate_start);
                        //图标
                        if (startLocationStatus == 1 && startTimeStatus == 1) {  //在范围内
                            start_tag.setImageResource(R.mipmap.kaoqinz);
                            //早班打卡位置
                            tv_start_location.setText(attendance.getData().getAreaAddress() + attendance.getData().getLocation());
                        } else if (startLocationStatus == -1 || startTimeStatus == -1) {
                            start_tag.setImageResource(R.mipmap.kaoqinyc);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    getAddress(1,inLat,inLng);
                                }
                            }).start();
                        }
                    }
                    //晚班卡逻辑判断
                    //晚班界面显示
                    push_out_re.setVisibility(View.VISIBLE);
                    //晚班打卡区域显示
                    push_out2_re.setVisibility(View.VISIBLE);
                    //晚班打卡定位栏
                    in_bottom_address.setVisibility(View.VISIBLE);
                    //晚班卡打卡信息隐藏
                    include_end.setVisibility(View.GONE);

                    //先关闭定位
                    if (baiduLocationHelper != null) {
                        baiduLocationHelper.stopLocation();
                    }
                    //开启连续定位
                    p.startLocations(4);
                }
            }
        } else if (type==3){ //(上下班卡都打了)
            //早班卡逻辑判断
            long inTime = k_bean.getInTime();
            if (inTime != 0) {   //如果不为0则打卡了否则没打卡
                //早班打卡信息显示
                include_start.setVisibility(View.VISIBLE);
                //早班打卡区域隐藏
                push_in2_re.setVisibility(View.GONE);
                //早班卡底部定位栏隐藏
                in_bottom_address_in.setVisibility(View.GONE);
                //早班卡打卡所在位置经纬度
                inLat = k_bean.getInLat();
                inLng = k_bean.getInLng();
                //早班打卡位置状态
                startLocationStatus = k_bean.getStartLocationStatus();
                //早班打卡时间状态
                startTimeStatus = k_bean.getStartTimeStatus();
                //早班打卡时间
                String stringDate_start = TimeUtils.getStingHM(k_bean.getInTime());
                start_time_d.setText(stringDate_start);
                //早班打卡位置

                //图标
                if (startLocationStatus == 1 && startTimeStatus == 1) {  //在范围内
                    start_tag.setImageResource(R.mipmap.kaoqinz);
                    tv_start_location.setText(attendance.getData().getAreaAddress() + attendance.getData().getLocation());
                } else if (startLocationStatus == -1 || startTimeStatus == -1) {
                    start_tag.setImageResource(R.mipmap.kaoqinyc);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getAddress(1,inLat,inLng);
                        }
                    }).start();
                }
            }

            //晚班卡逻辑判断
            long outTime = k_bean.getOutTime();
            if (outTime!=0) {
                //晚班界面显示
                push_out_re.setVisibility(View.VISIBLE);
                //晚班打卡区域隐藏
                push_out2_re.setVisibility(View.GONE);
                //晚班打卡定位栏隐藏
                in_bottom_address.setVisibility(View.GONE);
                //晚班卡打卡信息隐藏
                include_end.setVisibility(View.VISIBLE);
                //晚班打卡时间
                String stringDate_start = TimeUtils.getStingHM(k_bean.getOutTime());
                end_tv_time_d.setText(stringDate_start);
                //晚班打卡位置状态
                endLocationStatus = k_bean.getEndLocationStatus();
                //晚班打卡时间状态
                endTimeStatus = k_bean.getEndTimeStatus();
                //晚班打卡经纬度
                outLat= k_bean.getOutLat();
                outLng= k_bean.getOutLng();
                if (endLocationStatus ==1 && endTimeStatus==1){
                    //晚班打卡位置(正常就直接显示上班地址)
                    tv_end_location.setText(attendance.getData().getAreaAddress() + attendance.getData().getLocation());
                    end_tag.setImageResource(R.mipmap.kaoqinz);
                } else if (endLocationStatus ==-1 || endTimeStatus==-1){
                    end_tag.setImageResource(R.mipmap.kaoqinyc);
                    //晚班打卡位置(异常显示当时打卡位置)
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getAddress(2,outLat,outLng);
                        }
                    }).start();

                }
            }
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
        //当前经纬度
        continueStla = location.getLatitude();
        continueStlo = location.getLongitude();
        //当前定位位置
        Location_now = location.getAddress().street;
        KLog.d("当前定位",location.getAddress().street);
        //把定位信息赋值
        location_address_in.setText(Location_now);
        location_address_out.setText(Location_now);

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
        if (type==3){
            if (baiduLocationHelper != null) {
                baiduLocationHelper.stopLocation();
            }
            mHandler.removeMessages(LOCATIONMESSAGE);
            mHandler.removeCallbacks(run_time);
        }
        getData();

    }

    @Override
    public void getPushFail(String failure) {
        ToastUtils.showLong(failure + "请重新打卡");
    }

    //网络请求考勤信息
    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", Constants.ht_ProjectId);
        map.put("sessionId", Constants.sessionId);
        p.getMainData(map);
    }

    /**
     * 打卡
     */
    private void PushCard() {
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", Constants.sessionId);
        map.put("projectId", projectId);
        map.put("classId", classId);
        map.put("dealerId", dealerId);
        map.put("latitude", continueStla);
        map.put("longitude", continueStlo);
        if (type==1){
            map.put("startLocationStatus",isOnCircle?1:-1);
        } else {
            map.put("endLocationStatus",isOnCircle?1:-1);
        }
        p.getPushCard(map);
    }

    /**
     * 考勤备注
     *
     * @param remark
     */
    private void PushAttendanceRemark(String remark) {
        Map<String, Object> map = new HashMap();
        map.put("id", classId);
        map.put("remark", remark);
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
        KLog.d("隐藏了吗", hidden);
        if (hidden) {
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

    /**
     *
     * @param type_daka
     * @param lat
     * @param lng
     */
    private void getAddress(final int type_daka,double lat,double lng){
        BaiduApi baiduApi=new BaiduApi.Builder()
                .default_lat(String.valueOf(lat))
                .default_lot(String.valueOf(lng))
                .default_methodType(BaiduApi.OkHttpMethodType.POST)
                .CallBack(new HttpUtilListener() {
                    @Override
                    public void getAddressSuccess(Response response) {
                        if (response.isSuccessful()) {
                            try {
                                String string = response.body().string();
                                Gson gson=new Gson();
                                BaiduApiBean baiduApiBean = gson.fromJson(string, BaiduApiBean.class);
                                if (baiduApiBean.getStatus()==0) {
                                    KLog.d("地址",baiduApiBean.getResult().getAddressComponent().getStreet());
                                    if (type_daka==1){
                                        Message message=new Message();
                                        message.what=zao_daka;
                                        message.obj=baiduApiBean.getResult().getAddressComponent().getStreet();
                                        mHandler.sendMessage(message);
                                       // tv_start_location.setText(baiduApiBean.getResult().getAddressComponent().getStreet());
                                    } else {
                                       // tv_end_location.setText(baiduApiBean.getResult().getAddressComponent().getStreet());
                                        Message message=new Message();
                                        message.what=wan_daka;
                                        message.obj=baiduApiBean.getResult().getAddressComponent().getStreet();
                                        mHandler.sendMessage(message);
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                    @Override
                    public void getAddressFailure(String e) {

                    }
                })
                .build();
    }
}
