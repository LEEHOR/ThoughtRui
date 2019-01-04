package com.coahr.thoughtrui.Utils;

import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Leehor
 * on 2018/11/14
 * on 16:08
 */
public class TimeUtils {

    public static String getStringDate_start(long date){
        Date d = new Date(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = sdf.format(d);
        return startTime;
    }

    public static String getStringDate_end(long date){
        String startTime;
        if (date == 1){
            startTime="结束公开";
          return  startTime;
        } else {
            Date d = new Date(date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
             startTime = sdf.format(d);
            return startTime;
        }
    }

    public static String getStingYMDHM(long date){
        String startTime;
            Date d = new Date(date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            startTime = sdf.format(d);
            return startTime;

    }

    public static void  updataTimeFormat(TextView textView, int millisecond) {
        //将毫秒转换为秒
        int second = millisecond / 1000;
        //计算小时
        int hh = second / 3600;
        //计算分钟
        int mm = second % 3600 / 60;
        //计算秒
        int ss = second % 60;
        //判断时间单位的位数
        String str = null;
        if (hh != 0) {//表示时间单位为三位
            str = String.format("%02d:%02d:%02d", hh, mm, ss);
        } else {
            str = String.format("%02d:%02d", mm, ss);
        }
        //将时间赋值给控件
        textView.setText(str);
    }
}
