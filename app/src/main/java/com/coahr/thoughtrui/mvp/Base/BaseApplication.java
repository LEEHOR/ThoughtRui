package com.coahr.thoughtrui.mvp.Base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import androidx.fragment.app.Fragment;

import com.alibaba.sdk.android.oss.common.OSSLog;
import com.coahr.thoughtrui.BuildConfig;
import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.dagger.components.DaggerApplicationComponents;
import com.coahr.thoughtrui.mvp.view.ConstantsActivity;
import com.socks.library.KLog;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import org.litepal.LitePal;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 11:45
 */
public class BaseApplication extends MultiDexApplication implements HasActivityInjector, HasSupportFragmentInjector {
    private final static String TAG = "TH_BaseApplication";
    public static Context mContext;
    //内部封装map管理众多activity的component
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidActivityInjector;
    //内部封装map管理众多fragment的component
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidFragmentInjector;
    private PushAgent mPushAgent;
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerApplicationComponents.create().inject(this);
        mContext = getApplicationContext();
          MultiDex.install(mContext);
        LitePal.initialize(getApplicationContext());

        UMConfigure.init(this, "5c2c19abf1f556991e0000b8", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "ced813027db2c5016506edd6827d3d95");
//获取消息推送代理示例
        mPushAgent = PushAgent.getInstance(this);
        //registerUmeng();
        initUpush();
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        //SDKInitializer.initialize(getApplicationContext());
        //SDKInitializer.setCoordType(CoordType.BD09LL);

        //初始化QQbugly

        // 获取当前包名
        String packageName = mContext.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(mContext);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        CrashReport.setIsDevelopmentDevice(mContext, BuildConfig.DEBUG);
        // 初始化Bugly
//        CrashReport.initCrashReport(mContext, "e056cf4e34", true, strategy);
        CrashReport.initCrashReport(mContext, "5dde2dddaa", true, strategy);


        if (PreferenceUtils.contains(mContext, Constants.user_key)) {
            Constants.user_name = PreferenceUtils.getPrefString(mContext, Constants.user_key, "");
        }
        if (PreferenceUtils.contains(mContext, Constants.sessionId_key)) {
            Constants.sessionId = PreferenceUtils.getPrefString(mContext, Constants.sessionId_key, "");
        }
        if (PreferenceUtils.contains(mContext, Constants.devicestoken_key)) {
            Constants.devicestoken = PreferenceUtils.getPrefString(mContext, Constants.devicestoken_key, "");
        }
        if (PreferenceUtils.contains(mContext, Constants.user_type_key)) {
            Constants.user_type = PreferenceUtils.getPrefInt(mContext, Constants.user_type_key, 1);
        }
        if (PreferenceUtils.contains(mContext, Constants.user_type_key)) {
            Constants.user_type = PreferenceUtils.getPrefInt(mContext, Constants.user_type_key, 1);
        }
        if (PreferenceUtils.contains(mContext, Constants.user_type_key)) {
            Constants.user_type = PreferenceUtils.getPrefInt(mContext, Constants.user_type_key, 1);
        }


        //===================================阿里云Oss上传===================================
        if (PreferenceUtils.contains(mContext, Constants.AK_KEY)) {
            Constants.AK = PreferenceUtils.getPrefString(mContext, Constants.AK_KEY, "");
        }
        if (PreferenceUtils.contains(mContext, Constants.SK_KEY)) {
            Constants.SK = PreferenceUtils.getPrefString(mContext, Constants.SK_KEY, "");
        }
        if (PreferenceUtils.contains(mContext, Constants.STOKEN_KEY)) {
            Constants.STOKEN = PreferenceUtils.getPrefString(mContext, Constants.STOKEN_KEY, "");
        }
        if (PreferenceUtils.contains(mContext, Constants.Expiration_KEY)) {
            Constants.Expiration = PreferenceUtils.getPrefLong(mContext, Constants.Expiration_KEY, 0);
        }
        if (PreferenceUtils.contains(mContext, Constants.BUCKET_KEY)) {
            Constants.BUCKET = PreferenceUtils.getPrefString(mContext, Constants.BUCKET_KEY, "");
        }
        if (PreferenceUtils.contains(mContext, Constants.ENDPOINT_KEY)) {
            Constants.ENDPOINT = PreferenceUtils.getPrefString(mContext, Constants.ENDPOINT_KEY, "");
        }

        OSSLog.enableLog();
//        CrashReport.testJavaCrash();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
       // MultiDex.install(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidActivityInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidFragmentInjector;
    }

    private void initUpush() {
        PushAgent mPushAgent = PushAgent.getInstance(this);
        handler = new Handler(getMainLooper());
        //sdk开启通知声音
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);


        /**
         * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {

            @Override
            public void launchApp(Context context, UMessage msg) {
                Map<String, String> extra = msg.extra;
                String pushType = extra.get("pushType");
                String pushId = extra.get("pushId");
                KLog.d(TAG, pushId, pushType);
                JumpToProjectList();
            }

            @Override
            public void openUrl(Context context, UMessage msg) {
                KLog.d(TAG, 2);
                super.openUrl(context, msg);
            }

            @Override
            public void openActivity(Context context, UMessage msg) {
                KLog.d(TAG, 3);
                super.openActivity(context, msg);
            }

            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Map<String, String> extra = msg.extra;
                String pushType = extra.get("pushType");
                String pushId = extra.get("pushId");
                if (pushType != null && pushType.equals("1")) {
                    JumpToProjectList();
                }
            }
        };
        //使用自定义的NotificationHandler
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
        //注册推送服务 每次调用register都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                Log.i(TAG, "device token: " + deviceToken);
                PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.devicestoken_key, deviceToken);
                Constants.devicestoken = deviceToken;
                //sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.i(TAG, "register failed: " + s + " " + s1);
                //  sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
            }
        });
    }

    /**
     * 跳转到历史项目
     */
    private void JumpToProjectList() {
        Intent intent = new Intent(this, ConstantsActivity.class);
        intent.putExtra("from", Constants.fragment_mainInfo);
        intent.putExtra("to", Constants.fragment_umeng);
        startActivity(intent);
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
