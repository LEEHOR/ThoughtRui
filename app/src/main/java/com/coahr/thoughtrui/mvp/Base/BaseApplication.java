package com.coahr.thoughtrui.mvp.Base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.multidex.MultiDexApplication;
import androidx.fragment.app.Fragment;

import com.alibaba.sdk.android.oss.common.OSSLog;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.dagger.components.DaggerApplicationComponents;
import com.coahr.thoughtrui.mvp.view.UMPush.UmengNotificationService;
import com.socks.library.KLog;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;

import org.litepal.LitePal;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static anet.channel.bytes.a.TAG;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 11:45
 */
public class BaseApplication extends MultiDexApplication implements HasActivityInjector, HasSupportFragmentInjector {
    public interface MsgDisplayListener {
        void CODE_DOWNLOAD_SUCCESS(String appVersion,String Info,int HandlePatchVersion );
        void CODE_LOAD_RELAUNCH(String appVersion,String Info,int HandlePatchVersion);
        void CODE_LOAD_MFITEM(String appVersion,String Info,int HandlePatchVersion);
        void Other(String appVersion,int code,String Info,int HandlePatchVersion);
    }
    private static MsgDisplayListener listeners;
    public static Context mContext;
    //内部封装map管理众多activity的component
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidActivityInjector;
    //内部封装map管理众多fragment的component
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidFragmentInjector;
    @Override
    public void onCreate() {
        super.onCreate();
        DaggerApplicationComponents.create().inject(this);
        mContext=getApplicationContext();
        LitePal.initialize(getApplicationContext());
        SDKInitializer.initialize(getApplicationContext());
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        UMConfigure.init(this,
                UMConfigure.DEVICE_TYPE_PHONE, "ced813027db2c5016506edd6827d3d95");

        PlatformConfig.setWeixin("wx89f3b1477df1aa39", "b3ad27916ad0fa404f5d1478f3cc0bc2");
        PlatformConfig.setQQZone("","");
        initPush();
        if (PreferenceUtils.contains(mContext, Constants.user_key)) {
           Constants.user_name= PreferenceUtils.getPrefString(mContext, Constants.user_key, "");
        }
        if (PreferenceUtils.contains(mContext, Constants.sessionId_key)) {
           Constants.sessionId= PreferenceUtils.getPrefString(mContext, Constants.sessionId_key, "");
        }
        if (PreferenceUtils.contains(mContext,Constants.devicestoken_key)){
            Constants.devicestoken=PreferenceUtils.getPrefString(mContext,Constants.devicestoken_key,"");
        }
        OSSLog.enableLog();
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidActivityInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidFragmentInjector;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
      // initSophix();
    }
    private void initPush() {
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //sdk开启通知声音
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                KLog.d(TAG, "device token: " + deviceToken);
                Constants.devicestoken = deviceToken;
                PreferenceUtils.setPrefString(mContext, Constants.devicestoken_key, deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                KLog.d(TAG, "register failed: " + s + " " + s1);
            }
        });
        PushAgent.getInstance(mContext).onAppStart();

        //自定义消息处理
        mPushAgent.setPushIntentServiceClass(UmengNotificationService.class);
    }

    /**
     * 初始化阿里热更新
     */
    private void initSophix(){
        final String appVersion = getAppVersion();
        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                .setAesKey(null)
                .setSecretMetaData("25584439-1","71ac1e1bef6bc6bb248d895e06b63cbc","MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCGtLRYWTyuxRBJI8RSYjOPoLuchLXEL2b0yzzJAZiMU2qJBPQRUe/yCVL9sdnD54V69CKupR7KMl2paVU45Y4yIe6Sq53Y4AJWXjBgEFSgMSl4JzNIsK8xzRrH8tMioCP0i0oZMc5G5zJFAOl0FuhJLknzFCSu2zXUDTPcyCIXxa1dQ2GuDiA63fnAUCl1GgKTygJJrf8fDNKL/+Ac1RwiL1FOgAtLj9GnjyrGJtzowsszQCe4WvIeZbEt7G6WT1EZpwK01d2vdJdFZG72GoZaiCTTHR2UJgsbxlbNW7wHpwHvmIZdziugRBHaQk/8KVXtT4U3NMTy/QgAr06vEwtXAgMBAAECggEABGBAb5JOj1rlgYrA6PA5vpO5JIeHJB1qGc+0ZM9BdmWLU3Iuv5VK1zEIWid57IxL4MenjMbebjEeq885LoT+jpBdoLQkh5QXX1jl92jwZtfSAg4780OSHWsNUKuJwlkzcdFIkfL1QYZnMqip7NSFrULsshHYczm50O6w3Z4+xUjhjtSvi2HJeSos6Pb+xCDa8P6edf+aLpG45YS+vH8mHQTZ6h1logYGucNsWgsQDH7DyRRswKb7F/ikzGHxAtpi4xl3L1G8Zyx35eEHxJSNWGHNxBbgOx7msFkOOyNGQI0HStq/y4oZeD/AZ6soCXD7ugkUXqK6nEbFGq3PkXjB8QKBgQDMeRz1mRFRT/1Q6NJXS6GEUPLeHDyAUOKekXEJaxB1rN+lDNb5uNNFYtNCJyVSuxDJfAkKpoyq/qHRwDz5olQJfh7+qDAzcNdB8yY3sZhiWo49EL4zUCgKQAF1SfDw9ikxI/ZhmIGkXIDwV+zmFqLSNNoYh9am3Xd5lUCR1xPGXwKBgQCopsvVSZ1aC78aNxjZHw4RlA7hP1olcXfTJzYwIIbkPl0l8p9b9Czi3qD1yrmfZQe1BL9e1XmoC/TXAhkarKEB7X7X00zWgV2YYDMyJNh8JZg/ynheTTwxSXZpfIChfB1HQAZZZykOIwd4ortqi9Igwdxs1uqIf+RiHaUO78QuCQKBgDiX0O8dBZEG3ar2NbmZokO4D/BvykMNoBuZT7r2miCnz2KkUh/eCwOqXaRypz7lrbjGjs4W2No/DdS7K2VAi5fxA20iaezi74E+ZjaF/hJC1BmMt8gGCH8FxiGLbJeU2kPSm0/Z4Q/31mwvOc9ZNomNvUuK1Vtr8rSBHdT/vWZrAoGBAKfhKSQOXFDnQnrA/74ZuLJs1IcJvh1pnuKUtM8hgcUwAx8kLdel7wyCmm0xOKdfNVXO/QRUsf5CsJ1aXEz+LGOz2sDSeDlKmzV0BWJT5R9neO9/B2Svs0xImVOV+KFG4AotxGQ4WVjWK7i9HJyJxEiRUW8SXHI2BMxaQBTnPTypAoGANDis+n1PkJojRde0o39qh+oZz24ETM2CdakZqDWh3AGs0nlp9iEcq5hKM328sKFBofOlh4kEkuvp1HSDk6+1m0cb9PZknKZ8QNQZfC9ncLUx0stHEVXIhEkbf6YCHUByuWfEbl160Rs9r5OIr/uur7DcMkkbWHDSe2/E0Wt63Cc=")
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                            KLog.d("hot",mode,code,info,handlePatchVersion);
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_DOWNLOAD_SUCCESS) {
                            // 表明补丁加载成功
                            if (listeners != null) {
                                listeners.CODE_DOWNLOAD_SUCCESS(appVersion,info,handlePatchVersion);
                            }
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后应用自杀
                            PreferenceUtils.setPrefBoolean(getApplicationContext(),Constants.AliYunHot_key,false);
                            if (listeners != null) {
                                listeners.CODE_LOAD_RELAUNCH(appVersion,info,handlePatchVersion);
                            }
                        } else if (code == PatchStatus.CODE_LOAD_MFITEM) {////补丁SOPHIX.MF文件解析异常
                            // 内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载
                            if (listeners != null) {
                                listeners.CODE_LOAD_MFITEM(appVersion,info,handlePatchVersion);
                            }
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                            if (listeners != null) {
                                listeners.Other(appVersion,code,info,handlePatchVersion);
                            }
                        }
                    }
                }).initialize();
    }
    private String getAppVersion() {
        String appVersion;
        try {
            appVersion= this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            appVersion = "1.0.0";
        }
        return  appVersion;
    }
    public static void setListener(MsgDisplayListener listener) {
        listeners = listener;
    }
}
