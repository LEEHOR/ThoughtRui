package com.coahr.thoughtrui.Utils;

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
}
