package com.coahr.thoughtrui.mvp.view.UMPush;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;


import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.umeng.message.UTrack;
import com.umeng.message.entity.UMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class MyNotificationService extends Service {
    private static final String TAG = UmengNotificationService.class.getName();
    public static UMessage oldMessage = null;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return super.onStartCommand(intent, flags, startId);
        }
        String message = intent.getStringExtra("UmengMsg");
        try {
            UMessage msg = new UMessage(new JSONObject(message));
            if (oldMessage != null) {
                UTrack.getInstance(getApplicationContext()).setClearPrevMessage(true);
                UTrack.getInstance(getApplicationContext()).trackMsgDismissed(oldMessage);
            }
            showNotification(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void showNotification(UMessage msg) {
        int id = new Random(System.nanoTime()).nextInt();
        oldMessage = msg;
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
/*        RemoteViews myNotificationView = new RemoteViews(BaseApplication.mContext.getPackageName(), R.layout.notification_view);
        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
        myNotificationView.setTextViewText(R.id.tv_time, TimeUtils.getStringDate_start(System.currentTimeMillis()));
        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, msg.largeIcon != null ? Imageloader.getBitMap(msg.largeIcon) : BitmapFactory.decodeResource(BaseApplication.mContext.getResources(), R.mipmap.ic_launcher));
        myNotificationView.setImageViewResource(R.id.notification_small_icon, R.drawable.ic_launcher_background);

        RemoteViews myNotificationView_big = new RemoteViews(BaseApplication.mContext.getPackageName(), R.layout.notification_big_view);
        myNotificationView_big.setTextViewText(R.id.notification_title, msg.title);
        myNotificationView_big.setTextViewText(R.id.notification_text, msg.text);
        myNotificationView_big.setTextViewText(R.id.tv_time, TimeUtils.getStringDate_start(System.currentTimeMillis()));
        myNotificationView_big.setImageViewBitmap(R.id.notification_large_icon, msg.largeIcon != null ? Imageloader.getBitMap(msg.largeIcon) : BitmapFactory.decodeResource(BaseApplication.mContext.getResources(), R.mipmap.ic_launcher));
        myNotificationView_big.setImageViewResource(R.id.notification_small_icon, R.drawable.ic_launcher_background);*/
        Notification.Builder mBuilder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //创建渠道组
            String groupId = "group_notification";
            String groupName = "常规推送";
            NotificationChannelGroup group = new NotificationChannelGroup(groupId, groupName);
            manager.createNotificationChannelGroup(group);
            //创建通知消息
            String Notification_id = "Umeng";
            // 用户可以看到的通知渠道的名字.
            CharSequence Notification_name = getString(R.string.Notification_name);
            // 用户可以看到的通知渠道的描述
            String Notification_description = getString(R.string.Notification_name);
            NotificationChannel channel = new NotificationChannel(Notification_id, Notification_name, NotificationManager.IMPORTANCE_HIGH);
            if (manager != null) {
                // 配置通知渠道的属性
                channel.setGroup(groupId);
                channel.setDescription(Notification_description);
                if (msg.play_lights || msg.play_vibrate || msg.play_sound) {
                    // 设置通知出现时的闪灯（如果 android 设备支持的话）
                    channel.enableLights(true);
                    channel.setLightColor(Color.RED);
                    // 设置通知出现时的震动（如果 android 设备支持的话）
                    channel.enableVibration(true);
                    channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                }
                channel.setShowBadge(true);
                manager.createNotificationChannel(channel);
            }
            mBuilder = new Notification.Builder(getApplicationContext(), Notification_id);
           // mBuilder.setCustomContentView(myNotificationView);
            mBuilder.setWhen(System.currentTimeMillis())
                    .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_launcher_background))
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(msg.title)
                    .setContentText(msg.text)
                    .setTicker(msg.ticker)
                    .setStyle(new Notification.BigTextStyle().bigText(msg.text))
                    .setWhen(System.currentTimeMillis())
                    .setChannelId(Notification_id);

        } else {
            mBuilder = new Notification.Builder(getApplicationContext());
           // mBuilder.setContent(myNotificationView);
            mBuilder.setSmallIcon(R.drawable.ic_launcher_background)
                    .setWhen(System.currentTimeMillis())
                    .setLargeIcon(BitmapFactory.decodeResource(BaseApplication.mContext.getResources(), R.drawable.ic_launcher_background))
                    .setContentTitle(msg.title)
                    .setContentText(msg.text)
                    .setStyle(new Notification.BigTextStyle().bigText(msg.text))
                    .setTicker(msg.ticker);

        }
        Notification notification = mBuilder.build();
        PendingIntent clickPendingIntent = getClickPendingIntent(getApplicationContext(), msg);
        PendingIntent dismissPendingIntent = getDismissPendingIntent(getApplicationContext(), msg);
        notification.deleteIntent = dismissPendingIntent;
        notification.contentIntent = clickPendingIntent;
        manager.notify(id, notification);
    }

    /**
     * 点击
     * @param context
     * @param msg
     * @return
     */
    public PendingIntent getClickPendingIntent(Context context, UMessage msg) {
        Intent clickIntent = new Intent();
        clickIntent.setClass(context, NotificationBroadcast.class);
        clickIntent.putExtra(NotificationBroadcast.EXTRA_KEY_MSG,
                msg.getRaw().toString());
        clickIntent.putExtra(NotificationBroadcast.EXTRA_KEY_ACTION,
                NotificationBroadcast.ACTION_CLICK);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context,
                (int) (System.currentTimeMillis()),
                clickIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        return clickPendingIntent;
    }

    /**
     * 取消
     * @param context
     * @param msg
     * @return
     */
    public PendingIntent getDismissPendingIntent(Context context, UMessage msg) {
        Intent deleteIntent = new Intent();
        deleteIntent.setClass(context, NotificationBroadcast.class);
        deleteIntent.putExtra(NotificationBroadcast.EXTRA_KEY_MSG,
                msg.getRaw().toString());
        deleteIntent.putExtra(
                NotificationBroadcast.EXTRA_KEY_ACTION,
                NotificationBroadcast.ACTION_DISMISS);
        PendingIntent deletePendingIntent = PendingIntent.getBroadcast(context,
                (int) (System.currentTimeMillis() + 1),
                deleteIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        return deletePendingIntent;
    }

/*    *//**
     * 展开关闭
     * @return
     *//*
    public PendingIntent getOpenOrClosePendingIntent(Context context, UMessage msg) {
        Intent openOrClose = new Intent();
        openOrClose.setClass(context, NotificationBroadcast.class);
        openOrClose.putExtra(NotificationBroadcast.EXTRA_KEY_MSG,
                msg.getRaw().toString());
        openOrClose.putExtra(
                NotificationBroadcast.EXTRA_KEY_ACTION,
                NotificationBroadcast.NOTIFICATION_OPEN);
        PendingIntent OpenOrClosePendingIntent = PendingIntent.getBroadcast(context,
                (int) (System.currentTimeMillis() + 1),
                openOrClose, PendingIntent.FLAG_CANCEL_CURRENT);
        return OpenOrClosePendingIntent;
    }*/
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
