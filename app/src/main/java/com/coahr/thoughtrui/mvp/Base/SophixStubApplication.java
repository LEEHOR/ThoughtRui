package com.coahr.thoughtrui.mvp.Base;

import android.content.Context;
import android.util.Log;

import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

import androidx.annotation.Keep;
import androidx.multidex.MultiDex;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/23
 * 描述：
 */
public class SophixStubApplication extends SophixApplication {
    private final String TAG = "SophixStubApplication";
    private Context mcontext;
    private String appVersion;

    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(BaseApplication.class)
    static class RealApplicationStub {

    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
      //  this.mcontext=base;
//         如果需要使用MultiDex，需要在此处调用。
        // MultiDex.install(this);
        initSophix();
    }
    private void initSophix() {
        appVersion = "0.0.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData(null, null, null)
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.i(TAG, "sophix load patch success!");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            PreferenceUtils.setPrefBoolean(mcontext, Constants.AliYunHot_key,true);
                            PreferenceUtils.setPrefString(mcontext, Constants.path_key,info);
                            PreferenceUtils.setPrefString(mcontext, Constants.pathVersion_key,String.valueOf(handlePatchVersion));
                            PreferenceUtils.setPrefString(mcontext, Constants.app_version_key, appVersion);
                            Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                        }
                    }
                }).initialize();
    }
}
