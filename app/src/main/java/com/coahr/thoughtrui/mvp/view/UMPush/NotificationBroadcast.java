package com.coahr.thoughtrui.mvp.view.UMPush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.socks.library.KLog;
import com.umeng.message.UTrack;
import com.umeng.message.entity.UMessage;

import org.json.JSONException;
import org.json.JSONObject;


public class NotificationBroadcast extends BroadcastReceiver {
    public static final String EXTRA_KEY_ACTION = "ACTION";
    public static final String EXTRA_KEY_MSG = "MSG";
    public static final int ACTION_CLICK = 10;  //点击
    public static final int ACTION_DISMISS = 11; //取消
    public static final int EXTRA_ACTION_NOT_EXIST = -1; //点击分类
    private static final String TAG = NotificationBroadcast.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {

        String message = intent.getStringExtra(EXTRA_KEY_MSG);
        int action = intent.getIntExtra(EXTRA_KEY_ACTION,
                EXTRA_ACTION_NOT_EXIST);
        try {
            UMessage msg = (UMessage) new UMessage(new JSONObject(message));
            switch (action) {
                case ACTION_DISMISS:
                    Log.i(TAG, "dismiss notification");
                    UTrack.getInstance(context).setClearPrevMessage(true);
                    UTrack.getInstance(context).trackMsgDismissed(msg);
                    break;
                case ACTION_CLICK:
                    Log.i(TAG, "click notification");
                    UTrack.getInstance(context).setClearPrevMessage(true);
                    MyNotificationService.oldMessage = null;
                    UTrack.getInstance(context).trackMsgClick(msg);

                    KLog.d("点击了",msg.builder_id,msg.activity,msg.play_sound);
                    after_open(msg);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开操作
     */
    private void after_open(UMessage uMessage) {
/*        int appStatus = SystemUtils.getAppSatus(BaseApplication.mContext, "com.coahr.fanoftruck");

        if (appStatus == 1) {
            if (uMessage.after_open.equals("go_app")) {

            }
            if (uMessage.after_open.equals("go_url")) {
                Intent intent = new Intent(BaseApplication.mContext, ContainerActivity.class);
                intent.putExtra("tofragment",Constants.Fragment_MyWebView);
                intent.putExtra("url", uMessage.url);
                intent.putExtra("title", uMessage.display_type);
                BaseApplication.mContext.startActivity(intent);
            }
            if (uMessage.after_open.equals("go_activity")) {


            }
        }
        if (appStatus == 2) {
            if (uMessage.after_open.equals("go_app")) {
                Intent intent = new Intent(BaseApplication.mContext, MainActivity.class);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setAction(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                BaseApplication.mContext.startActivity(intent);
            }

            if (uMessage.after_open.equals("go_url")) {
                Intent intent = new Intent(BaseApplication.mContext, ContainerActivity.class);
                intent.putExtra("url", uMessage.url);
                intent.putExtra("title", uMessage.display_type);
                BaseApplication.mContext.startActivity(intent);
            }

            if (uMessage.after_open.equals("go_activity")) {


            }

        }

        if (appStatus == 3) {
            //启动App

            if (uMessage.after_open.equals("go_app")) {
                Intent intent = BaseApplication.mContext.getPackageManager().getLaunchIntentForPackage(BaseApplication.mContext.getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                BaseApplication.mContext.startActivity(intent);
            }

            if (uMessage.after_open.equals("go_url")) {

                Intent intent = BaseApplication.mContext.getPackageManager().getLaunchIntentForPackage(BaseApplication.mContext.getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                BaseApplication.mContext.startActivity(intent);

                Intent intent_url = new Intent(BaseApplication.mContext, ContainerActivity.class);
                intent_url.putExtra("url", uMessage.url);
                intent_url.putExtra("title", uMessage.display_type);
                BaseApplication.mContext.startActivity(intent_url);
            }

            if (uMessage.after_open.equals("go_activity")) {
                Intent intent = BaseApplication.mContext.getPackageManager().getLaunchIntentForPackage(BaseApplication.mContext.getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                BaseApplication.mContext.startActivity(intent);

            }


        }*/


    }
}
