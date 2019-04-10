package com.coahr.thoughtrui.mvp.view.attence;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.TimeUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.constract.AttendanceFC_h;
import com.coahr.thoughtrui.mvp.model.Bean.AttendanceHistory;
import com.coahr.thoughtrui.mvp.model.Bean.BaiduApiBean;
import com.coahr.thoughtrui.mvp.presenter.AttendanceFP_h;
import com.coahr.thoughtrui.widgets.AltDialog.Login_DialogFragment;
import com.coahr.thoughtrui.widgets.HttpUtils.BaiduApi;
import com.coahr.thoughtrui.widgets.HttpUtils.HttpUtilListener;
import com.google.gson.Gson;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.socks.library.KLog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import butterknife.BindView;
import okhttp3.Response;

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
    @BindView(R.id.tv_date_out)
    TextView tv_date_out;
    @BindView(R.id.tv_date_in)
    TextView tv_date_in;
    @BindView(R.id.tv_h_remark)
    TextView tv_h_remark;
    private String projectId;

    private TextView attendance_time_k;
    private ImageView iv_attendance_tag_start;
    private TextView tv_attendance_address_k;

    private TextView attendance_time_e;
    private ImageView iv_attendance_tag_end;
    private TextView tv_attendance_address_e;
    private TimePickerDialog datePickerDialog;
    //
    private static final int zao_daka = 1;
    private static final int wan_daka = 2;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case zao_daka:
                    String s = msg.obj.toString();
                    tv_attendance_address_k.setText(s);
                    break;
                case wan_daka:
                    String s2 = msg.obj.toString();
                    tv_attendance_address_e.setText(s2);
                    break;
            }
        }
    };

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
        //获取接触历史
        getHistory(Constants.ht_ProjectId, System.currentTimeMillis());
    }

    @Override
    public void getAttendanceHistorySuccess(AttendanceHistory history) {
        if (history.getData() != null) {
            AttendanceHistory.DataBean.AttendanceBean attendance = history.getData().getAttendance();
            if (attendance != null) {
                //进度
                tv_progress.setText(attendance.getProgress());
                //时间
                tv_select_time.setText(attendance.getDateTime());
                //早班卡时间
                tv_date_in.setText(Constants.zao_ka);
                //晚班卡时间
                tv_date_out.setText(Constants.wan_ka);
                //早班卡
                if (attendance.getInTime() == 0) {
                    iv_attendance_tag_start.setImageResource(R.mipmap.kaoqinq);
                    iv_attendance_tag_end.setImageResource(R.mipmap.kaoqinq);
                    attendance_time_k.setText("");
                    attendance_time_e.setText("");
                    tv_attendance_address_k.setText("");
                    tv_attendance_address_e.setText("");
                } else {
                    final double inLat = attendance.getInLat();
                    final double inLng = attendance.getInLng();
                    attendance_time_k.setText(attendance.getInType() == 0 ? getResources().getString(R.string.attendance_morrow) : getResources().getString(R.string.attendance_the_same_day) + TimeUtils.getStingHM(attendance.getInTime()));
                    if (attendance.getStartTimeStatus() == 1 && attendance.getStartLocationStatus() == 1) {
                        iv_attendance_tag_start.setImageResource(R.mipmap.kaoqinz);
                    } else {
                        iv_attendance_tag_start.setImageResource(R.mipmap.kaoqinyc);
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getAddress(1, inLat, inLng);
                        }
                    }).start();

                    if (attendance.getOutTime() != 0) {
                        attendance_time_e.setText(attendance.getOutType() == 0 ? getResources().getString(R.string.attendance_morrow) : getResources().getString(R.string.attendance_the_same_day) + TimeUtils.getStingHM(attendance.getOutTime()));
                        final double outLat = attendance.getOutLat();
                        final double outLng = attendance.getOutLng();
                        if (attendance.getEndLocationStatus() == 1 && attendance.getEndTimeStatus() == 1) {
                            iv_attendance_tag_end.setImageResource(R.mipmap.kaoqinz);
                        } else {
                            iv_attendance_tag_end.setImageResource(R.mipmap.kaoqinyc);
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                getAddress(2, outLat, outLng);
                            }
                        }).start();
                    } else {
                        attendance_time_e.setText("");
                        tv_attendance_address_e.setText("");
                        iv_attendance_tag_end.setImageResource(R.mipmap.kaoqinq);
                    }

                    if (attendance.getRemark() != null) {
                        tv_h_remark.setText(attendance.getRemark());
                    }
                }
            }
        }
    }

    @Override
    public void getAttendanceHistoryFailure(String failure, int code) {
        ToastUtils.showLong(failure);
        if (code != -1) {

        } else {
            loginDialog();
        }
    }

    //打卡历史
    private void getHistory(String projectId, long dateTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("sessionId", Constants.sessionId);
        map.put("token", Constants.devicestoken);
        map.put("dateTime", TimeUtils.getStingYMD(dateTime));
        p.getAttendanceHistory(map);
    }

    private void initTimePickerDialog() {
        long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
        long fourHour = 60 * 60 * 4L * 1000;
        datePickerDialog = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId(getString(R.string.cancel))
                .setSureStringId(getString(R.string.resume))
                .setTitleStringId(getString(R.string.attendance_appointment_date))
                .setYearText(getString(R.string.phrases_11))
                .setMonthText(getString(R.string.phrases_12))
                .setDayText(getString(R.string.phrases_13))
                .setHourText(getString(R.string.phrases_14))
                .setMinuteText(getString(R.string.phrases_14))
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis() - tenYears)
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.material_blue_600))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.black))
                .setWheelItemTextSize(12)
                .build();
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        getHistory(Constants.ht_ProjectId, millseconds);
    }

    /**
     * 获取打卡地址
     *
     * @param type_daka
     * @param lat
     * @param lng
     */
    private void getAddress(final int type_daka, double lat, double lng) {
        BaiduApi baiduApi = new BaiduApi.Builder()
                .default_lat(String.valueOf(lat))
                .default_lot(String.valueOf(lng))
                .default_methodType(BaiduApi.OkHttpMethodType.POST)
                .CallBack(new HttpUtilListener() {
                    @Override
                    public void getAddressSuccess(Response response) {
                        if (response.isSuccessful()) {
                            try {
                                String string = response.body().string();
                                Gson gson = new Gson();
                                BaiduApiBean baiduApiBean = gson.fromJson(string, BaiduApiBean.class);
                                if (baiduApiBean.getStatus() == 0) {
                                    if (type_daka == 1) {
                                        Message message = new Message();
                                        message.what = zao_daka;
                                        message.obj = baiduApiBean.getResult().getAddressComponent().getStreet();
                                        mHandler.sendMessage(message);
                                        // tv_start_location.setText(baiduApiBean.getResult().getAddressComponent().getStreet());
                                    } else {
                                        // tv_end_location.setText(baiduApiBean.getResult().getAddressComponent().getStreet());
                                        Message message = new Message();
                                        message.what = wan_daka;
                                        message.obj = baiduApiBean.getResult().getAddressComponent().getStreet();
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

    /**
     * 登录Dialog
     */
    private void loginDialog() {
        Login_DialogFragment login_dialogFragment = Login_DialogFragment.newInstance(Constants.MyTabFragmentCode);

        login_dialogFragment.setLoginListener(new Login_DialogFragment.loginListener() {
            @Override
            public void loginSuccess(AppCompatDialogFragment dialogFragment) {
                dialogFragment.dismiss();
                if (haslogin()) {
                    getHistory(Constants.ht_ProjectId, System.currentTimeMillis());
                } else {
                    ToastUtils.showLong(getString(R.string.toast_10));
                }
            }
        });
        login_dialogFragment.show(getFragmentManager(), TAG);
    }

    @Override
    public void showError(@Nullable Throwable e) {
        super.showError(e);
        ToastUtils.showLong(e.toString());
    }
}
