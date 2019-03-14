package com.coahr.thoughtrui.mvp.view.TimeService;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.view.UMPush.NotificationUtil;
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
        int type = intent.getIntExtra("type", 0);
        String tittle = intent.getStringExtra("ContentTittle");
        String contentText = intent.getStringExtra("ContentText");
        String pCode = intent.getStringExtra("PCode");
        if (tittle !=null && contentText !=null &&  pCode !=null){
            if (type==1){
                NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationUtil.notifyMsg(context,(int)System.currentTimeMillis(),manager,Constants.fragment_main,tittle,contentText+pCode);
            }
            if (type==2){
                NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationUtil.notifyMsg(context,(int)System.currentTimeMillis(),manager,Constants.fragment_main,tittle,contentText+pCode);
            }
            if (type==3){
                NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationUtil.notifyMsg(context,(int)System.currentTimeMillis(),manager,Constants.fragment_main,tittle,contentText+pCode);
            }

        }

    }
}
