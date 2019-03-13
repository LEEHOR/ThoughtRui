package com.coahr.thoughtrui.mvp.view.TimeService;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

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
        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);

        return super.onStartCommand(intent, flags, startId);
    }
}
