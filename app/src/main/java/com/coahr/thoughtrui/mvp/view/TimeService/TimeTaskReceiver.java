package com.coahr.thoughtrui.mvp.view.TimeService;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.view.UMPush.NotificationUtil;
import com.socks.library.KLog;

import java.util.Random;

import omrecorder.PullableSource;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/3/13
 * 描述：定时广播
 */
public class TimeTaskReceiver extends BroadcastReceiver {
    @SuppressLint("NewApi")
    @Override
    public void onReceive(Context context, Intent intent) {
        //高版本重复设置闹钟达到低版本中setRepeating相同效果

        AlarmTimerUtil.getInstance(BaseApplication.mContext).getUpAlarmManagerWorkOnReceiver();
        KLog.d("轮询", intent.getStringExtra("msg"));
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationUtil.notifyMsg(context,intent.getIntExtra("notifyId",0),manager);
    }
}
