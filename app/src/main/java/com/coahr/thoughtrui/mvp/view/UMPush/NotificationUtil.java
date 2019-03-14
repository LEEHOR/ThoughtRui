package com.coahr.thoughtrui.mvp.view.UMPush;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import com.coahr.thoughtrui.R;

import androidx.core.app.NotificationCompat;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/3/13
 * 描述：
 */
public class NotificationUtil {
    private static final String TAG = "NotificationUtil";
    /**
     * 消息通知
     *
     * @param context
     */
    public static void notifyMsg(Context context, int nid, NotificationManager mNotifyMgr) {
        if (mNotifyMgr == null) {
            mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        //准备intent
        Intent intent = new Intent();
        //notification
        Notification notification = null;
        // 构建 PendingIntent
        PendingIntent pi = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //版本兼容

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//兼容Android8.0
            String id = "my_channel_01";
            int importance = NotificationManager.IMPORTANCE_LOW;
            CharSequence name = "notice";
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            mChannel.enableLights(true);
            mChannel.setDescription("just show notice");
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.GREEN);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotifyMgr.createNotificationChannel(mChannel);

            Notification.Builder builder = new Notification.Builder(context, id);
            builder.setAutoCancel(true)
                    .setContentIntent(pi)
                    .setContentTitle("测试标题")
                    .setContentText("测试内容1")
                    .setOngoing(false)
                    .setSmallIcon(R.mipmap.ic_desktop)
                    .setWhen(System.currentTimeMillis());
            builder.setSubText("哈哈哈哈哈哈哈");
            notification = builder.build();
        } else if (Build.VERSION.SDK_INT >= 23) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle("测试标题")
                    .setContentText("测试内容2")
                    .setSmallIcon(R.mipmap.ic_desktop)
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .setOngoing(false)
                    .setWhen(System.currentTimeMillis());
            builder.setSubText("哈哈哈哈哈哈哈");
            notification = builder.build();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN &&
                Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            Notification.Builder builder = new Notification.Builder(context);
            builder.setAutoCancel(true)
                    .setContentIntent(pi)
                    .setContentTitle("测试标题")
                    .setContentText("测试内容3")
                    .setOngoing(false)
                    .setSmallIcon(R.mipmap.ic_desktop)
                    .setWhen(System.currentTimeMillis());
            builder.setSubText("哈哈哈哈哈哈哈");
            notification = builder.build();
        }
        if (notification != null) {
            mNotifyMgr.notify(nid, notification);
        }
    }
}
