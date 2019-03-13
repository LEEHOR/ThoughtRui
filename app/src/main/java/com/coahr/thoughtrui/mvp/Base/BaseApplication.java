package com.coahr.thoughtrui.mvp.Base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import androidx.fragment.app.Fragment;

import com.alibaba.sdk.android.oss.common.OSSLog;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.dagger.components.DaggerApplicationComponents;
import com.socks.library.KLog;

import org.litepal.LitePal;

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
        MultiDex.install(mContext);
        LitePal.initialize(getApplicationContext());
        SDKInitializer.initialize(getApplicationContext());
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        if (PreferenceUtils.contains(mContext, Constants.user_key)) {
           Constants.user_name= PreferenceUtils.getPrefString(mContext, Constants.user_key, "");
        }
        if (PreferenceUtils.contains(mContext, Constants.sessionId_key)) {
           Constants.sessionId= PreferenceUtils.getPrefString(mContext, Constants.sessionId_key, "");
        }
        if (PreferenceUtils.contains(mContext,Constants.devicestoken_key)){
            Constants.devicestoken=PreferenceUtils.getPrefString(mContext,Constants.devicestoken_key,"");
        }
        if (PreferenceUtils.contains(mContext,Constants.user_type_key)){
            Constants.user_type=PreferenceUtils.getPrefInt(mContext,Constants.user_type_key,1);
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

    private String getAppVersion() {
        String appVersion;
        try {
            appVersion= this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            appVersion = "1.0.0";
        }
        return  appVersion;
    }
}
