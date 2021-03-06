package com.coahr.thoughtrui.mvp.view.TimeService;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.text.format.Time;

import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.socks.library.KLog;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/3/13
 * 描述：
 */
public class AlarmTimerUtil {
    private static final long TIME_INTERVAL = 60*1000*20;//闹钟执行任务的时间间隔
    private Context context;
    public static AlarmManager am;
    public static PendingIntent pendingIntent;
    private Intent intent;
    private AlarmTimerUtil(Context aContext) {
        this.context = aContext;
    }

    //单例模式
    private static AlarmTimerUtil instance = null;

    public static AlarmTimerUtil getInstance(Context aContext) {
        if (instance == null) {
            synchronized (AlarmTimerUtil.class) {
                if (instance == null) {
                    instance = new AlarmTimerUtil(aContext);
                }
            }
        }
        return instance;
    }

    public void createGetUpAlarmManager(Context activity,String action, int alarmId) {
        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        intent = new Intent(activity, TimeTaskService.class);
        intent.setAction(action);
        intent.putExtra("msg", "赶紧起床");
        //pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);//每隔5秒发送一次广播
        //服务这么写
        pendingIntent = PendingIntent.getService(context, alarmId, intent, 0);
    }

    @SuppressLint("NewApi")
    public void getUpAlarmManagerStartWork() {
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //MARSHMALLOW OR ABOVE
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //LOLLIPOP 21 OR ABOVE
            AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(System.currentTimeMillis(), pendingIntent);
            am.setAlarmClock(alarmClockInfo, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //KITKAT 19 OR ABOVE
            am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
        } else { //FOR BELOW KITKAT ALL DEVICES
            am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
        }*/

        //版本适配
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// 6.0及以上
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {// 4.4及以上
            am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                    pendingIntent);
        } else {
            am.setRepeating(AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis(), TIME_INTERVAL, pendingIntent);
        }
    }

    @SuppressLint("NewApi")
    public void getUpAlarmManagerWorkOnReceiver(Context context) {
        AlarmManager   alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, TimeTaskService.class);
        intent.setAction("TIMER_ACTION");
        intent.putExtra("msg", "赶紧起床");
        //pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);//每隔5秒发送一次广播
        //服务这么写
        PendingIntent  pendingIntents = PendingIntent.getService(context, 0, intent, 0);
        //高版本重复设置闹钟达到低版本中setRepeating相同效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// 6.0及以上
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + TIME_INTERVAL, pendingIntents);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {// 4.4及以上
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                    + TIME_INTERVAL, pendingIntents);
        }
    }

    /**
     * 取消闹钟
     */
    public void cancelAlarmTimer(String action, int alarmId) {
        Intent myIntent = new Intent(BaseApplication.mContext,TimeTaskReceiver.class);
        myIntent.setAction(action);
        PendingIntent sender = PendingIntent.getBroadcast(context, alarmId, myIntent, 0);//如果是广播，就这么写
       // PendingIntent sender = PendingIntent.getService(context, alarmId, myIntent, 0);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(sender);
    }
}
 