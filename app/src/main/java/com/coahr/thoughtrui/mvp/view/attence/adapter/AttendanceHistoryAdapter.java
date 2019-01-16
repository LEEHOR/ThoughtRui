package com.coahr.thoughtrui.mvp.view.attence.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.GetAddressUtil;
import com.coahr.thoughtrui.Utils.TimeUtils;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.model.Bean.AttendanceHistory;
import com.socks.library.KLog;


/**
 * Created by Leehor
 * on 2018/12/24
 * on 16:30
 * 已弃用
 */
public class AttendanceHistoryAdapter extends BaseQuickAdapter<AttendanceHistory, BaseViewHolder> {
    public AttendanceHistoryAdapter() {
        super(R.layout.item_recycler_attedancehistory, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, final AttendanceHistory item) {
      /*  KLog.d("开始了2");
        //开始
        View include_start = helper.getView(R.id.start_part);
        TextView attendance_time_k = include_start.findViewById(R.id.attendance_time_k);
        ImageView iv_attendance_tag_start = include_start.findViewById(R.id.iv_attendance_tag_start);
         TextView tv_attendance_address_k = include_start.findViewById(R.id.tv_attendance_address_k);
        //结束
        View include_end = helper.getView(R.id.end_part);
        TextView attendance_time_e = include_end.findViewById(R.id.attendance_time_e);
        ImageView iv_attendance_tag_end = include_end.findViewById(R.id.iv_attendance_tag_end);
         TextView tv_attendance_address_e = include_end.findViewById(R.id.tv_attendance_address_e);
        LinearLayout viewById = include_end.findViewById(R.id.line_update);
        viewById.setVisibility(View.GONE);
        include_end.findViewById(R.id.tv_bzhu).setVisibility(View.GONE);
        helper.setText(R.id.tv_date, TimeUtils.getStringDate_start(Long.parseLong(item.getDateTime())));
        helper.setText(R.id.tv_progress, item.getProgress());
        if (item.getInTime() != 0) {
            attendance_time_k.setText(TimeUtils.getStingHM(item.getInTime()));
            if (item.getStartTimeStatus() == 1 && item.getStartLocationStatus() == 1) {
                iv_attendance_tag_start.setImageResource(R.mipmap.kaoqinz);
            } else {
                iv_attendance_tag_start.setImageResource(R.mipmap.kaoqinyc);
            }
            if (item.getInLng() != 0 && item.getInLat() != 0) {
                String address = GetAddressUtil.getAddress(BaseApplication.mContext, item.getInLat(), item.getInLat());
                tv_attendance_address_k.setText(address);
            }
*//*            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (item.getInLat() != 0 && item.getInLng() != 0) {
                        //根据经纬度获取地址
                        HttpUtils.getAddress(String.valueOf(item.getInLat()), String.valueOf(item.getInLng()), new HttpUtilListener() {
                            @Override
                            public void getAddressSuccess(String s) {
                                KLog.d("返回数据", s);
                                tv_attendance_address_k.setText(s);
                            }

                            @Override
                            public void getAddressFailure(String e) {
                                KLog.d("返回数据", e);
                            }
                        });
                    }
                }
            }).start();*//*
        } else {
            iv_attendance_tag_start.setImageResource(R.mipmap.kaoqinq);
        }

        if (item.getOutTime() != 0) {
            attendance_time_e.setText(TimeUtils.getStingHM(item.getOutTime()));
            if (item.getEndTimeStatus() == 1 && item.getEndLocationStatus() == 1) {
                iv_attendance_tag_end.setImageResource(R.mipmap.kaoqinz);
            } else {
                iv_attendance_tag_end.setImageResource(R.mipmap.kaoqinyc);
            }

            if (item.getOutLat() != 0 && item.getOutLng() != 0) {
                String address = GetAddressUtil.getAddress(BaseApplication.mContext, item.getOutLng(), item.getOutLat());
                tv_attendance_address_e.setText(address);
            }
  *//*          new Thread(new Runnable() {
                @Override
                public void run() {
                    if (item.getOutLat() != 0 && item.getOutLng() != 0) {
                        //根据经纬度获取地址
                        HttpUtils.getAddress(String.valueOf(item.getOutLat()), String.valueOf(item.getOutLng()), new HttpUtilListener() {
                            @Override
                            public void getAddressSuccess(String s) {
                                KLog.d("返回数据", s);
                                tv_attendance_address_e.setText(s);
                            }

                            @Override
                            public void getAddressFailure(String e) {
                                KLog.d("返回数据", e);
                            }
                        });
                    }
                }
            }).start();*//*

        } else {
            iv_attendance_tag_end.setImageResource(R.mipmap.kaoqinq);
        }*/
    }
}
