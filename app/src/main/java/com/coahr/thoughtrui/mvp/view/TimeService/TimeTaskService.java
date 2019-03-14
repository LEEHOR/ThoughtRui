package com.coahr.thoughtrui.mvp.view.TimeService;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.view.UMPush.NotificationUtil;
import com.socks.library.KLog;

import androidx.annotation.Nullable;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/3/13
 * 描述：定时任务
 */
public class TimeTaskService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //高版本重复设置闹钟达到低版本中setRepeating相同效果
        AlarmTimerUtil.getInstance(BaseApplication.mContext).getUpAlarmManagerWorkOnReceiver();
        KLog.d("轮询", intent.getStringExtra("msg"));
        NotificationManager manager = (NotificationManager)BaseApplication.mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationUtil.notifyMsg(BaseApplication.mContext,intent.getIntExtra("notifyId",0),manager);
        return super.onStartCommand(intent, flags, startId);
    }
}
